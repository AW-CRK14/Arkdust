package com.ardc.arkdust.capability.rdi_auth;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.capability.AbsCapabilityProvider;
import com.ibm.icu.impl.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.network.PacketDistributor;

@AutoRegisterCapability
public class RDIAccountAuthCapability implements AbsCapabilityProvider.CommonEntityCap {
    public void sendToClient(ServerPlayer entity) {
        RDIAccountAuthDataNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> entity), new RDIAccountAuthDataNetwork.RDIAccountAuthDataPack(getAExp(),getAExp()));
        Utils.LOGGER.debug("[ArdNetwork-RDIAccount]Player#" + entity.getName().getString() + " send pack from server");
        Utils.LOGGER.debug("[ArdNetwork-RDIAccount]RDIAccAuthCap in server:" + this);
    }

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


    public String toString() {
        return "RhodesIslandAuthorityAccountCapability{" +
                "exp=" + nAExp +
                "(flowAndLevel:" + this.AExp$getFlowAndLevel() + "," + this.AExp$getFlowAndLevel() +
                "),sanity=" + nSanity +
                "}";
    }

    private int nAExp;
    private int nSanity;


    public int getAExp() {
        return nAExp;
    }


    public void setAExp(int num) {
        nAExp = num;
    }


    public int addAExp(int num) {
        nAExp += num;
        return nAExp;
    }


    public int getSanity() {
        return nSanity;
    }


    public void setSanity(int num) {
        nSanity = num;
    }


    public int addSanity(int num) {
        nSanity += num;
        return nSanity;
    }


    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("exp",nAExp);
        nbt.putInt("sanity",nSanity);
        return nbt;
    }


    public void deserializeNBT(CompoundTag nbt) {
        nAExp = nbt.getInt("exp");
        nSanity = nbt.getInt("sanity");
    }


    public void addSanity(){addSanity(1);}
    public void resetSanity(){setSanity(getMaxSanity());}
    public int getMaxSanity(){return 70 + AExp$getFlowAndLevel().second / 2;}


    public static int range1 = 41000;
    public static int range2 = range1 + 244000;
    public static int range3 = range2 + 810000;//1095000
    public Pair<Integer,Integer> AExp$getFlowAndLevel(){
        int p = getAExp();
        if(p < range1)
            return RDIAccountAuthCapability.toNum(p,0,50);
        else if (p < range2)
            return RDIAccountAuthCapability.toNum(p-range1,40,200);
        else if(p < range3)
            return RDIAccountAuthCapability.toNum(p,80,500);
        else {
            setAExp(range3);
            return Pair.of(RDIAccountAuthCapability.levelIncludeAExp(120), 120);
        }
    }
}
