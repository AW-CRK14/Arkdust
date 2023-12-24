package com.ardc.arkdust.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Consumer;

public class StructureHelper {

    @Deprecated
    public static boolean checkPosNoWater(Structure.GenerationContext context, BlockPos pos){
        int yWorld = context.chunkGenerator().getBaseHeight(pos.getX(),pos.getZ(), Heightmap.Types.WORLD_SURFACE_WG,context.heightAccessor(),context.randomState());
        return context.chunkGenerator().getBaseColumn(pos.getX(), pos.getZ(),context.heightAccessor(),context.randomState()).getBlock(yWorld-1).getFluidState().isEmpty();
    }
    @Deprecated
    public static StructurePiece setRotation(List<StructurePiece> list, int num, Direction direction) {
        StructurePiece piece = list.get(num);
        piece.setOrientation(direction);
        return piece;
    }
    @Deprecated
    public static BlockPos getEndPos(BoundingBox box, BlockPos beginPos) {
        int x = beginPos.getX();
        int z = beginPos.getZ();
        if (box.minX() == x) x = box.maxX();
        if (box.minZ() == z) z = box.maxZ();
        return new BlockPos(x, 64, z);
    }

    public static void movePieceToCenter(BlockPos centerPos, BoundingBox box, List<StructurePiece> pieceList){
        BlockPos boxCenter = box.getCenter();
        pieceList.forEach((p)->p.move(centerPos.getX()-boxCenter.getX(),0,centerPos.getZ()-boxCenter.getZ()));
    }
    @Deprecated
    public static boolean isEachPlaceAvailable(ChunkGenerator chunkGenerator, Heightmap.Types type, int allowHeightScope, @Nonnull List<BlockPos> pos, LevelHeightAccessor accessor, RandomState randomState) {
        if (pos.size() == 0) return false;
        boolean heightTestFlag = allowHeightScope >= 0;//是否启用高度限制
        if (heightTestFlag) {
            List<Integer> heightList = new ArrayList<>();
            for (BlockPos fPos : pos) {
                int height = chunkGenerator.getBaseHeight(fPos.getX(), fPos.getZ(), type,accessor,randomState);
                //添加位置高度至List
                heightList.add(height);
                //y高度判定
                if(height <= 0 || height > 256) return false;
                //方块状态判定
                BlockState state = chunkGenerator.getBaseColumn(fPos.getX(), fPos.getY(),accessor,randomState).getBlock(Math.max(height - 1, -63));
                if(!state.getFluidState().isEmpty()) return false;
            }
            heightList.sort(Comparator.naturalOrder());
            return !heightList.isEmpty() && (heightList.get(heightList.size() - 1) - heightList.get(0) <= allowHeightScope);
        }else {
            for (BlockPos fPos : pos) {
                int height = chunkGenerator.getBaseHeight(fPos.getX(), fPos.getZ(), type,accessor,randomState);

                //y高度判定
                if (height <= 0 || height > 256) return false;
                //方块状态判定
                BlockState state = chunkGenerator.getBaseColumn(fPos.getX(), fPos.getY(),accessor,randomState).getBlock(Math.max(height - 1, -63));
                if (!state.getFluidState().isEmpty()) return false;
            }
        }
        return true;
    }

    @Deprecated
    public static boolean isEachPlaceWater(ChunkGenerator chunkGenerator, @Nonnull List<BlockPos> posList, LevelHeightAccessor accessor, RandomState randomState){
        for (BlockPos pos : posList){
            int height = chunkGenerator.getBaseHeight(pos.getX(), pos.getZ(), Heightmap.Types.WORLD_SURFACE_WG,accessor,randomState);
            BlockState state = chunkGenerator.getBaseColumn(pos.getX(), pos.getZ(),accessor,randomState).getBlock(Math.max(height-1,-63));
            if(!state.getFluidState().isEmpty()) return false;
        }
        return true;
    }

    public static StructureTemplate.StructureBlockInfo setTemplateBlock(StructureTemplate.StructureBlockInfo info, BlockState state){
        return new StructureTemplate.StructureBlockInfo(info.pos(),state,info.nbt());
    }
    public static class StructureAndVariation{
        public final ResourceLocation MAIN_STRUCTURE;
        public ResourceLocation[] VARIATION_STRUCTURE;
        public float possibility;

        public StructureAndVariation(String modId,String path){
            this.MAIN_STRUCTURE = new ResourceLocation(modId,path);
            this.possibility = 1;
        }

        @Deprecated
        public StructureAndVariation(String modId,String mainPath,float possibility,String... variationPath){
            this.MAIN_STRUCTURE = new ResourceLocation(modId,mainPath);
            this.possibility = possibility;
        }

        @Deprecated
        public StructureAndVariation(String modId,String mainPath,String... variationPath){
            this.MAIN_STRUCTURE = new ResourceLocation(modId,mainPath);
//            this.VARIATION_STRUCTURE  = Collections.emptyList();
//            for(String s : variationPath){
//                VARIATION_STRUCTURE.add(new ResourceLocation(modId,s));
//            }
            this.possibility = -1;
        }

        public StructureAndVariation(ResourceLocation main){
            this.MAIN_STRUCTURE = main;
            this.possibility = 1;
        }

        public StructureAndVariation(ResourceLocation main,float possibility,ResourceLocation[] variation){
            this.MAIN_STRUCTURE = main;
            this.VARIATION_STRUCTURE = variation;
            this.possibility = possibility;
        }

        public StructureAndVariation(ResourceLocation main,ResourceLocation[] variation){
            this.MAIN_STRUCTURE = main;
            this.VARIATION_STRUCTURE = variation;
            this.possibility = -1;
        }

        @Deprecated
        public void addVariation(ResourceLocation location){
//            if(VARIATION_STRUCTURE!=null){
//                VARIATION_STRUCTURE.add(location);
//            }else {
//                VARIATION_STRUCTURE = Collections.singletonList(location);
//            }
        }

        public void setPossibility(float possibility){
            this.possibility = possibility;
        }

        public ResourceLocation getRandomPart(RandomSource r){
            if(VARIATION_STRUCTURE == null || VARIATION_STRUCTURE.length == 0 || possibility >= 1){//只有Main的时候
                return MAIN_STRUCTURE;
            }else if(possibility == -1){//possibility取-1时，平均分配可能性
                int i = r.nextInt(VARIATION_STRUCTURE.length + 1);
                if(i == VARIATION_STRUCTURE.length)
                    return MAIN_STRUCTURE;
                return VARIATION_STRUCTURE[i];
            }else {//按比重分配Main
                if(r.nextFloat() <= possibility)
                    return MAIN_STRUCTURE;
                return VARIATION_STRUCTURE[r.nextInt(VARIATION_STRUCTURE.length)];
            }
        }
    }

    public static BlockPos withPosAtChunkCenter(Structure.GenerationContext context,Heightmap.Types type){
        ChunkPos chunkpos = context.chunkPos();
        int i = chunkpos.getMiddleBlockX();
        int j = chunkpos.getMiddleBlockZ();
        int k = context.chunkGenerator().getFirstOccupiedHeight(i, j, type, context.heightAccessor(), context.randomState());
        return new BlockPos(i,k,j);
    }

    public static void forEachVer(BoundingBox box, Consumer<BlockPos> posConsumer){
        posConsumer.accept(new BlockPos(box.minX(),box.minY(),box.minZ()));
        posConsumer.accept(new BlockPos(box.maxX(),box.minY(),box.minZ()));
        posConsumer.accept(new BlockPos(box.minX(),box.minY(),box.maxZ()));
        posConsumer.accept(new BlockPos(box.maxX(),box.minY(),box.maxZ()));
    }
}
