package com.ardc.arkdust.registry;

import com.ardc.arkdust.worldgen.biome.BiomeKey;
import com.ardc.arkdust.worldgen.biome.biome_maker.CWBiomeMaker;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

//@Deprecated
public class BiomeRegistry {
//    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Utils.MOD_ID);
//
//    public static final RegistryObject<Biome> CW$FAULT_LINE = BIOMES.register("cw/fault_line", CWBiomeMaker::faultLineBiomeMaker);

    public static void bootstrap(BootstapContext<Biome> context){
        HolderGetter<ConfiguredWorldCarver<?>> c = context.lookup(Registries.CONFIGURED_CARVER);
        HolderGetter<PlacedFeature> p = context.lookup(Registries.PLACED_FEATURE);

        context.register(BiomeKey.CW$FAULT_LINE,CWBiomeMaker.faultLineBiomeMaker(p,c));
    }
}
