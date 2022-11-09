package com.ardc.arkdust.Items.blocks;

import com.ardc.arkdust.preobject.BlockState.DropSelfBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class CautionLight extends DropSelfBlock implements IWaterLoggable {
    private static final BooleanProperty WATERLOGGED = BooleanProperty.create("waterlogged");
    private static final BooleanProperty LIGHTING = BooleanProperty.create("light");
    private final DyeColor COLOR;

    public CautionLight() {
        super(Properties
                        .of(Material.STONE)
                        .harvestTool(ToolType.PICKAXE)
                        .lightLevel((level)->(level.getBlockState().getValue(LIGHTING) ? 9 : 0))
                        .noOcclusion()
                ,1);
        this.COLOR = null;
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED,false).setValue(LIGHTING,false));
    }

    public CautionLight(DyeColor color) {
        super(Properties
                        .of(Material.STONE)
                        .strength(1)
                        .lightLevel((level)->(level.getBlockState().getValue(LIGHTING) ? 9 : 0))
                        .noOcclusion()
                ,1);
        this.COLOR = color;
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED,false).setValue(LIGHTING,false));
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        worldIn.setBlock(pos,state.setValue(LIGHTING,!state.getValue(LIGHTING)),3);
        return ActionResultType.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder){
        builder.add(WATERLOGGED,LIGHTING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
        return VoxelShapes.box(0.375F,0,0.375F,0.625F,0.4F,0.625F);
    }

    @Nullable
    public DyeColor getColor() {
        return this.COLOR;
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
}
