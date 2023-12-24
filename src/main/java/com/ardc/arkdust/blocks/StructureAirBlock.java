package com.ardc.arkdust.blocks;

import com.ardc.arkdust.preobject.PreBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StructureAirBlock extends PreBlock {
    public StructureAirBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Shapes.empty();
    }
}
