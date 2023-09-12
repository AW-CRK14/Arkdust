package com.ardc.arkdust.blockstate;

import com.ardc.arkdust.helper.LootHelper;
import com.ardc.arkdust.preobject.PreBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;

import java.util.Collections;
import java.util.List;

public class DropSelfWhenSilkTouchBlock extends PreBlock {
    public final IItemStackProviderWithLootContest i;
    public DropSelfWhenSilkTouchBlock(Properties properties, IItemStackProviderWithLootContest itemStackSupplier){
        super(properties);
        i = itemStackSupplier;
    }

    public interface IItemStackProviderWithLootContest{
        ItemStack get(LootContext context);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {

        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        LootContext context = builder.withParameter(LootParameters.BLOCK_STATE, state).create(LootParameterSets.BLOCK);
        if(LootHelper.getEnchantment(context,Enchantments.SILK_TOUCH)==1) return Collections.singletonList(new ItemStack(this));
        return Collections.singletonList(i.get(context));
    }
}
