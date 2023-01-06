package com.ardc.arkdust.playmethod.rdi_auth;

import com.ardc.arkdust.Utils;
import com.ibm.icu.impl.Pair;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.network.PacketDistributor;

public interface IRDIAccountAuthCapability extends INBTSerializable<CompoundNBT> {
    default void sendPackToClient(ServerPlayerEntity entity) {
        RDIAccountAuthDataNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> entity), new RDIAccountAuthDataNetwork.RDIAccountAuthDataPack(getAExp(),getAExp()));
        Utils.LOGGER.info("Player#" + entity.getName().getString() + " send pack from server");
        Utils.LOGGER.info("RDIAccAuthCapInServer:" + this.toString());
    }


    int range1 = 41000;
    int range2 = range1 + 244000;
    int range3 = range2 + 810000;//1095000
    //1-40��ÿ������50
    //41-80��ÿ������200
    //81-120��ÿ������500
    default Pair<Integer,Integer> AExp$getFlowAndLevel(){
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
    ////��Ҿ��鲿��
    int getAExp();
    void setAExp(int num);
    int addAExp(int num);
    default void resetAExp(){setAExp(0);}

    ////������ǲ���
    int getSanity();
    void setSanity(int num);
    int addSanity(int num);
    default void addSanity(){addSanity(1);}
    default void resetSanity(){setSanity(getMaxSanity());}
    default int getMaxSanity(){return 70 + AExp$getFlowAndLevel().second / 2;}

//    ////���Ȩ�޲���
//    int getIslandAuthority();
//    void setIslandAuthority(int level);

}
