package com.ardc.arkdust.CodeMigration.RunHelper;

import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DirectionHelper {

    public enum direcList {
        DIRECTION(Arrays.asList(Direction.UP,Direction.DOWN,Direction.EAST,Direction.WEST,Direction.NORTH,Direction.SOUTH)),
        HORIZON_DIRECTION(Arrays.asList(Direction.EAST,Direction.WEST,Direction.NORTH,Direction.SOUTH));
        private final List<Direction> list;

        direcList(List<Direction> list){
            this.list = list;
        }

        public List<Direction> getList(){
            return this.list;
        }
    }

    public static Direction RandomDirection(direcList direcList ,Random r){
        if(r == null) {
            r = new Random();
        }
        List<Direction> list = direcList.getList();
        int length = list.size();
        int c = r.nextInt(length);
        return direcList.getList().get(c);
    }

    public static PosHelper.PosMoveBag PosDirectionRun(BlockPos centerPos, Vector3i structureCenterPos, PosHelper.PosMoveBag basePos , boolean chuckDirection){
//        PosHelper.PosMoveBag returnPos = new PosHelper.PosMoveBag(0,0,0);
        int xFactor = pos2FactorInX(centerPos,structureCenterPos);
        int zFactor = pos2FactorInZ(centerPos,structureCenterPos);
        Direction toDirection = factor2Direction(xFactor,zFactor);
        basePos.setDirection(toDirection);
        if(toDirection == Direction.NORTH || toDirection == Direction.SOUTH)
            basePos.xzExchange();
        basePos.setX(basePos.x * xFactor);
        basePos.setZ(basePos.z * zFactor);
        return basePos;
    }

    public static PosHelper.PosMoveBag PosDirectionRecount(Rotation rotation, PosHelper.PosMoveBag basePos){
        Direction toDirection = rotation2Direction(rotation);
        int xFactor = direc2FactorInX(toDirection);
        int zFactor = direc2FactorInZ(toDirection);
        basePos.setDirection(toDirection);
        if(toDirection == Direction.NORTH || toDirection == Direction.SOUTH)
            basePos.xzExchange();
        basePos.setX(basePos.x * xFactor);
        basePos.setZ(basePos.z * zFactor);
        return basePos;
    }



    public static int pos2FactorInX(BlockPos centerPos, Vector3i structureCenterPos){
        return structureCenterPos.getX() - centerPos.getX() >=0 ? 1 : -1;
    }
    public static int pos2FactorInZ(BlockPos centerPos, Vector3i structureCenterPos){
        return structureCenterPos.getZ() - centerPos.getZ() >=0 ? 1 : -1;
    }

    public static int direc2FactorInX(Direction direction){
        return (direction == Direction.WEST || direction == Direction.SOUTH) ? 1 : -1;
    }
    public static int direc2FactorInZ(Direction direction){
        return (direction == Direction.NORTH || direction == Direction.EAST) ? 1 : -1;
    }

    public static Direction factor2Direction(int xf , int zf){
//        System.out.println("direction in:" + xf + "," + zf);
        if(xf > 0)
            return zf > 0 ? Direction.WEST : Direction.SOUTH;
        return zf > 0 ? Direction.NORTH : Direction.EAST;
    }

    public static Direction rotation2Direction(Rotation rotation){
        switch (rotation){
            case CLOCKWISE_90:
                return Direction.EAST;
            case CLOCKWISE_180:
                return Direction.SOUTH;
            case COUNTERCLOCKWISE_90:
                return Direction.WEST;
            case NONE:
            default:
                return Direction.NORTH;
        }
    }
}
