package com.ardc.arkdust.CodeMigration.RunHelper;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.structure.StructurePiece;

import java.util.List;
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

    public static long posToRandomSeed (BlockPos pos){
        Random r = new Random();
        r.setSeed((long) pos.getX() *pos.getX()-pos.getY()+ (long) pos.getZ() *pos.getY()*pos.getX());
        return r.nextLong();
    }

    public static Random posToRandom (BlockPos pos){
        Random r = new Random();
        r.setSeed(posToRandomSeed(pos));
        return r;
    }

    public static int limitY(int y){
        return Math.max(1,Math.min(y,256));
    }

    public static class PosMoveBag{
        public int x;
        public int y;
        public int z;
        public Direction direction;

        public PosMoveBag(int x,int y,int z){
            this.x = x;
            this.y = y;
            this.z = z;
            this.direction = Direction.NORTH;
        }

        public PosMoveBag(int x,int y,int z,Direction direction){
            this.x = x;
            this.y = y;
            this.z = z;
            this.direction = direction;
        }

        public void setX(int x){
            this.x = x;
        }

        public void setY(int y){
            this.y = y;
        }

        public void setZ(int z){
            this.z = z;
        }

        public void setDirection(Direction direction){
            this.direction = direction;
        }

        public BlockPos posMove(BlockPos pos){
            return pos.offset(this.x,limitY(pos.getY() + this.y),this.z);
        }

        public void pieceMove(StructurePiece piece){
            piece.move(this.x,this.y,this.z);
        }

        public void pieceMove(List<StructurePiece> pieceList){
            pieceList.forEach(piece -> piece.move(this.x,this.y,this.z));
        }

        public void xzExchange(){
            int a = this.x;
            this.x = this.z;
            this.z = a;
        }
    }
}
