package com.ardc.arkdust.worldgen.biome;

import com.ardc.arkdust.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeManager;

public class BiomeKey {

    public static final ResourceKey<Biome> CW$FAULT_LINE = register("cw/fault_line");

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(Utils.MOD_ID,name));
    }

    public static void generateBiome(){
        BiomeManager.addBiome(BiomeManager.BiomeType.DESERT,new BiomeManager.BiomeEntry(CW$FAULT_LINE, 18));
    }
}
