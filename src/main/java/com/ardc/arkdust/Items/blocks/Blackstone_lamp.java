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

public class Blackstone_lamp extends DropSelfBlock implements IWaterLoggable {
    private static final BooleanProperty WATERLOGGED = BooleanProperty.create("waterlogged");
    private static final BooleanProperty LIGHTING = BooleanProperty.create("light");

    private static final VoxelShape shape;
    static {
        VoxelShape shape1 = VoxelShapes.join(VoxelShapes.block() , Block.box(0,3,3,16,13,13), IBooleanFunction.ONLY_FIRST);
        shape1 = VoxelShapes.join(shape1 , Block.box(3,3,0,13,13,16), IBooleanFunction.ONLY_FIRST);
        shape = VoxelShapes.join(shape1 , Block.box(5,2,5,11,13,11), IBooleanFunction.OR);
    }

    public Blackstone_lamp() {
        super(Properties
                .of(Material.STONE)
                .harvestTool(ToolType.PICKAXE)
                .strength(2,3)
                .lightLevel((level)->(level.getBlockState().getValue(LIGHTING) ? 13 : 0))
                .noOcclusion()
                .requiresCorrectToolForDrops()
                ,1);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED,false).setValue(LIGHTING,false));
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if((player.getMainHandItem().getItem() == Items.COAL || player.getMainHandItem().getItem() == Items.TORCH || player.getMainHandItem().getItem() == Items.CHARCOAL) && !state.getValue(LIGHTING)) {
            worldIn.setBlock(new BlockPos(pos), state.setValue(LIGHTING, true), 3);
            player.getMainHandItem().shrink(1);
            return ActionResultType.CONSUME;
        }
//        if(!worldIn.isClientSide()) {
//            WorldOIData data = WorldOIData.get(worldIn);
//            player.displayClientMessage(new TranslationTextComponent("pma.oi.worldOIState." + data.getWorldOIState()), false);
//        }
        return ActionResultType.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder){
        builder.add(WATERLOGGED,LIGHTING);
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
}
