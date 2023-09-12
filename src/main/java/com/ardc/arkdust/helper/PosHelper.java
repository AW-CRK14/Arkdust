package com.ardc.arkdust.helper;

import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.StructurePiece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PosHelper {
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

    public static BlockPos entityPosToBlock(LivingEntity entity){
        return new BlockPos(Math.floor(entity.getX()),(int)entity.getY(),Math.floor(entity.getZ())).below();
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
        Random r = new Random((long) pos.getX() *pos.getX()-pos.getY()+ (long) pos.getZ() *pos.getY()*pos.getX());
        return r.nextLong();
    }

    public static int posAdd(BlockPos pos){
        return pos.getX() + pos.getY() + pos.getZ();
    }

    public static Random posToRandom (BlockPos pos){
        return new Random(posToRandomSeed(pos));
    }

    public static int limitY(int y){
        return Math.max(1,Math.min(y,256));
    }

    public static Pair<BlockPos,BlockPos> boundingBoxToBlockPos(MutableBoundingBox box){
        return Pair.of(new BlockPos(box.x0,box.y0,box.z0),new BlockPos(box.x1,box.y1,box.z1));
    }

    public static Pair<ChunkPos,ChunkPos> boundingBoxToChunkPos(MutableBoundingBox box){
        Pair<BlockPos,BlockPos> p = boundingBoxToBlockPos(box);
        return Pair.of(new ChunkPos(p.getFirst()),new ChunkPos(p.getSecond()));
    }

    public static List<BlockPos> createBox(int x,int y,int z){
        if(x<0||y<0||z<0) return Collections.EMPTY_LIST;
        List<BlockPos> list = new ArrayList<>();
        for(int a=0;a<=x;a++){
            for(int b=0;b<=y;b++){
                for(int c=0;c<=z;c++){
                    list.add(new BlockPos(a,b,c));
                }
            }
        }
        return list;
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
