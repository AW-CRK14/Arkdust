package com.ardc.arkdust.worldgen;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class WorldStructureSavaData extends SavedData {
    private static final String NAME = "ArdStructureCreateData";
    public CompoundTag nbt = new CompoundTag();

    public WorldStructureSavaData(){}
    public WorldStructureSavaData(CompoundTag tag){this.nbt = tag;}

    public static WorldStructureSavaData get(ServerLevel worldIn){
        return worldIn.getDataStorage().computeIfAbsent(WorldStructureSavaData::new,WorldStructureSavaData::new, NAME);
    }

    public void setData(String key,boolean state){
        nbt.putBoolean(key,state);
        setDirty();
    }

    public CompoundTag getData(){
        return nbt;
    }
    public CompoundTag save(CompoundTag nbt) {
        nbt.put("ArdStructure",this.nbt);
        return nbt;
    }
}
