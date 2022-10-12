package com.ardc.arkdust.CodeMigration.RunHelper;

import com.ibm.icu.impl.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.plaf.basic.BasicListUI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        Vector3i boxCenterV3i = box.getCenter();
        BlockPos boxCenter;
        if(boxCenterV3i instanceof BlockPos){
            boxCenter = (BlockPos) boxCenterV3i;
        }else {
            boxCenter = new BlockPos(boxCenterV3i.getX(),boxCenterV3i.getY(),boxCenterV3i.getZ());
        }
//        PosHelper.PosMoveBag moveBag = new PosHelper.PosMoveBag(centerPos.getX()-boxCenter.getX(),0, centerPos.getZ()-boxCenter.getZ());
        new PosHelper.PosMoveBag(centerPos.getX()-boxCenter.getX(),0, centerPos.getZ()-boxCenter.getZ()).pieceMove(pieceList);
    }

    public static boolean isEachPlaceAvailable(ChunkGenerator chunkGenerator, Heightmap.Type type, int allowHeightScope, @Nonnull List<BlockPos> pos,boolean testMode) {
        if (pos.size() == 0) return false;
        boolean heightTestFlag = allowHeightScope >= 0;//是否启用高度限制
//        int maxHeight = -1;
        if(testMode) System.out.println("#isEachPlaceAvailable testMode load!");
        if(testMode) System.out.println("heightTestFlag:" + heightTestFlag + "---scope:" + allowHeightScope);
        if (heightTestFlag) {
            List<Integer> heightList = new ArrayList<>();
            for (BlockPos fPos : pos) {
                if(testMode) System.out.println(BlockPosHelper.posInfo(fPos));
                int height = chunkGenerator.getBaseHeight(fPos.getX(), fPos.getZ(), type);
                if(testMode) System.out.println("    PosSurfaceHeight:" + height);

                //添加位置高度至List
                heightList.add(height);


                //y高度判定
                if(height <= 0 || height > 256) return false;
                //方块状态判定
                BlockState state = chunkGenerator.getBaseColumn(fPos.getX(), fPos.getZ()).getBlockState(new BlockPos(fPos.getX(),Math.max(height-1,1),fPos.getZ()));
                if(testMode) System.out.println("    BlockStateChuck-Block:" + state.getBlock().toString() + "-Fluid:" + state.getFluidState().toString());
                if(!state.getFluidState().isEmpty()) return false;
            }
            heightList.sort(Comparator.naturalOrder());
            if(testMode) {
                System.out.println("    HeightList:");
                PrintHelper.printList(heightList);
            }
            if(heightList.isEmpty() || (heightList.get(heightList.size()-1) - heightList.get(0) >allowHeightScope))
                return false;
        }else {
            for (BlockPos fPos : pos) {
                if(testMode) System.out.println(BlockPosHelper.posInfo(fPos));
                int height = chunkGenerator.getBaseHeight(fPos.getX(), fPos.getZ(), type);

                if(testMode) System.out.println("    PosSurfaceHeight:" + height);
                //y高度判定
                if (height <= 0 || height > 256) return false;
                //方块状态判定
                BlockState state = chunkGenerator.getBaseColumn(fPos.getX(), fPos.getY()).getBlockState(new BlockPos(fPos.getX(), Math.max(height - 1, 1), fPos.getZ()));
                if(testMode) System.out.println("    BlockStateChuck-Block:" + state.getBlock().toString() + "-Fluid:" + state.getFluidState().toString());
                if (!state.getFluidState().isEmpty()) return false;
            }
        }
        if(testMode) System.out.println("[return]Chuck finish:each pos is valuable" );
        return true;
    }
}
