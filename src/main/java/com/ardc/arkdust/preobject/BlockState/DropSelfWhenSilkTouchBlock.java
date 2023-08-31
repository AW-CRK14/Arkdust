package com.ardc.arkdust.preobject.BlockState;

import com.ardc.arkdust.advanced_obj.AASupplierItemStack;
import com.ardc.arkdust.helper.LootHelper;
import com.ardc.arkdust.preobject.pre.PreBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

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
