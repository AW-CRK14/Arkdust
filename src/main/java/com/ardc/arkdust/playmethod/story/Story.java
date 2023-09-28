package com.ardc.arkdust.playmethod.story;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.registry.StoryRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Story{
    static final Logger LOGGER = LogManager.getLogger(Story.class);
    public static final Map<ResourceLocation, Story> storyMap = new HashMap<>();



    public static final ResourceLocation defRPB = new ResourceLocation(Utils.MOD_ID,"");
    public static final ResourceLocation defRPS = new ResourceLocation(Utils.MOD_ID,"");//TODO 制作默认图




    public ResourceLocation name;
    public int length;
    public Map<Integer, StoryAchListener<?>> achieveListenerMap;
    public boolean allowToSkip;
    public ResourceLocation renderBig;//控制在游戏中展示的剧情图片
    public ResourceLocation renderSmall;//控制在游戏概览种展示的剧情图片
    public StoryType type;
    public IOnStoryAchieve progress;
    public List<ResourceLocation> dependence;//剧情前置依赖

    public Story(ResourceLocation name, int length, StoryType type, @Nullable IOnStoryAchieve progress, ResourceLocation renderBig, ResourceLocation renderSmall, List<ResourceLocation> dependence,boolean allowToSkip,achieveMapBuilder builder){
        this.name = name;
        this.length = length;
        this.type = type;
        this.progress = progress == null ? empty : progress;
        this.renderBig = renderBig;
        this.renderSmall = renderSmall;
        this.dependence = dependence == null ? new ArrayList<>() : dependence;
        this.allowToSkip = allowToSkip;
        this.achieveListenerMap = new HashMap<>();
        builder.call(this.achieveListenerMap);
    }

    public Story(ResourceLocation name,int length){
        this.name = name;
        this.length = length;
        this.type = StoryType.DEFAULT;
        this.progress = empty;
        this.renderBig = defRPB;
        this.renderSmall = defRPS;
        this.dependence = new ArrayList<>();
        this.allowToSkip = false;
        this.achieveListenerMap = new HashMap<>();
    }

    @Nullable
    public StoryAchListener.AdvStoryAchieveListener<?> createAdvSAL(int stage){
        if(!this.achieveListenerMap.containsKey(stage)) return null;
        return new StoryAchListener.AdvStoryAchieveListener<>(stage,this,this.achieveListenerMap.get(stage));
    }

    public Component createTitle(){
        return Component.translatable("story.trans.title.$" + this.name);
    }

    public Component createMessage(int stage, boolean in){
        return Component.translatable("story.trans.common."+ (in ? "write" : "title")).append(createTitle()).append(Component.translatable("story.trans.common.stage",stage));
    }

    @Override
    public String toString() {
        return "Story{" +
                "name=" + name +
                ", length=" + length +
                ", dependence=" + dependence +
                '}';
    }

    public ResourceLocation getName(){
        return name;
    }

    public static final IOnStoryAchieve empty = (a,b,c)->{};

    public interface IOnStoryAchieve {
        void call(int achieveValue, Story bag, Player player);
    }

    public static Story nameToBag(String name){
        return storyMap.getOrDefault(new ResourceLocation(name), StoryRegistry.DEFAULT);
    }

    public enum StoryType {
        DEFAULT,//默认
        THEME,//主题曲，官方剧情
        EPISODE,//插曲，官方大型活动
        PUBLIC_AFFAIRS,//公共事务，官方小型活动
        SPEC_OPS,//特别行动，官方故事集
        ACTIVITY,//活动，mod添加活动
        FIRST_PERSON,//第一人称，mod剧情主线
        MONSTER//怪物，mon世界观插入剧情
    }

    public interface achieveMapBuilder{
        void call(Map<Integer, StoryAchListener<?>> map);
    }
}
