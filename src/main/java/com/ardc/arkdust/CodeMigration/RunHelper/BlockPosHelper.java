package com.ardc.arkdust.CodeMigration.RunHelper;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;

import java.util.ArrayList;
import java.util.List;

public class BlockPosHelper {
    public static List<BlockPos> getCenterAndSquareVertexPos(BlockPos centerPos,int halfLength,boolean sideCenterPoint,boolean vertexPoint){
        List<BlockPos> posList = new ArrayList<>();
        posList.add(centerPos);
        if(vertexPoint) {
            posList.add(new BlockPos(centerPos.offset(halfLength,0,halfLength)));
            posList.add(new BlockPos(centerPos.offset(halfLength,0,-halfLength)));
            posList.add(new BlockPos(centerPos.offset(-halfLength,0,halfLength)));
            posList.add(new BlockPos(centerPos.offset(-halfLength,0,-halfLength)));
        }
        if(sideCenterPoint) {
            posList.add(new BlockPos(centerPos.offset(halfLength,0,0)));
            posList.add(new BlockPos(centerPos.offset(-halfLength,0,0)));
            posList.add(new BlockPos(centerPos.offset(0,0,halfLength)));
            posList.add(new BlockPos(centerPos.offset(0,0,-halfLength)));
        }
        return posList;
    }

//    public static List<BlockPos> getStructureMarkPoint(MutableBoundingBox box, boolean sideCenterPoint, boolean vertexPoint) {
//        return getCenterAndSquareVertexPos();
//    }

    public static String posInfo(BlockPos pos){
        return "BlockPos:[" + pos.getX() + "," + pos.getY() + "," + pos.getZ() +"]";
    }
}
