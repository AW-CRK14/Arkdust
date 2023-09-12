package com.ardc.arkdust.blocks.terra_industrial;

import com.ardc.arkdust.enums.TechMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class StructureFrameBlock extends IndustrialBlock implements IWaterLoggable {
    private static final BooleanProperty WATERLOGGED = BooleanProperty.create("waterlogged");

    private static final VoxelShape shape;
    static {
        VoxelShape shape1;
        shape1 = VoxelShapes.join(VoxelShapes.block() , Block.box(0,3,3,16,13,13), IBooleanFunction.ONLY_FIRST);
        shape1 = VoxelShapes.join(shape1, Block.box(3,0,3,13,16,13), IBooleanFunction.ONLY_FIRST);
        shape = VoxelShapes.join(shape1 , Block.box(3,3,0,13,13,16), IBooleanFunction.ONLY_FIRST);
    }

    public StructureFrameBlock(Properties properties,TechMaterial material) {
        super(properties,material);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED,false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder){
        builder.add(WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
        return shape;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context){
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
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos){
        if(state.getValue(WATERLOGGED)){
            world.getLiquidTicks().scheduleTick(pos,Fluids.WATER,Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state,facing,facingState,world,pos,facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state){
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override//掉落物为一个自己
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {

        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }
}
