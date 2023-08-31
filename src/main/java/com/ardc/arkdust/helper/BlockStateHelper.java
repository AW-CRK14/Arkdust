package com.ardc.arkdust.helper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.ITag;
import net.minecraft.util.math.BlockPos;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class BlockStateHelper {
    public static BlockState waterloggedRotateBlock(Block t, BlockItemUseContext context){
        BlockPos pos = context.getClickedPos();
        BlockState state = context.getLevel().getBlockState(pos);
        if(state.is(t)){
            return t.defaultBlockState().setValue(WATERLOGGED,false).setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
        }else{
            FluidState fluid = context.getLevel().getFluidState(pos);
            return t.defaultBlockState().setValue(WATERLOGGED,fluid.getType() == Fluids.WATER).setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
        }
    }
}
