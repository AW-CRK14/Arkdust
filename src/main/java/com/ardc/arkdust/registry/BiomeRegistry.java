package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.worldgen.biome.biome_maker.CWBiomeMaker;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeRegistry {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Utils.MOD_ID);

    public static final RegistryObject<Biome> CW$FAULT_LINE = BIOMES.register("cw/fault_line", CWBiomeMaker::faultLineBiomeMaker);
}
