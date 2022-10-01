package com.ardc.arkdust.preobject.Block;

import com.ardc.arkdust.preobject.pre.PreBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class StructureAirBlock extends PreBlock {
    public StructureAirBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return VoxelShapes.empty();
    }
}
