package com.ardc.arkdust.playmethod.health_system;

import com.ibm.icu.impl.Pair;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.network.PacketDistributor;

public interface IHealthSystemCapability extends INBTSerializable<CompoundNBT> {
    default void sendPackToClient(ServerPlayerEntity entity){
        HealthSystemDataNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(()-> entity), new HealthSystemDataNetwork.HealthSystemDataPack(ORI$getRLevel(),ORI$getPoint()));
//                Utils.LOGGER.debug("Player#" + event.getPlayer().getName().getString() + " send pack from server");
//                Utils.LOGGER.debug("ORICapInServer:" + i.toString());
    }

    ////感染点数相关部分
    //获取当前感染点数
    int ORI$getPoint();
    //获取当前感染等级  first为溢出点数 second为感染评级
    default Pair<Integer,Integer> ORI$getLevelAndOverflow(){
        int overflow = ORI$getPoint();
        int level = 0;
        int level2Point;
        while (true) {
            level2Point = 100 + 20 * level;
            if (overflow - level2Point < 0) {
                return Pair.of(overflow,level);
            }else {
                level += 1;
                overflow -= level2Point;
            }
        }
    }
    //增加感染点数
    int ORI$addPoint(int num);
    //设置感染点数
    void ORI$setPoint(int num);
    //重置感染点数
    default void ORI$resetPoint(){ ORI$setPoint(0); }
    ////感染抗性等级相关
    //获取抗性等级
    byte ORI$getRLevel();
    //判断等级是否可行
    default boolean ORI$isRLevelValuable(int level){
        return level <= ORI$getRLevel() + 1;
    }
    //提升抗性等级
    default void ORI$addRLevel(){ ORI$addRLevel((byte) 1);}
    void ORI$addRLevel(byte level);
    //设置抗性等级
    void ORI$setRLevel(byte level);
    //重置抗性等级
    default void ORI$resetRLevel(){ ORI$setRLevel((byte) 0);}


    ////杂项
    //感染保护状态？
    //boolean getORICR();
    //设置特殊感染忽略
    //void setORISR();
    //获取特殊感染忽略
    //<?> getORISR();
    //重置特殊感染忽略
    //void resetORISR();
}
