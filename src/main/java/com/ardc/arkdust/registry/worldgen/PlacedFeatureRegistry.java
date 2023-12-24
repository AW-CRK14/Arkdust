package com.ardc.arkdust.registry.worldgen;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.worldgen.config.ListNBTFeatureConfig;
import com.ardc.arkdust.worldgen.config.SingleBlockPlacementConfig;
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
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlacedFeatureRegistry {
    public static final ResourceKey<PlacedFeature> FAULTLINE$STONE_OAK_TREE = registry("faultline/stone_oak_tree");
    public static final ResourceKey<PlacedFeature> OVERWORLD_ORE_PILE = registry("overworld_ore_pile");
    public static final ResourceKey<PlacedFeature> FAULTLINE$MYCELIUM_PILE = registry("faultline/mycelium_pile");
    public static final ResourceKey<PlacedFeature> FAULTLINE$HOLE = registry("faultline/hole");
    public static final ResourceKey<PlacedFeature> FAULTLINE$RUIN = registry("faultline/ruin");
    public static final ResourceKey<PlacedFeature> FAULTLINE$ORE_PUA = registry("faultline/ore_pua");

    public static void bootstrap(BootstapContext<PlacedFeature> context){
        HolderGetter<ConfiguredFeature<?,?>> getter = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(FAULTLINE$STONE_OAK_TREE,conf(getter,ConfiguredFeatureRegistry.FAULTLINE$STONE_OAK_TREE, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.GRAVEL,Blocks.COBBLESTONE))));
        context.register(OVERWORLD_ORE_PILE,conf(getter,ConfiguredFeatureRegistry.OVERWORLD_ORE_PILE,RarityFilter.onAverageOnceEvery(72), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.STONE))));
        context.register(FAULTLINE$MYCELIUM_PILE,conf(getter,ConfiguredFeatureRegistry.FAULTLINE$MYCELIUM_PILE, CountPlacement.of(UniformInt.of(2,8)) ,InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.MYCELIUM))));
        context.register(FAULTLINE$HOLE,conf(getter,ConfiguredFeatureRegistry.FAULTLINE$HOLE,RarityFilter.onAverageOnceEvery(24),InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome(),RandomOffsetPlacement.vertical(ConstantInt.of(-5))));
        context.register(FAULTLINE$RUIN,conf(getter,ConfiguredFeatureRegistry.FAULTLINE$RUIN,RarityFilter.onAverageOnceEvery(96),InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome(),RandomOffsetPlacement.vertical(ConstantInt.of(-1))));


        PlacedOreRegistry.bootstrap(context);
//        context.register(FAULTLINE$ORE_PUA,conf(getter,ConfiguredFeatureRegistry.FAULTLINE$ORE_PUA, commonOrePlacement(30, HeightRangePlacement.uniform(VerticalAnchor.absolute(136), VerticalAnchor.top()))));
    }

    private static PlacedFeature conf(HolderGetter<ConfiguredFeature<?,?>> getter, ResourceKey<ConfiguredFeature<?,?>> key, PlacementModifier... list){
        return new PlacedFeature(getter.getOrThrow(key),Arrays.stream(list).toList());
    }

    private static ResourceKey<PlacedFeature> registry(String name){
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Utils.MOD_ID,name));
    }
}
