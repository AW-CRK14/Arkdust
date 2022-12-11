package com.ardc.arkdust.playmethod.story;

import net.minecraft.nbt.CompoundNBT;

import java.util.Collections;
import java.util.List;

public class StorySaveCapability implements IStorySaveCapability{
    public List<StoryBag.StorySaveDataBag> saveDataBagList;

    public StorySaveCapability(List<StoryBag.StorySaveDataBag> list){
        this.saveDataBagList = (list==null) ? new java.util.ArrayList<>(Collections.emptyList()) : list;
    }

    public StorySaveCapability(){
        this.saveDataBagList = new java.util.ArrayList<>(Collections.emptyList());
    }

    @Override
    public List<StoryBag.StorySaveDataBag> getAchieveBagList() {
        return this.saveDataBagList;
    }

    @Override
    public boolean addToList(StoryBag bag, int level) {
        return StoryBag.addBagToList(saveDataBagList,bag,level);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("StorySaveData",createSaveNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.saveDataBagList = parseSaveNBT(nbt);
    }


}
