package com.ardc.arkdust.capability.story;

import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;
import java.util.List;

public enum StoryBag {
    CW_MAIN("moonfall",11,true,BagDisplayType.FIRST_PERSON,null);

    public String bag_name;
    public int length;
    public boolean requireLBL;
    public BagDisplayType type;
    public IOnStoryAchieve progress;

    StoryBag(String name,int length,boolean requireLevelByLevel,BagDisplayType type,@Nullable IOnStoryAchieve progress){
        this.bag_name = name;
        this.length = length;
        this.requireLBL = requireLevelByLevel;
        this.type = type;
        this.progress = progress;
    }

    public String getName(){
        return bag_name;
    }

    public interface IOnStoryAchieve {
        void call(int achieveValue, StoryBag bag, PlayerEntity player);
    }

    public static StoryBag nameToBag(String name){
        for(StoryBag bag:StoryBag.values()){
            if(bag.getName().equals(name))
                return bag;
        }
        return null;
    }
    public static int levelCorrect(StoryBag bag,int level){
        if(level < 0) level = 0;
        return Math.min(level, bag.length);
    }
    public static boolean addBagToList(List<StorySaveDataBag> list,StoryBag bag,int level){
        level = levelCorrect(bag,level);
        for (StorySaveDataBag inBag : list){
            if(inBag.getBag() == bag){
                return dataBagLevelSet(inBag,bag,level);
            }
        }
        StorySaveDataBag newBag = new StorySaveDataBag(bag,0);
        boolean b = dataBagLevelSet(newBag,bag,level);
        if(b) list.add(newBag);
        return b;
    }
    private static boolean dataBagLevelSet(StorySaveDataBag inBag,StoryBag bag,int level){
        //注：关于返回的boolean的标准：允许信息展示则会返回true，而非按照是否设置成功判断
        if(bag.requireLBL){
            if(level == inBag.achieveLevel + 1) {
                inBag.setAchieveLevel(level);
                return true;
            }else return level <= inBag.achieveLevel;
        }else {
            if(level > inBag.achieveLevel){
                inBag.setAchieveLevel(level);
            }
            return true;
        }
    }

    public enum BagDisplayType{
        DEFAULT,//默认
        THEME,//主题曲，官方剧情
        EPISODE,//插曲，官方大型活动
        PUBLIC_AFFAIRS,//公共事务，官方小型活动
        SPEC_OPS,//特别行动，官方故事集
        ACTIVITY,//活动，mod添加活动
        FIRST_PERSON,//第一人称，mod剧情主线
        MONSTER//怪物，mon世界观插入剧情
    }

    public static class StorySaveDataBag{
        public StoryBag bag;
        public int achieveLevel;

        public StorySaveDataBag(StoryBag bag,int level){
            this.bag = bag;
            this.achieveLevel = level;
        }

        public StorySaveDataBag(String bag,int level){
            this.bag = nameToBag(bag);
            this.achieveLevel = level;
        }

        public boolean ifBagNull(){
            return bag==null;
        }

        public StoryBag getBag() {
            return bag;
        }

        public void setAchieveLevel(int level){
            this.achieveLevel = level;
        }

        public int getAchieveLevel() {
            return achieveLevel;
        }

        public String toString(){
            return "StorySDB:[#name:" + bag.getName() + ",#level:" + getAchieveLevel() + "]";
        }
    }
}
