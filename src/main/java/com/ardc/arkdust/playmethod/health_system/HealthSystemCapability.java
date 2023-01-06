package com.ardc.arkdust.playmethod.health_system;

import net.minecraft.nbt.CompoundNBT;

public class HealthSystemCapability implements IHealthSystemCapability {
    private int nORIPoint;
    private byte nORIRLevel;
    public static int ORI$level2Point(int level){
        return 100 + 100*level + 20*level*level;
    }

    public HealthSystemCapability(){
        this.nORIPoint = 0;
        this.nORIPoint = 0;
    }

    public int ORI$getPoint() {
        return nORIPoint;
    }

    public int ORI$addPoint(int num) {
        nORIPoint += num;
        if(nORIPoint < 0) nORIPoint = 0;
        return nORIPoint;
    }

    public void ORI$setPoint(int num) {
        nORIPoint = num;
    }

    public byte ORI$getRLevel() {
        return nORIRLevel;
    }

    public void ORI$addRLevel(byte level) {
        nORIRLevel += level;
    }

    public void ORI$setRLevel(byte level) {
        nORIRLevel = level;
    }

    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("ori_point",nORIPoint);
        nbt.putByte("ori_level",nORIRLevel);
        return nbt;
    }

    public void deserializeNBT(CompoundNBT nbt) {
        nORIPoint = nbt.getInt("ori_point");
        nORIRLevel = nbt.getByte("ori_level");
    }

    @Override
    public String toString() {
        return "HealthSystemCapability{ORI:{" +
                "point=" + nORIPoint +
                ",level=" + nORIRLevel +
                "}}";
    }
}
