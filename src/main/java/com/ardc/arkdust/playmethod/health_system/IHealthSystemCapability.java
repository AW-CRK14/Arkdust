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

    ////��Ⱦ������ز���
    //��ȡ��ǰ��Ⱦ����
    int ORI$getPoint();
    //��ȡ��ǰ��Ⱦ�ȼ�  firstΪ������� secondΪ��Ⱦ����
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
    //���Ӹ�Ⱦ����
    int ORI$addPoint(int num);
    //���ø�Ⱦ����
    void ORI$setPoint(int num);
    //���ø�Ⱦ����
    default void ORI$resetPoint(){ ORI$setPoint(0); }
    ////��Ⱦ���Եȼ����
    //��ȡ���Եȼ�
    byte ORI$getRLevel();
    //�жϵȼ��Ƿ����
    default boolean ORI$isRLevelValuable(int level){
        return level <= ORI$getRLevel() + 1;
    }
    //�������Եȼ�
    default void ORI$addRLevel(){ ORI$addRLevel((byte) 1);}
    void ORI$addRLevel(byte level);
    //���ÿ��Եȼ�
    void ORI$setRLevel(byte level);
    //���ÿ��Եȼ�
    default void ORI$resetRLevel(){ ORI$setRLevel((byte) 0);}


    ////����
    //��Ⱦ����״̬��
    //boolean getORICR();
    //���������Ⱦ����
    //void setORISR();
    //��ȡ�����Ⱦ����
    //<?> getORISR();
    //���������Ⱦ����
    //void resetORISR();
}
