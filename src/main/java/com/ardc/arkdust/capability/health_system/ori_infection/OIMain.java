package com.ardc.arkdust.capability.health_system.ori_infection;

import net.minecraft.entity.player.PlayerEntity;

public class OIMain {
    public static class WorldOI {
        public boolean ifWorldOIRun(net.minecraft.world.World worldIn){//检测世界OI是否启用
            if(!worldIn.isClientSide()){
                WorldOIData data = WorldOIData.get(worldIn);
                return data.getWorldOIState();
            }
            return false;
        }

        public double getWholeOIDataHere(net.minecraft.world.World worldIn, double x, double y, double z, PlayerEntity player){//获取此玩家所在位置的感染总值
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
//            /*level在小于0时，全部返回0
//              level=0/1时，全部返回case3结果
//              1<level<=5时，debuff爆发区间与一致
//              level>5时，三个分别计算
//             */
//            switch (state){
//                case 2://趋近区间
//                    if(level > 5) return run1((int) (1.2*level +0.5));
//                case 1://debuff爆发区间
//                    if(level > 1) return run1(level);
//                case 3://最大区间
//                    if(level >= 0) return run1((int) (1.7*level +0.3));
//                default:
//                    return 0;
//            }
//        }
}
