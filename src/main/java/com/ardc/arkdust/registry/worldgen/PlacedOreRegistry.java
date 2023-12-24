package com.ardc.arkdust.registry.worldgen;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.worldgen.modifier.SurfaceBiomeFilter;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.Arrays;

public class PlacedOreRegistry {
    public static final ResourceKey<PlacedFeature> FAULTLINE$ORE_PUA = registry("faultline/ore_pua");

    public static void bootstrap(BootstapContext<PlacedFeature> context){
        HolderGetter<ConfiguredFeature<?,?>> getter = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(FAULTLINE$ORE_PUA,confUniformCommon(getter,ConfiguredFeatureRegistry.FAULTLINE$ORE_PUA,2,5,4,32));
    }

    private static PlacedFeature confUniformCommon(HolderGetter<ConfiguredFeature<?,?>> getter, ResourceKey<ConfiguredFeature<?,?>> key, int minCount,int maxCount,int minHeight ,int maxHeight){
        return new PlacedFeature(getter.getOrThrow(key),Arrays.asList(CountPlacement.of(UniformInt.of(minCount,maxCount)),InSquarePlacement.spread(),HeightRangePlacement.triangle(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)), SurfaceBiomeFilter.INSTANCE));
    }

    private static ResourceKey<PlacedFeature> registry(String name){
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Utils.MOD_ID,name));
    }
}
