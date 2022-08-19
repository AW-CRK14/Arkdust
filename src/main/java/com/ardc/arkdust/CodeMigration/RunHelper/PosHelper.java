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
        int xAdd = r.nextInt(2 * xRange + 1) - xRange;//����xλ��������
        int zAdd = r.nextInt(2 * zRange + 1) - zRange;//����zλ��������
        yMin = Math.min(Math.max(2,yMin),225);//y��Χ�ص�
        yMax = Math.min(Math.max(yMax,yMin+4),254);
        int y = r.nextInt(yMax-yMin+1)+yMin;//y����
        return new BlockPos(pos.getX()+xAdd,y,pos.getZ()+zAdd);
    }
}
