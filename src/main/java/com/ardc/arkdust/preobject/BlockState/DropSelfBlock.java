package com.ardc.arkdust.preobject.BlockState;

import com.ardc.arkdust.advanced_obj.AASupplierItemStack;
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

public class DropSelfBlock extends PreBlock {
    public final ICreateDrops i;

    public DropSelfBlock(Properties properties){
        super(properties);
        i = (s,c)->Collections.singletonList(new ItemStack(this));
    }

    public DropSelfBlock(Properties properties, int number, Supplier<Item> item){
        super(properties);
        i = (s,c)->Collections.singletonList(new ItemStack(item.get(),number));
    }

    public DropSelfBlock(Properties properties, int number){
        super(properties);
        i = (s,c)->Collections.singletonList(new ItemStack(this,number));
    }

    public DropSelfBlock(Properties properties,ICreateDrops iCreateDrops){
        super(properties);
        i = iCreateDrops;
    }

    public interface ICreateDrops{
        List<ItemStack> create(BlockState state,LootContext context);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {

        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return i.create(state,builder.withParameter(LootParameters.BLOCK_STATE, state).create(LootParameterSets.BLOCK));
    }
}
