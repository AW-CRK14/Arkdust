package com.ardc.arkdust.preobject.BlockState;

import com.ardc.arkdust.preobject.pre.PreBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;

import java.util.Collections;
import java.util.List;

public class DropSelfBlock extends PreBlock {
    public final int dropNumber;

    public DropSelfBlock(Properties properties, int number){
        super(properties);
        dropNumber = number;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {

        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, dropNumber));
    }
}
