package com.ardc.arkdust.blockstate;

import com.ardc.arkdust.helper.LootHelper;
import com.ardc.arkdust.preobject.PreBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.Collections;
import java.util.List;

public class DropSelfWhenSilkTouchBlock extends PreBlock {
    public final IItemStackProviderWithLootContest i;
    public DropSelfWhenSilkTouchBlock(Properties properties, IItemStackProviderWithLootContest itemStackSupplier){
        super(properties);
        i = itemStackSupplier;
    }

    public interface IItemStackProviderWithLootContest{
        ItemStack get(LootParams context);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {

        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        LootParams context = builder.withParameter(LootContextParams.BLOCK_STATE, state).create(LootContextParamSets.BLOCK);
        if(LootHelper.getEnchantment(context, Enchantments.SILK_TOUCH)==1) return Collections.singletonList(new ItemStack(this));
        return Collections.singletonList(i.get(context));
    }
}
