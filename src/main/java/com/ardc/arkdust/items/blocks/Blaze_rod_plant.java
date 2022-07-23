package com.ardc.arkdust.items.blocks;

import com.ardc.arkdust.ItemRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public class Blaze_rod_plant extends BushBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0, 0, 0, 16, 5, 16), Block.box(0, 0, 0, 16, 8, 16), Block.box(0, 0, 0, 16, 11, 16), Block.box(0, 0, 0, 16, 14, 16)};

    public Blaze_rod_plant() {
        super(Properties.of(Material.CAKE, MaterialColor.FIRE).noCollission().instabreak().lightLevel((level)->level.getValue(AGE)));
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE_BY_AGE[p_220053_1_.getValue(AGE)];
    }

    @Override
    protected boolean mayPlaceOn(BlockState p_200014_1_, IBlockReader p_200014_2_, BlockPos p_200014_3_) {
        return p_200014_1_.is(Blocks.SOUL_SAND);
    }

    @Override
    public boolean isRandomlyTicking(BlockState p_149653_1_) {
        return p_149653_1_.getValue(AGE) < 3;
    }

    @Override
    public void randomTick(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {
        int i = p_225542_1_.getValue(AGE);
        if (i < 3 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_225542_2_, p_225542_3_, p_225542_1_, p_225542_4_.nextInt(10) == 0)) {
            p_225542_1_ = p_225542_1_.setValue(AGE, Integer.valueOf(i + 1));
            p_225542_2_.setBlock(p_225542_3_, p_225542_1_, 2);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_225542_2_, p_225542_3_, p_225542_1_);
        }

    }

    @Override
    public ItemStack getCloneItemStack(IBlockReader p_185473_1_, BlockPos p_185473_2_, BlockState p_185473_3_) {
        return new ItemStack(ItemRegistry.blaze_rod_plant.get());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(AGE);
    }

    @Override//ÖØÒª£¡
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.NETHER;
    }
}
