package com.ardc.arkdust.Enums.BlockEntityAbout;

import com.ardc.arkdust.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.Random;

public enum ScreenedTable {
    GRAVEL          (1,Items.GRAVEL,
            Items.FLINT,Items.IRON_NUGGET,Items.GOLD_NUGGET,Items.CLAY_BALL,Items.NAUTILUS_SHELL),
    SAND            (1,Items.SAND,
            Items.IRON_NUGGET,Items.GOLD_NUGGET,Items.BONE_MEAL,Items.MELON_SEEDS,Items.BONE,Items.BAMBOO,Items.STICK),
    RED_SAND        (1,Items.RED_SAND,
            Items.IRON_NUGGET,Items.GOLD_NUGGET,Items.BONE_MEAL,Items.MELON_SEEDS,Items.BONE,Items.BAMBOO,Items.STICK,Items.REDSTONE),
    BLACKSTONE_SAND (1,ItemRegistry.blackstone_sand.get(),
            Items.IRON_NUGGET,Items.GOLD_NUGGET,Items.BONE_MEAL,Items.BONE,Items.BAMBOO,Items.STICK,ItemRegistry.muddy_silicon.get(),ItemRegistry.pau_nugget.get(),Items.ENDER_PEARL,ItemRegistry.netherite_nugget.get()),
    PRISMARINE      (2,Items.PRISMARINE,
            Items.FLINT,Items.PRISMARINE_SHARD,Items.PRISMARINE_CRYSTALS,Items.NAUTILUS_SHELL,Items.INK_SAC,Items.SPONGE,Items.WET_SPONGE,Items.TRIDENT,Items.SEA_LANTERN,
            Items.PRISMARINE_SHARD,Items.PRISMARINE_CRYSTALS,Items.PRISMARINE_SHARD,Items.PRISMARINE_CRYSTALS,Items.PRISMARINE_SHARD,Items.PRISMARINE_CRYSTALS,
            Items.PRISMARINE_SHARD,Items.PRISMARINE_CRYSTALS,Items.PRISMARINE_SHARD,Items.PRISMARINE_CRYSTALS,Items.PRISMARINE_SHARD,Items.PRISMARINE_CRYSTALS),
    SOUL_SAND       (2,Items.SOUL_SAND,
            Items.NETHER_WART,Items.GOLD_NUGGET,Items.BLAZE_POWDER,Items.QUARTZ,Items.GHAST_TEAR,Items.REDSTONE,ItemRegistry.netherite_nugget.get());

    private final Item keyItem;
    private final int hardLevel;
    private final Item[] items;
    ScreenedTable(int hardLevel,Item keyItem,Item... itemList){
        this.items = itemList;
        this.hardLevel = hardLevel;
        this.keyItem = keyItem;
    }

    public Item throwNewItem(){
        Random r = new Random();
        return items[r.nextInt(items.length)];
    }

    public Item getKeyItem(){
        return keyItem;
    }

    public int getHardLevel(){
        return hardLevel;
    }
}
