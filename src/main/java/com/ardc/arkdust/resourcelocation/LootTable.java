package com.ardc.arkdust.resourcelocation;

import com.ardc.arkdust.Utils;
import net.minecraft.loot.LootTables;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class LootTable {
    public static final ResourceLocation CW_BLUEPRINT_BOX = new ResourceLocation(Utils.MOD_ID,"chests/wm_blueprint_box");
    public static final ResourceLocation FISH = new ResourceLocation(Utils.MOD_ID,"chests/fish_chest");
    public static final ResourceLocation GENERAL_SUPPLY_BOX_A = new ResourceLocation(Utils.MOD_ID,"chests/general_supply_box_a");
    public static final ResourceLocation STORY_MOONFALL_CPLT_TREASURE = new ResourceLocation(Utils.MOD_ID,"chests/oasis_treasure");
    public static final ResourceLocation MINER_CHEST_A = new ResourceLocation(Utils.MOD_ID,"chests/miner_chest_a");
    public static final ResourceLocation[] SUNDRIES = new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"chests/wm_blueprint_box"), LootTables.DESERT_PYRAMID, LootTables.VILLAGE_FISHER, LootTables.VILLAGE_SAVANNA_HOUSE, LootTables.ABANDONED_MINESHAFT, LootTables.PILLAGER_OUTPOST, LootTables.BASTION_HOGLIN_STABLE, LootTables.BASTION_BRIDGE, LootTables.BASTION_TREASURE};



    public static ResourceLocation getRandomLootTable(ResourceLocation[] list){
        return getRandomLootTable(list,new Random());
    }

    public static ResourceLocation getRandomLootTable(ResourceLocation[] list, Random random){
        return list[random.nextInt(list.length)];
    }


}
