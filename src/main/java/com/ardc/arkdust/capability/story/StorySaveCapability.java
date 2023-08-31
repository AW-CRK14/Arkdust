package com.ardc.arkdust.capability.story;

import com.ardc.arkdust.advanced_obj.RangeNoRepIntList;
import com.ardc.arkdust.playmethod.story.Story;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class StorySaveCapability implements IStorySaveCapability{
    private static final Logger LOGGER = LogManager.getLogger();
    public Map<Story, RangeNoRepIntList> saveDataBagList;

    private void initialize(){
        saveDataBagList = saveDataBagList == null ? new HashMap<>() : saveDataBagList;
    }

    @Override
    public CompoundNBT serializeNBT() {
        initialize();
        CompoundNBT nbt = new CompoundNBT();
        saveDataBagList.forEach((s,p)-> nbt.putIntArray(s.name.toString(),p));
        LOGGER.debug("[ArdCap-StorySaveCap]SerializeNBT finished:{player:{},capNbt:{}}", Minecraft.getInstance().player,nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        initialize();
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
        initialize();
        return saveDataBagList;
    }

    @Override
    public boolean contains(Story story, int storyIndex) {
        initialize();
        return saveDataBagList.containsKey(story) && saveDataBagList.get(story).contains(storyIndex);
    }

    @Override
    public boolean add(Story storyIns, int storyIndex) {
        initialize();
        if(storyIndex <= 0 || storyIndex > storyIns.length)
            return false;
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
        initialize();
        if(saveDataBagList.containsKey(story)){
            saveDataBagList.get(story).fill();
        }else {
            saveDataBagList.put(story,new RangeNoRepIntList(1,story.length).fill());
        }
    }
}
