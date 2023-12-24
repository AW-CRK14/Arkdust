package com.ardc.arkdust.registry.worldgen;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.resource.Tag;
import com.ardc.arkdust.worldgen.config.ListNBTFeatureConfig;
import com.ardc.arkdust.worldgen.config.SingleBlockPlacementConfig;
import com.ardc.arkdust.worldgen.feature.faultline.FaultlineStoneOakTreeFeature;
import com.ardc.arkdust.worldgen.feature.faultline.OrePile;
import com.ardc.arkdust.worldgen.feature.pre.ListNBTGenerateFeature;
import com.ardc.arkdust.worldgen.feature.pre.SingleBlockOnTarget;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class FeatureRegistry {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Utils.MOD_ID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> FAULTLINE$STONE_OAK_TREE = FEATURES.register("faultline/stone_oak_tree", ()->new FaultlineStoneOakTreeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> OVERWORLD_ORE_PILE = FEATURES.register("overworld_ore_pile", ()->new OrePile(NoneFeatureConfiguration.CODEC, Tag.Blocks.OVERWORLD_ORES));
    public static final RegistryObject<Feature<SingleBlockPlacementConfig>> ABS$SINGLE_BLOCK_ON = FEATURES.register("abs/single_block_on", ()->new SingleBlockOnTarget(SingleBlockPlacementConfig.CODEC));
    public static final RegistryObject<Feature<ListNBTFeatureConfig>> ABS$LIST_NBT_FEATURE = FEATURES.register("abs/list_nbt_feature", ()->new ListNBTGenerateFeature(ListNBTFeatureConfig.CODEC));

}
