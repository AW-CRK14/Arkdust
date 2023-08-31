package com.ardc.arkdust.helper;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.Template;

import javax.annotation.Nonnull;
import java.util.*;

public class StructureHelper {
    public static StructurePiece setRotation(List<StructurePiece> list, int num, Direction direction) {
        StructurePiece piece = list.get(num);
        piece.setOrientation(direction);
        return piece;
    }

    public static BlockPos getEndPos(MutableBoundingBox box, BlockPos beginPos) {
        int x = beginPos.getX();
        int z = beginPos.getZ();
        if (box.x0 == x) x = box.x1;
        if (box.z0 == z) z = box.z1;
        return new BlockPos(x, 64, z);
    }

    public static boolean isEachPlaceAvailable(ChunkGenerator chunkGenerator, Heightmap.Type type, int allowHeightScope, @Nonnull List<BlockPos> pos){
        return isEachPlaceAvailable(chunkGenerator,type,allowHeightScope,pos,false);
    }

    public static void movePieceToCenter(BlockPos centerPos,MutableBoundingBox box,List<StructurePiece> pieceList){
        Vector3i boxCenter = box.getCenter();
        pieceList.forEach((p)->p.move(centerPos.getX()-boxCenter.getX(),0,centerPos.getZ()-boxCenter.getZ()));
    }

    public static boolean isEachPlaceAvailable(ChunkGenerator chunkGenerator, Heightmap.Type type, int allowHeightScope, @Nonnull List<BlockPos> pos,boolean testMode) {
        if (pos.size() == 0) return false;
        boolean heightTestFlag = allowHeightScope >= 0;//是否启用高度限制
//        int maxHeight = -1;
//        if(testMode) System.out.println("#isEachPlaceAvailable testMode load!");
//        if(testMode) System.out.println("heightTestFlag:" + heightTestFlag + "---scope:" + allowHeightScope);
        if (heightTestFlag) {
            List<Integer> heightList = new ArrayList<>();
            for (BlockPos fPos : pos) {
                if(testMode) System.out.println(PosHelper.posInfo(fPos));
                int height = chunkGenerator.getBaseHeight(fPos.getX(), fPos.getZ(), type);
//                if(testMode) System.out.println("    PosSurfaceHeight:" + height);

                //添加位置高度至List
                heightList.add(height);


                //y高度判定
                if(height <= 0 || height > 256) return false;
                //方块状态判定
                BlockState state = chunkGenerator.getBaseColumn(fPos.getX(), fPos.getZ()).getBlockState(new BlockPos(fPos.getX(),Math.max(height-1,1),fPos.getZ()));
//                if(testMode) System.out.println("    BlockStateChuck-Block:" + state.getBlock().toString() + "-Fluid:" + state.getFluidState().toString());
                if(!state.getFluidState().isEmpty()) return false;
            }
            heightList.sort(Comparator.naturalOrder());
            if(testMode) {
//                System.out.println("    HeightList:");
                ListAndMapHelper.printList(heightList);
            }
            if(heightList.isEmpty() || (heightList.get(heightList.size()-1) - heightList.get(0) >allowHeightScope))
                return false;
        }else {
            for (BlockPos fPos : pos) {
                if(testMode) System.out.println(PosHelper.posInfo(fPos));
                int height = chunkGenerator.getBaseHeight(fPos.getX(), fPos.getZ(), type);

//                if(testMode) System.out.println("    PosSurfaceHeight:" + height);
                //y高度判定
                if (height <= 0 || height > 256) return false;
                //方块状态判定
                BlockState state = chunkGenerator.getBaseColumn(fPos.getX(), fPos.getY()).getBlockState(new BlockPos(fPos.getX(), Math.max(height - 1, 1), fPos.getZ()));
//                if(testMode) System.out.println("    BlockStateChuck-Block:" + state.getBlock().toString() + "-Fluid:" + state.getFluidState().toString());
                if (!state.getFluidState().isEmpty()) return false;
            }
        }
//        if(testMode) System.out.println("[return]Chuck finish:each pos is valuable" );
        return true;
    }

    public static boolean isEachPlaceWater(ChunkGenerator chunkGenerator, @Nonnull List<BlockPos> posList){
        for (BlockPos pos : posList){
            int height = chunkGenerator.getBaseHeight(pos.getX(), pos.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
            FluidState state = chunkGenerator.getBaseColumn(pos.getX(), pos.getZ()).getFluidState(new BlockPos(pos.getX(),Math.max(height-1,1),pos.getZ()));
            if(state.getType() != Fluids.WATER) return false;
        }
        return true;
    }

    public static Template.BlockInfo setTemplateBlock(Template.BlockInfo info,BlockState state){
        return new Template.BlockInfo(info.pos,state,info.nbt);
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

        public ResourceLocation getRandomPart(Random r){
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
                Random newR = new Random(r.nextLong());
                return VARIATION_STRUCTURE[newR.nextInt(VARIATION_STRUCTURE.length)];
            }
        }
    }
}
