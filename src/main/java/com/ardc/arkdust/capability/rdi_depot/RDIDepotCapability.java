package com.ardc.arkdust.capability.rdi_depot;

import com.ardc.arkdust.capability.AbsCapabilityProvider;
import com.ibm.icu.impl.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.network.PacketDistributor;

import java.util.Collections;
import java.util.List;

@AutoRegisterCapability
public class RDIDepotCapability implements AbsCapabilityProvider.CommonEntityCap {
    private CompoundTag nbt;

    public RDIDepotCapability(){
        this.nbt = new CompoundTag();
    }

    public RDIDepotCapability(CompoundTag nbt){
        this.nbt = nbt;
    }

    @Override
    public CompoundTag serializeNBT() {
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.nbt = nbt;
    }

    public CompoundTag getNbt() {
        return nbt;
    }

    public int putObject(DepotObject object, int num){
        nbt.putInt(object.NAME,Math.max((nbt.contains(object.NAME) ? nbt.getInt(object.NAME) : 0) + num,0));
        return nbt.getInt(object.NAME);
    }

    public int getObject(DepotObject object){
        return nbt.getInt(object.NAME);
    }

    public int reduceObject(DepotObject object, int num){
        return putObject(object,-num);
    }

    public void createNBT(CompoundTag nbt) {
        this.nbt = nbt;
    }

    public List<Pair<DepotObject,Integer>> getDepotObjList(){
        List<Pair<DepotObject,Integer>> list = new java.util.ArrayList<>(Collections.emptyList());
        for(DepotObject obj:DepotObject.values()){
            int num = nbt.getInt(obj.NAME);
            if(num > 0){
                list.add(Pair.of(obj,num));
            }
        }
        return list;
    }

    @Override
    public void sendToClient(ServerPlayer player) {
        RDIDepotDataNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new RDIDepotDataNetwork.RDIDepotDataPack(getNbt()));
    }

    public enum DepotObject {
        TEST_OBJ("test_obj"),
        LMB("lmb"),
        ORIGINITE_PRIME("originite_prime"),
        ORUNDUM("orundum"),
        ORIGINIUM_INGOT("originium_ingot");

        public final String NAME;
        DepotObject(String name){
            this.NAME = name;
        }

        public Component getNameTrans(){
            return Component.translatable("depot_obj/" + NAME);
        }

        public static DepotObject getObj(String name){
            for (DepotObject obj : DepotObject.values()){
                if(obj.NAME.equals(name))
                    return obj;
            }
            return null;
        }
    }
}
