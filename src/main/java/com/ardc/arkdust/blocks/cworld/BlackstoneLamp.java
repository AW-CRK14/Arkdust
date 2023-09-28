package com.ardc.arkdust.blocks.cworld;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.blockstate.DropSelfBlock;
import com.ardc.arkdust.helper.BlockStateHelper;
import com.ardc.arkdust.registry.CapabilityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BlackstoneLamp extends DropSelfBlock implements SimpleWaterloggedBlock {
    private static final BooleanProperty WATERLOGGED = BooleanProperty.create("waterlogged");
    private static final BooleanProperty LIGHTING = BooleanProperty.create("light");

    private static final VoxelShape shape;
    static {
        VoxelShape shape1 = Shapes.join(Shapes.block() , Block.box(0,3,3,16,13,13), BooleanOp.ONLY_FIRST);
        shape1 = Shapes.join(shape1 , Block.box(3,3,0,13,13,16), BooleanOp.ONLY_FIRST);
        shape = Shapes.join(shape1 , Block.box(5,2,5,11,13,11), BooleanOp.OR);
    }

    public BlackstoneLamp() {
        super(Properties.copy(Blocks.BLACKSTONE)
                .strength(2,3)
                .lightLevel((level)->(level.getValue(LIGHTING) ? 13 : 0))
                .noOcclusion()
                .requiresCorrectToolForDrops());
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED,false).setValue(LIGHTING,false));
    }

        public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
            if((player.getMainHandItem().getItem() == Items.COAL || player.getMainHandItem().getItem() == Items.TORCH || player.getMainHandItem().getItem() == Items.CHARCOAL) && !blockState.getValue(LIGHTING)) {
                level.setBlock(new BlockPos(pos), blockState.setValue(LIGHTING, true), 3);
                player.getMainHandItem().shrink(1);
                return InteractionResult.CONSUME;
            }
//        if(!worldIn.isClientSide()) {
//            WorldOIData data = WorldOIData.get(worldIn);
//            player.displayClientMessage(new TranslationTextComponent("pma.oi.worldOIState." + data.getWorldOIState()), false);
//        }
            return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(WATERLOGGED,LIGHTING);
        super.createBlockStateDefinition(builder);
    }

    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return shape;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context){
        return BlockStateHelper.waterloggedBlock(super.getStateForPlacement(context),context);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos){
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
