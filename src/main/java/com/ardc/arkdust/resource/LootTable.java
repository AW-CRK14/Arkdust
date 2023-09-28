package com.ardc.arkdust.resource;

import com.ardc.arkdust.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import java.util.Random;

public class LootTable {
    public static final ResourceLocation CW_BLUEPRINT_BOX = new ResourceLocation(Utils.MOD_ID,"chests/wm_blueprint_box");
    public static final ResourceLocation FISH = new ResourceLocation(Utils.MOD_ID,"chests/fish_chest");
    public static final ResourceLocation GENERAL_SUPPLY_BOX_A = new ResourceLocation(Utils.MOD_ID,"chests/general_supply_box_a");
    public static final ResourceLocation STORY_MOONFALL_CPLT_TREASURE = new ResourceLocation(Utils.MOD_ID,"chests/oasis_treasure");
    public static final ResourceLocation MINER_CHEST_A = new ResourceLocation(Utils.MOD_ID,"chests/miner_chest_a");
    public static final ResourceLocation[] SUNDRIES = new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"chests/wm_blueprint_box"), BuiltInLootTables.DESERT_PYRAMID, BuiltInLootTables.VILLAGE_FISHER, BuiltInLootTables.VILLAGE_SAVANNA_HOUSE, BuiltInLootTables.ABANDONED_MINESHAFT, BuiltInLootTables.PILLAGER_OUTPOST, BuiltInLootTables.BASTION_HOGLIN_STABLE, BuiltInLootTables.BASTION_BRIDGE, BuiltInLootTables.BASTION_TREASURE};



    public static ResourceLocation getRandomLootTable(ResourceLocation[] list){
        return getRandomLootTable(list,new Random());
    }

    public static ResourceLocation getRandomLootTable(ResourceLocation[] list, RandomSource random){
        return list[random.nextInt(list.length)];
    }

    public static ResourceLocation getRandomLootTable(ResourceLocation[] list, Random random){
        return list[random.nextInt(list.length)];
    }


}
