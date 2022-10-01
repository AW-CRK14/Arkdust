package com.ardc.arkdust.CodeMigration.RunHelper;

import com.ibm.icu.impl.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructurePiece;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
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

    public static boolean isEachPlaceAvailable(ChunkGenerator chunkGenerator, Heightmap.Type type, int allowHeightScope, @Nonnull List<BlockPos> pos) {
        if (pos.size() == 0) return false;
        boolean heightTestFlag = allowHeightScope >= 0;//�Ƿ����ø߶�����
        int maxHeight = -1;
        if (heightTestFlag) {
            for (BlockPos fPos : pos) {
                int height = chunkGenerator.getBaseHeight(fPos.getX(), fPos.getZ(), type);

                //���߶�����
                if (maxHeight == -1) maxHeight = height;//��ע���߶�
                else maxHeight = Math.max(height, maxHeight);//�������߶�

                if (height < maxHeight - allowHeightScope)//�߶�С�����߶ȼ�ƫ�Χ
                    return false;

                //y�߶��ж�
                if(height <= 0 || height > 256) return false;
                //����״̬�ж�
                BlockState state = chunkGenerator.getBaseColumn(fPos.getX(), fPos.getY()).getBlockState(new BlockPos(fPos.getX(),Math.max(height-1,1),fPos.getZ()));
                if(!state.getFluidState().isEmpty()) return false;
            }
        }else {
            for (BlockPos fPos : pos) {
                int height = chunkGenerator.getBaseHeight(fPos.getX(), fPos.getZ(), type);
                //y�߶��ж�
                if (height <= 0 || height > 256) return false;
                //����״̬�ж�
                BlockState state = chunkGenerator.getBaseColumn(fPos.getX(), fPos.getY()).getBlockState(new BlockPos(fPos.getX(), Math.max(height - 1, 1), fPos.getZ()));
                if (!state.getFluidState().isEmpty()) return false;
            }
        }
        return true;
    }
}
