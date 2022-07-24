package com.ardc.arkdust.CodeMigration.BlockState;

import com.ardc.arkdust.CodeMigration.pre.PreBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;

import java.util.Collections;
import java.util.List;

public class DropSelfBlock extends PreBlock {
    public final int dropNumber;

    public DropSelfBlock(Properties properties, int number){
        super(properties);
        dropNumber = number;
    }


    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return new ItemStack(Blocks.BRICKS);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {

        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, dropNumber));
    }
}
