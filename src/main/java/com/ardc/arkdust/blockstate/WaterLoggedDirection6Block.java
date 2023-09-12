package com.ardc.arkdust.blockstate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class WaterLoggedDirection6Block extends Direction6Block implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public WaterLoggedDirection6Block(Properties properties, boolean drop) {
        super(properties, drop);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder){
        builder.add(WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context){
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
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos){
//        super.updateShape(state,facing,facingState,world,pos,facingPos);
        if(state.getValue(WATERLOGGED)){
            world.getLiquidTicks().scheduleTick(pos,Fluids.WATER,Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state,facing,facingState,world,pos,facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state){
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
