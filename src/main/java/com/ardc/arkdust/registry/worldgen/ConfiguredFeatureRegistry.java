package com.ardc.arkdust.registry.worldgen;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.worldgen.config.ListNBTFeatureConfig;
import com.ardc.arkdust.worldgen.config.SingleBlockPlacementConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Collections;

public class ConfiguredFeatureRegistry {
    public static final ResourceKey<ConfiguredFeature<?, ?>> FAULTLINE$STONE_OAK_TREE = registry("faultline/stone_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_ORE_PILE = registry("overworld_ore_pile");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FAULTLINE$MYCELIUM_PILE = registry("faultline/mycelium_pile");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FAULTLINE$HOLE = registry("faultline/hole");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FAULTLINE$RUIN = registry("faultline/ruin");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FAULTLINE$ORE_PUA = registry("faultline/ore_pua");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context){
        context.register(FAULTLINE$STONE_OAK_TREE,conf(FeatureRegistry.FAULTLINE$STONE_OAK_TREE));
        context.register(OVERWORLD_ORE_PILE,conf(FeatureRegistry.OVERWORLD_ORE_PILE));
        context.register(FAULTLINE$MYCELIUM_PILE,conf(FeatureRegistry.ABS$SINGLE_BLOCK_ON,new SingleBlockPlacementConfig(Collections.singletonList(BlockRegistry.mycelium_pile.get().defaultBlockState()))));
        context.register(FAULTLINE$HOLE,conf(FeatureRegistry.ABS$LIST_NBT_FEATURE,new ListNBTFeatureConfig(Utils.MOD_ID+":biomefeature/faultline/hole_",3,true,1)));
        context.register(FAULTLINE$RUIN,conf(FeatureRegistry.ABS$LIST_NBT_FEATURE,new ListNBTFeatureConfig(Utils.MOD_ID+":biomefeature/faultline/ruin_",6,true,0.85F)));
        context.register(FAULTLINE$ORE_PUA,conf(Feature.ORE,new OreConfiguration(new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD),BlockRegistry.pau_ore.get().defaultBlockState(),7,0.4F)));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> ConfiguredFeature<FC,F> conf(F reg,FC config){
        return new ConfiguredFeature<>(reg,config);
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> ConfiguredFeature<FC,F> conf(RegistryObject<F> reg,FC config){
        return conf(reg.get(),config);
    }

    private static <F extends Feature<NoneFeatureConfiguration>> ConfiguredFeature<NoneFeatureConfiguration,F> conf(RegistryObject<F> reg){
        return new ConfiguredFeature<>(reg.get(),new NoneFeatureConfiguration());
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> registry(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Utils.MOD_ID,name));
    }
}
