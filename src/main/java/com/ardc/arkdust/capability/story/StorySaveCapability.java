package com.ardc.arkdust.capability.story;

import com.ardc.arkdust.advanced_obj.RangeNoRepIntList;
import com.ardc.arkdust.helper.ListAndMapHelper;
import com.ardc.arkdust.playmethod.story.Story;
import com.ardc.arkdust.playmethod.story.StoryAchListener;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.eventbus.api.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StorySaveCapability implements IStorySaveCapability{
    private static final Logger LOGGER = LogManager.getLogger();
    public Map<Story, RangeNoRepIntList> saveDataBagList = new HashMap<>();

    public final Map<Class<?>, List<StoryAchListener.AdvStoryAchieveListener<?>>> listenerMap = new HashMap<>();

    private void initialize(){
//        saveDataBagList = saveDataBagList == null ? new HashMap<>() : saveDataBagList;
    }

    @Override
    public CompoundNBT serializeNBT() {
//        initialize();
        CompoundNBT nbt = new CompoundNBT();
        saveDataBagList.forEach((s,p)-> nbt.putIntArray(s.name.toString(),p));
        LOGGER.debug("[ArdCap-StorySaveCap]SerializeNBT finished:{player:{},capNbt:{}}", Minecraft.getInstance().player,nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
//        initialize();
        Story.storyMap.forEach((res, story)->{
            if(nbt.contains(res.toString())){
//                saveDataBagList.put(story,new ArrayList<>(Arrays.asList()));
                saveDataBagList.put(story,new RangeNoRepIntList(1,story.length).addAllAndReturn(Arrays.stream(nbt.getIntArray(res.toString())).boxed().collect(Collectors.toList())));
            }
        });
        LOGGER.debug("[ArdCap-StorySaveCap]DeserializeNBT finished:{player:{},infoMap:{}}", Minecraft.getInstance().player,saveDataBagList);
    }


    @Override
    public Map<Story, RangeNoRepIntList> getStoryProcess() {
//        initialize();
        return saveDataBagList;
    }

    @Override
    public boolean contains(Story story, int storyIndex) {
//        initialize();
        return saveDataBagList.containsKey(story) && saveDataBagList.get(story).contains(storyIndex);
    }

    @Override
    public boolean add(Story storyIns, int storyIndex,PlayerEntity player){
        if(storyIndex <= 0 || storyIndex > storyIns.length)
            return false;
        boolean result = addIn(storyIns,storyIndex);
        if(!result) return false;

        StoryAchListener.AdvStoryAchieveListener<?> listener = storyIns.createAdvSAL(storyIndex);
        if(listener!=null) ListAndMapHelper.tryDeleteElementToMapList(this.listenerMap,listener.listener.clazz, Collections.singletonList(listener));

        if(!storyIns.allowToSkip && storyIndex+1 <= storyIns.length){
            StoryAchListener.AdvStoryAchieveListener<?> listener2 = storyIns.createAdvSAL(storyIndex+1);
            if(listener2!=null) ListAndMapHelper.tryAddElementToMapList(this.listenerMap,listener2.listener.clazz, listener2);
        }

        if(!player.level.isClientSide) player.displayClientMessage(storyIns.createMessage(storyIndex,true),false);

        return true;
    }

    public boolean addIn(Story storyIns, int storyIndex) {
//        initialize();
        if(saveDataBagList.containsKey(storyIns)){
            RangeNoRepIntList s = saveDataBagList.get(storyIns);
            if(storyIns.allowToSkip)
                return s.add(storyIndex);
            else {
                return (s.contains(storyIndex - 1) || storyIndex == 1) && s.add(storyIndex);
            }
        }else if(storyIns.allowToSkip || storyIndex == 1) {
            saveDataBagList.put(storyIns, new RangeNoRepIntList(1, storyIns.length).addAndReturn(storyIndex));
            return true;
        }
        return false;

    }

    @Override
    public void finishStory(Story story) {
//        initialize();
        if(saveDataBagList.containsKey(story)){
            saveDataBagList.get(story).fill();
        }else {
            saveDataBagList.put(story,new RangeNoRepIntList(1,story.length).fill());
        }
    }

    @Override
    public void buildListener() {
        List<StoryAchListener.AdvStoryAchieveListener<?>> listenerList = new ArrayList<>();
        Story.storyMap.forEach((r,s)->{
            if(isStoryFinished(s)) return;
            if(s.dependence.isEmpty() || checkDependence(s)){
                if(!s.allowToSkip){
                    int index = saveDataBagList.containsKey(s) ? saveDataBagList.get(s).getFirstNotExistInt() : 1;
                    listenerList.add(s.createAdvSAL(index));
                }else {
                    List<Integer> list = saveDataBagList.containsKey(s) ? saveDataBagList.get(s).getNotExistInt() : IntStream.range(1,s.length).boxed().collect(Collectors.toList());
                    for(int stage : list){
                        listenerList.add(s.createAdvSAL(stage));
                    }
                }
            }
        });
        ListAndMapHelper.tryTransListToMapList(this.listenerMap,listenerList.stream().filter(Objects::nonNull).collect(Collectors.toList()), (i)->i.listener.clazz,(i)->i);
    }

    private boolean checkDependence(Story story){
        for(ResourceLocation r : story.dependence){
            Story s = Story.storyMap.get(r);
            if(s!=null && s!=story && !isStoryFinished(s)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isStoryFinished(Story s) {
        return saveDataBagList.containsKey(s) && saveDataBagList.get(s).size()==s.length;
    }

    @Override
    public void pushEvent(Event event, PlayerEntity player) {
        for(StoryAchListener.AdvStoryAchieveListener<?> l : ListAndMapHelper.copyList(listenerMap.getOrDefault(event.getClass(), Collections.emptyList()))){
            if (l.listener.call.preCall(event)) {
                add(l.story, l.stage, player);
            }
        }
    }
}
