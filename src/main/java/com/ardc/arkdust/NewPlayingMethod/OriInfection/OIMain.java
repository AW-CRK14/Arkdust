package com.ardc.arkdust.NewPlayingMethod.OriInfection;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

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
                return data.getEnvironmentOILevel(x, y, z) + new OIMain.EntityOI().getLevelAndPoint(player,worldIn).get(0);
            }
            return 0;
        }

        public double getWholeOIDataHere(net.minecraft.world.World worldIn, PlayerEntity player){
            return getWholeOIDataHere(worldIn,player.getX(),player.getY(),player.getZ(),player);
        }

        public void tryAddWorldOIPoint(World worldIn,double addNum){//测试用
            if(!worldIn.isClientSide()){
                WorldOIData data = WorldOIData.get(worldIn);
                data.setWorldOILevelVariableForTest(worldIn,addNum);
            }
        }

        public double getWorldOILevelConstant(World worldIn){
            if(!worldIn.isClientSide()){
                WorldOIData data = WorldOIData.get(worldIn);
                return data.getWorldOILevelConstant();
            }
            return 0;
        }

        public double getWorldOILevelVariable(World worldIn){
            if(!worldIn.isClientSide()){
                WorldOIData data = WorldOIData.get(worldIn);
                return data.getWorldOILevelVariable();
            }
            return 0;
        }
    }


    public static class EntityOI{//玩家源石相关
        //获取玩家源石抗性
        public int getPlayerOIResistanceLevel(PlayerEntity entity,World world){
            if(!world.isClientSide()) {
                WorldOIData data = WorldOIData.get(world);
                data.getPlayerOIRLevel(entity);
            }
            return 0;
        }
        @Deprecated
        //设置玩家源石抗性
        public void setPlayerOIResistanceLevel(PlayerEntity entity,int level){
            entity.serializeNBT().putInt("OIResistanceLevel",level);
        }
        @Deprecated
        //提高玩家源石抗性
        public void addPlayerOIResistanceLevel(PlayerEntity entity,int addLevel){
            int level = entity.serializeNBT().getInt("OIResistanceLevel");
            entity.serializeNBT().putInt("OIResistanceLevel",level + addLevel);
        }


        public int getPlayerOIPoint(PlayerEntity entity,World worldIn){
            if(!worldIn.isClientSide()){
                WorldOIData data = WorldOIData.get(worldIn);
                return data.getPlayerOIPoint(entity);
            }
            return 0;
        }

        public void addPlayerOIPoint(Entity entity, int point){
            World world = entity.level;
            if(entity instanceof PlayerEntity && !world.isClientSide) {
                WorldOIData data = WorldOIData.get(world);
                data.addPlayerOIPoint((PlayerEntity) entity,point);
            }
        }

        public void setPlayerOIPoint(Entity entity, int point){
            World world = entity.level;
            if(entity instanceof PlayerEntity && !world.isClientSide) {
                WorldOIData data = WorldOIData.get(world);
                data.setPlayerOIPoint((PlayerEntity) entity,point);
            }
        }

        @Deprecated
        public void minusPlayerOIPoint(PlayerEntity entity,int point){
            int p = entity.serializeNBT().getInt("OIPoint");
            entity.serializeNBT().putInt("OIPoint",Math.max(0,p-point));
        }

        public int getBoundary(int level,int state){
            /*level在小于0时，全部返回0
              level=0/1时，全部返回case3结果
              1<level<=5时，debuff爆发区间与一致
              level>5时，三个分别计算
             */
            switch (state){
                case 2://趋近区间
                    if(level > 5) return run1((int) (1.2*level +0.5));
                case 1://debuff爆发区间
                    if(level > 1) return run1(level);
                case 3://最大区间
                    if(level >= 0) return run1((int) (1.7*level +0.3));
                default:
                    return 0;
            }
        }

        public List<Integer> getLevelAndPointByData(PlayerEntity entity,World world){
            List<Integer> list = new ArrayList<>();
            if(!world.isClientSide()){
                WorldOIData data = WorldOIData.get(world);
                return data.getOIDataList(entity);
            }
            return null;
        }
        public List<Integer> getLevelAndPoint(PlayerEntity entity,World world){
            int point = getPlayerOIPoint(entity,world);
            return run2(point);
        }
        public List<Integer> run2(int point){
            List<Integer> list = new ArrayList<>();
            int level;
            int levelToPoint;
            int levelToPoint2;
            for(level = 0;true;level++){
                levelToPoint = run1(level);
                levelToPoint2 = run1(level+1);
                if(levelToPoint <= point && levelToPoint2 > point) break;
            }
            list.add(0,level);
            list.add(1,point-levelToPoint);//溢出点数
            list.add(2,levelToPoint2-point);//升级缺失点数
            list.add(3,point);
            return list;
        }
        public int run1(int level){
            return level*level*16 + 128*level;
        }

    }

    public static void seedOIData(PlayerEntity player){
        net.minecraft.world.World worldIn = player.level;
        if(!worldIn.isClientSide()){
            //世界源石感染状态
            player.displayClientMessage(new TranslationTextComponent("pma.oi.worldOIState." + new WorldOI().ifWorldOIRun(worldIn)), false);
            //世界源石感染数据
            player.displayClientMessage(new TranslationTextComponent("pma.oi.worldOILevel",new WorldOI().getWholeOIDataHere(worldIn,player),new WorldOI().getWorldOILevelConstant(worldIn),new  WorldOI().getWorldOILevelVariable(worldIn)), false);
            List<Integer> list = new OIMain.EntityOI().getLevelAndPoint(player,worldIn);
            //玩家源石感染数据
            player.displayClientMessage(new TranslationTextComponent("pma.oi.playerOIInfo",list.get(0),list.get(3),list.get(1),list.get(2),new OIMain.EntityOI().run1(list.get(0)),new OIMain.EntityOI().getPlayerOIResistanceLevel(player,worldIn)), false);

        }
    }
}
