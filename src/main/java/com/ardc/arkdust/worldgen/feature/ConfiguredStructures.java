package com.ardc.arkdust.worldgen.feature;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.registry.StructureRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ConfiguredStructures {
    public static StructureFeature<?,?> cfed_undertree_blueprint = StructureRegistry.UNDERTREE_BLUEPRINT.get().configured(IFeatureConfig.NONE);

    public static void registryCfedStructure(){
        Registry<StructureFeature<?,?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;

        Registry.register(registry,new ResourceLocation(Utils.MOD_ID,"cfed_undertree_blueprint"),cfed_undertree_blueprint);

        FlatGenerationSettings.STRUCTURE_FEATURES.put(StructureRegistry.UNDERTREE_BLUEPRINT.get(),cfed_undertree_blueprint);
    }
}
