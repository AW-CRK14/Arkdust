package com.ardc.arkdust.capability.story;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Collections;
import java.util.List;

public interface IStorySaveCapability extends INBTSerializable<CompoundNBT> {
    List<StoryBag.StorySaveDataBag> getAchieveBagList();

    boolean addToList(StoryBag bag,int level);

    default boolean addToList(String bagName,int level){
        StoryBag bag = StoryBag.nameToBag(bagName);
        return bag != null && addToList(bag, level);
    }

    default ListNBT createSaveNBT(){
        List<StoryBag.StorySaveDataBag> list = getAchieveBagList();
        ListNBT nbt = new ListNBT();
        for (StoryBag.StorySaveDataBag bag : list){
            CompoundNBT inNbt = new CompoundNBT();
            inNbt.putString("name",bag.bag.getName());
            inNbt.putInt("level",bag.achieveLevel);
            nbt.add(inNbt);
        }
        return nbt;
    }

    default List<StoryBag.StorySaveDataBag> parseSaveNBT(CompoundNBT nbt){
        List<StoryBag.StorySaveDataBag> list = new java.util.ArrayList<>(Collections.emptyList());
        INBT inbt = nbt.get("StorySaveData");
        if(inbt instanceof ListNBT){
            for (INBT inInbt : (ListNBT)inbt){
                if(inInbt instanceof CompoundNBT){
                    StoryBag.StorySaveDataBag bag = new StoryBag.StorySaveDataBag(((CompoundNBT) inInbt).getString("name"),((CompoundNBT) inInbt).getInt("level"));
                    list.add(bag);
                }
            }
        }
        return list;
    }

    default StringBuilder createListInfo(){
        StringBuilder builder = new StringBuilder();
        int c = 0;
        for(StoryBag.StorySaveDataBag bag:getAchieveBagList()){
            c++;
            builder.append("\n").append(c).append(":").append(bag.toString());
        }
        return builder;
    }
}