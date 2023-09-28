package com.ardc.arkdust.blocks.cworld;

import com.ardc.arkdust.blockstate.DropSelfBlock;
import com.ardc.arkdust.helper.BlockStateHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class CautionLight extends DropSelfBlock implements SimpleWaterloggedBlock {
    private static final BooleanProperty WATERLOGGED = BooleanProperty.create("waterlogged");
    private static final BooleanProperty LIGHTING = BooleanProperty.create("light");

    public CautionLight() {
        super(Properties.copy(Blocks.REDSTONE_LAMP)
                        .lightLevel((level)->(level.getValue(LIGHTING) ? 9 : 0))
                        .noOcclusion()
        );
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED,false).setValue(LIGHTING,false));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        worldIn.setBlock(pos,state.setValue(LIGHTING,!state.getValue(LIGHTING)),3);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(WATERLOGGED,LIGHTING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
        return Shapes.box(0.375F,0,0.375F,0.625F,0.4F,0.625F);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context){
        return BlockStateHelper.waterloggedBlock(super.getStateForPlacement(context),context);
    }

    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos){
        if(state.getValue(WATERLOGGED)){
            world.scheduleTick(pos, Fluids.WATER,Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state,facing,facingState,world,pos,facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state){
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
