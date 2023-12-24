package com.ardc.arkdust.blockstate;

import com.ardc.arkdust.helper.BlockStateHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class WaterLoggedRotateBlock extends RotateBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BooleanProperty.create("waterlogged");

    public WaterLoggedRotateBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED,false));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context){
        return BlockStateHelper.waterloggedRotateBlock(this.defaultBlockState(),context);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos){
//        super.updateShape(state,facing,facingState,world,pos,facingPos);
        if(state.getValue(WATERLOGGED)){
            world.scheduleTick(pos, Fluids.WATER,Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state,facing,facingState,world,pos,facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state){
//        super.getFluidState(state);
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }
}
