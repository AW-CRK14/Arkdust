package com.ardc.arkdust.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class BlockStateHelper {
    public static BlockState waterloggedRotateBlock(BlockState t, BlockPlaceContext context){
        return waterloggedBlock(t,context).setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    public static BlockState waterloggedBlock(BlockState t, BlockPlaceContext context){
        BlockPos pos = context.getClickedPos();
        BlockState state = context.getLevel().getBlockState(pos);
        if(state.is(t.getBlock())){
            return t.setValue(WATERLOGGED,false);
        }else{
            FluidState fluid = context.getLevel().getFluidState(pos);
            return t.setValue(WATERLOGGED,fluid.getType() == Fluids.WATER);
        }
    }

//    public interface ISetBlockFromInfoState{
//        void call(ISeedReader world, Template.BlockInfo info,BlockState state);
//    }
}
