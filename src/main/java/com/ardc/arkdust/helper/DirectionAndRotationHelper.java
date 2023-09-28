package com.ardc.arkdust.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Rotation;
import org.joml.Vector3i;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DirectionAndRotationHelper {

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

    public static Rotation direction2Rotation(Direction from, Direction to){
        for(Rotation t : Rotation.values()){
            if(t.rotate(from) == to) return t;
        }
        return Rotation.NONE;
    }

    public static Rotation direction2Rotation(Direction from,Direction to,Random random){
        for(Rotation t : Rotation.values()){
            if(t.rotate(from) == to) return t;
        }
        return random==null ? Rotation.NONE : Rotation.getRandom(RandomSource.create(random.nextLong()));
    }

    public static Direction RandomDirection(direcList direcList ,RandomSource r){
        return direcList.getList().get(r.nextInt(direcList.getList().size()));
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
        return structureCenterPos.x - centerPos.getX() >=0 ? 1 : -1;
    }
    public static int pos2FactorInZ(BlockPos centerPos, Vector3i structureCenterPos){
        return structureCenterPos.z - centerPos.getZ() >=0 ? 1 : -1;
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
        return switch (rotation) {
            case CLOCKWISE_90 -> Direction.EAST;
            case CLOCKWISE_180 -> Direction.SOUTH;
            case COUNTERCLOCKWISE_90 -> Direction.WEST;
            case NONE, default -> Direction.NORTH;
        };
    }


}
