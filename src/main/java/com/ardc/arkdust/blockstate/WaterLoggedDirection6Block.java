package com.ardc.arkdust.blockstate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class WaterLoggedDirection6Block extends Direction6Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public WaterLoggedDirection6Block(Properties properties, boolean drop) {
        super(properties, drop);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context){
//        super.getStateForPlacement(context);
        BlockPos pos = context.getClickedPos();
        BlockState state = super.getStateForPlacement(context);
        if(state.is(this)){
            return state.setValue(WATERLOGGED,false);
        }else{
            FluidState fluid = context.getLevel().getFluidState(pos);
            return state.setValue(WATERLOGGED,fluid.getType() == Fluids.WATER);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos){
//        super.updateShape(state,facing,facingState,world,pos,facingPos);
        if(state.getValue(WATERLOGGED)){
            world.scheduleTick(pos,Fluids.WATER,Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state,facing,facingState,world,pos,facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state){
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
