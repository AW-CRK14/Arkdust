package com.ardc.arkdust.blocks.cworld;

import com.ardc.arkdust.helper.LootHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.List;

public class HardGlassBlock extends GlassBlock {

    public HardGlassBlock(BlockBehaviour.Properties properties){
        super(properties);
    }

    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return LootHelper.dropSelfWhenNoLoot(state,builder);
    }
}