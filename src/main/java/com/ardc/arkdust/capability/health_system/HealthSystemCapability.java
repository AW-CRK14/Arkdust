package com.ardc.arkdust.capability.health_system;


import com.ardc.arkdust.Utils;
import com.ardc.arkdust.capability.AbsCapabilityProvider;
import com.ardc.arkdust.capability.rdi_auth.RDIAccountAuthDataNetwork;
import com.ibm.icu.impl.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.network.PacketDistributor;

@AutoRegisterCapability
public class HealthSystemCapability implements AbsCapabilityProvider.CommonEntityCap {
    public static final int tendencyLevel = 6;
    public static final int tendencyPoint = ORI$level2Point(tendencyLevel);

    public void sendToClient(ServerPlayer entity) {
        CompoundTag tag = new CompoundTag();
        deserializeNBT(tag);
        HealthSystemDataNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> entity), new HealthSystemDataNetwork.Pack(tag));
        Utils.LOGGER.debug("[ArdNetwork-HealthSystem]Player#" + entity.getName().getString() + " send pack from server");
        Utils.LOGGER.debug("[ArdNetwork-HealthSystem]HealthSystem in server:" + this);
    }



    private int oriPoint;
    private Pair<Integer,Integer> pointToLevel = Pair.of(1,0);//first is level,second is flow point
    public static final int MAX_ORINFEC_LEVEL = 16;
    public static final int MAX_ORINFEC_POINT = ORI$level2Point(MAX_ORINFEC_LEVEL);
    public static int ORI$level2Point(int level){
        return 50*level*(level+1)*(2*level+1)/6;
    }
    public static Pair<Integer,Integer> ORI$point2Level(int point){
        int last = 0;
        for (int i = 1 ; i < 16 ;i ++){
            int p = ORI$level2Point(i);
            if(p > point)
                return Pair.of(i,point-last);
            last = p;
        }
        return Pair.of(16,0);
    }

    public Pair<Integer,Integer> ORI$point2Level(){
        return pointToLevel;
    }

    public void ORI$prepare(){
        this.pointToLevel = ORI$point2Level(this.oriPoint);
    }

    public void rebirth(){
        ORI$addPoint((int) ((tendencyPoint-oriPoint)*0.1F) + 5 * pointToLevel.first);
    }

    public HealthSystemCapability(){
        this.oriPoint = 0;
    }

    public int ORI$getPoint() {
        return oriPoint;
    }

    public int ORI$addPoint(int num) {
        ORI$setPoint(oriPoint+num);
        return oriPoint;
    }

    public void ORI$setPoint(int num) {
        oriPoint = Math.max(0,Math.min(num, MAX_ORINFEC_POINT));
        ORI$prepare();
    }



    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("ori_point", oriPoint);
        return nbt;
    }

    public void deserializeNBT(CompoundTag nbt) {
        oriPoint = nbt.getInt("ori_point");
        ORI$prepare();
    }

    @Override
    public String toString() {
        return "HealthSystemCapability{ORI:{" +
                "point=" + oriPoint +
                "}}";
    }
}
