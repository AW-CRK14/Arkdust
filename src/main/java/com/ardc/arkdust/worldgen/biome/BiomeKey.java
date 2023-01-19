package com.ardc.arkdust.worldgen.biome;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.registry.BiomeRegistry;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;

public class BiomeKey {
    public static RegistryKey<Biome> CW$FAULT_LINE = createKey(BiomeRegistry.CW$FAULT_LINE);

    public static RegistryKey<Biome> createKey(RegistryObject<Biome> b){
        return RegistryKey.create(Registry.BIOME_REGISTRY,b.getId());
    }

    public static void generateBiome(){
        BiomeManager.addBiome(BiomeManager.BiomeType.DESERT,new BiomeManager.BiomeEntry(CW$FAULT_LINE, 18));
    }
}
