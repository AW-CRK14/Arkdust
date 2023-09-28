package com.ardc.arkdust.helper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.Collections;
import java.util.List;

public class LootHelper {
    public static ItemStack getUseItemStack(LootParams context){
//        return context.getParamOrNull(LootContextParams.TOOL).equals(null) ? new ItemStack(Items.AIR) : context.getParamOrNull(LootContextParams.TOOL);
        return context.getParamOrNull(LootContextParams.TOOL);
    }

    public static int getEnchantment(LootParams context, Enchantment enchantment){//TODO ¼ì²éÐ§¹û
        ItemStack stack = getUseItemStack(context);
        if(stack.getTag() != null && stack.getTag().get("Enchantments")!=null && stack.getTag().get("Enchantments") instanceof ListTag){
            for (Tag nbt :((ListTag) stack.getTag().get("Enchantments"))) {
                if(nbt instanceof CompoundTag && ((CompoundTag) nbt).getString("id").equals(enchantment.getDescriptionId()))
                    return ((CompoundTag) nbt).getInt("lvl");
            }
        }
        return 0;
    }

    public static List<ItemStack> dropSelfWhenNoLoot(BlockState state, LootParams.Builder builder){
        ResourceLocation resourcelocation = state.getBlock().getLootTable();
        if (resourcelocation == BuiltInLootTables.EMPTY) {
            return Collections.singletonList(new ItemStack(state.getBlock()));
        } else {
            LootParams lootparams = builder.withParameter(LootContextParams.BLOCK_STATE, state).create(LootContextParamSets.BLOCK);
            ServerLevel serverlevel = lootparams.getLevel();
            LootTable loottable = serverlevel.getServer().getLootData().getLootTable(resourcelocation);
            return loottable.getRandomItems(lootparams);
        }
    }
}
