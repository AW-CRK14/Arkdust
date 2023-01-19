package com.ardc.arkdust.capability.health_system.ori_infection;

import net.minecraft.entity.player.PlayerEntity;

public class OIMain {
    public static class WorldOI {
        public boolean ifWorldOIRun(net.minecraft.world.World worldIn){//�������OI�Ƿ�����
            if(!worldIn.isClientSide()){
                WorldOIData data = WorldOIData.get(worldIn);
                return data.getWorldOIState();
            }
            return false;
        }

        public double getWholeOIDataHere(net.minecraft.world.World worldIn, double x, double y, double z, PlayerEntity player){//��ȡ���������λ�õĸ�Ⱦ��ֵ
            if(!worldIn.isClientSide()){
                WorldOIData data = WorldOIData.get(worldIn);
                return data.getEnvironmentOILevel(x, y, z);
            }
            return 0;
        }

        public double getWholeOIDataHere(net.minecraft.world.World worldIn, PlayerEntity player){
            return getWholeOIDataHere(worldIn,player.getX(),player.getY(),player.getZ(),player);
        }
    }

//        public int getBoundary(int level,int state){
//            /*level��С��0ʱ��ȫ������0
//              level=0/1ʱ��ȫ������case3���
//              1<level<=5ʱ��debuff����������һ��
//              level>5ʱ�������ֱ����
//             */
//            switch (state){
//                case 2://��������
//                    if(level > 5) return run1((int) (1.2*level +0.5));
//                case 1://debuff��������
//                    if(level > 1) return run1(level);
//                case 3://�������
//                    if(level >= 0) return run1((int) (1.7*level +0.3));
//                default:
//                    return 0;
//            }
//        }
}
