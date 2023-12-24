package com.ardc.arkdust.helper;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;

public class RegNameHelper {
    public static ResourceLocation getName(Item item){
        return BuiltInRegistries.ITEM.getKey(item);
    }

//    public static ResourceLocation getName(Biome item){
//        return BuiltInRegistries.ITEM.getKey(item);
//    }
}
