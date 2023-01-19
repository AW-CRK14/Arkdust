package com.ardc.arkdust.capability.rdi_auth;

import com.ibm.icu.impl.Pair;
import net.minecraft.nbt.CompoundNBT;

public class RDIAccountAuthCapability implements IRDIAccountAuthCapability {
    public static Pair<Integer,Integer> toNum(int p,int levelAdd,int dif){
        int level = 1;
        int level2Point;
        while (true) {
            level2Point = dif * level;
            if (p - level2Point < 0) {
                return Pair.of(p + levelAdd,level);
            }else {
                level += 1;
                p -= level2Point;
            }
        }
    }

    public static int levelToAExp(int level){
        if(level <= 40)
            return (25 + 25*level) * level;
        else if(level <= 80){
            level -= 40;
            return range1 + (2100 + 100 *level)*level;
        }else if(level <= 120){
            level -= 80;
            return range2 + (10250 + 250*level)*level;
        }else
            return range3;
    }

    public static int levelIncludeAExp(int level){
        if(level <= 40)
            return level*50;
        else if (level <= 80)
            return 2000 + (level-40) * 200;
        else if (level <= 120)
            return 10000 + (level-80) * 500;
        else
            return 30000;
    }

    @Override
    public String toString() {
        return "RhodesIslandAuthorityAccountCapability{" +
                "exp=" + nAExp +
                "(flowAndLevel:" + this.AExp$getFlowAndLevel() + "," + this.AExp$getFlowAndLevel() +
                "),sanity=" + nSanity +
                "}";
    }

    private int nAExp;
    private int nSanity;

    @Override
    public int getAExp() {
        return nAExp;
    }

    @Override
    public void setAExp(int num) {
        nAExp = num;
    }

    @Override
    public int addAExp(int num) {
        nAExp += num;
        return nAExp;
    }

    @Override
    public int getSanity() {
        return nSanity;
    }

    @Override
    public void setSanity(int num) {
        nSanity = num;
    }

    @Override
    public int addSanity(int num) {
        nSanity += num;
        return nSanity;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("exp",nAExp);
        nbt.putInt("sanity",nSanity);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        nAExp = nbt.getInt("exp");
        nSanity = nbt.getInt("sanity");
    }
}
