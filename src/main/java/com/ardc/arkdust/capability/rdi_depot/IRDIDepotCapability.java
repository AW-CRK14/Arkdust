package com.ardc.arkdust.capability.rdi_depot;

import com.ibm.icu.impl.Pair;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

public interface IRDIDepotCapability extends INBTSerializable<CompoundNBT> {
    default void sendPackToClient(ServerPlayerEntity entity) {
//        Utils.LOGGER.info("nbt:" + getNbt() + ",entity:" + entity + ",instance:" + RDIDepotDataNetwork.INSTANCE);
        RDIDepotDataNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> entity), new RDIDepotDataNetwork.RDIDepotDataPack(getNbt()));
//        Utils.LOGGER.info("Player#" + entity.getName().getString() + " send pack from server");
//        Utils.LOGGER.info("RDIAccAuthCapInServer:" + this.toString());
    }

    default void testNBT(){
        if(getNbt() == null)
            createNBT(new CompoundNBT());
    }

    CompoundNBT getNbt();

    int putObject(RDIDepotCapability.DepotObject object, int num);

    int getObject(RDIDepotCapability.DepotObject object);

    int reduceObject(RDIDepotCapability.DepotObject object, int num);

    void createNBT(CompoundNBT nbt);

    List<Pair<RDIDepotCapability.DepotObject,Integer>> getDepotObjList();


}
