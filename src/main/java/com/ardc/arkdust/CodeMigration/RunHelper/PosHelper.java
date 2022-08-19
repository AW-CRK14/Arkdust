package com.ardc.arkdust.CodeMigration.RunHelper;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class PosHelper {
    public static BlockPos entityPosToBlock(LivingEntity entity){
        return new BlockPos((int)entity.getX()+0.5,(int)entity.getY(),(int)entity.getZ()+0.5).below();
    }

    public static BlockPos getRandomPosNearPos(BlockPos pos,int xRange,int zRange,int yMin,int yMax){
        Random r = new Random();
        int xAdd = r.nextInt(2 * xRange + 1) - xRange;//正负x位移量计算
        int zAdd = r.nextInt(2 * zRange + 1) - zRange;//正负z位移量计算
        yMin = Math.min(Math.max(2,yMin),225);//y范围重导
        yMax = Math.min(Math.max(yMax,yMin+4),254);
        int y = r.nextInt(yMax-yMin+1)+yMin;//y计算
        return new BlockPos(pos.getX()+xAdd,y,pos.getZ()+zAdd);
    }
}
