package com.ardc.arkdust.worldgen;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

public class WorldStructureSavaData extends WorldSavedData {
    private static final String NAME = "ArdStructureCreateData";
    public CompoundNBT dNbt;

    public WorldStructureSavaData(){
        super(NAME);
    }

    public static WorldStructureSavaData get(World worldIn){
        if(worldIn instanceof ServerWorld){
            ServerWorld world = worldIn.getServer().getLevel(World.OVERWORLD);
            DimensionSavedDataManager s = world.getDataStorage();
            return s.computeIfAbsent(WorldStructureSavaData::new, NAME);
        }
        throw new RuntimeException("you shouldn't use the client world");
    }
    private CompoundNBT chuckNBT(CompoundNBT nbt){
        return nbt == null ? new CompoundNBT() : nbt;
    }

    public void setData(String key,boolean state){
        dNbt = chuckNBT(dNbt);
        dNbt.putBoolean(key,state);
        setDirty();
    }

    public CompoundNBT getData(){
        return chuckNBT(dNbt);
    }

    public void load(CompoundNBT nbt) {
        this.dNbt = nbt.getCompound("ArdStructure");
    }
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put("ArdStructure",this.dNbt);
        return nbt;
    }
}
