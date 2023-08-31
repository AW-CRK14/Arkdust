package com.ardc.arkdust.helper;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

public class LootHelper {
    public static ItemStack getUseItemStack(LootContext context){
        return context.getParamOrNull(LootParameters.TOOL).equals(null) ? new ItemStack(Items.AIR) : context.getParamOrNull(LootParameters.TOOL);
    }

    public static int getEnchantment(LootContext context, Enchantment enchantment){
        ItemStack stack = getUseItemStack(context);
        if(stack.getTag() != null && stack.getTag().get("Enchantments")!=null && stack.getTag().get("Enchantments") instanceof ListNBT){
            for (INBT nbt :((ListNBT) stack.getTag().get("Enchantments"))) {
                if(nbt instanceof CompoundNBT && ((CompoundNBT) nbt).getString("id").equals(enchantment.getRegistryName().toString()))
                    return ((CompoundNBT) nbt).getInt("lvl");
            }
        }
        return 0;
    }
}
