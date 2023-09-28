package com.ardc.arkdust.blocks.terra_industrial;

import com.ardc.arkdust.enums.TechMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class StructureFrameBlock extends IndustrialBlock implements SimpleWaterloggedBlock {
    private static final BooleanProperty WATERLOGGED = BooleanProperty.create("waterlogged");

    private static final VoxelShape shape;
    static {
        VoxelShape shape1;
        shape1 = Shapes.join(Shapes.block() , Block.box(0,3,3,16,13,13), BooleanOp.ONLY_FIRST);
        shape1 = Shapes.join(shape1, Block.box(3,0,3,13,16,13), BooleanOp.ONLY_FIRST);
        shape = Shapes.join(shape1 , Block.box(3,3,0,13,13,16), BooleanOp.ONLY_FIRST);
    }

    public StructureFrameBlock(Properties properties,TechMaterial material) {
        super(properties,material);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED,false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
        return shape;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context){
        BlockPos pos = context.getClickedPos();
        BlockState state = context.getLevel().getBlockState(pos);
        if(state.is(this)){
            return defaultBlockState().setValue(WATERLOGGED,false);
        }else{
            FluidState fluid = context.getLevel().getFluidState(pos);
            return this.defaultBlockState().setValue(WATERLOGGED,fluid.getType() == Fluids.WATER);
        }
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

    @Override//掉落物为一个自己
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {

        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }
}
