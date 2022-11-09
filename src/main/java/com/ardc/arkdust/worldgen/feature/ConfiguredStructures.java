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
    public static StructureFeature<?,?> cfed_cw_old_house_0 = StructureRegistry.CW_OLD_HOUSE_0.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?,?> cfed_pixark_library = StructureRegistry.PIXARK_LIBRARY.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?,?> cfed_cw_grave = StructureRegistry.CW_GRAVE.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?,?> cfed_cw_tower = StructureRegistry.CW_TOWER.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?,?> cfed_cw_old_house = StructureRegistry.CW_OLD_HOUSE.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?,?> cfed_cw_boat = StructureRegistry.CW_BOAT.get().configured(IFeatureConfig.NONE);


    public static void registryCfedStructure(){
        Registry<StructureFeature<?,?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;

        Registry.register(registry,new ResourceLocation(Utils.MOD_ID,"cfed_undertree_blueprint"),cfed_undertree_blueprint);
        Registry.register(registry,new ResourceLocation(Utils.MOD_ID,"cfed_cw_old_house_0"), cfed_cw_old_house_0);
        Registry.register(registry,new ResourceLocation(Utils.MOD_ID,"cfed_pixark_library"),cfed_pixark_library);
        Registry.register(registry,new ResourceLocation(Utils.MOD_ID,"cfed_cw_grave"),cfed_cw_grave);
        Registry.register(registry,new ResourceLocation(Utils.MOD_ID,"cfed_cw_tower"),cfed_cw_tower);
        Registry.register(registry,new ResourceLocation(Utils.MOD_ID,"cfed_cw_old_house"),cfed_cw_old_house);
        Registry.register(registry,new ResourceLocation(Utils.MOD_ID,"cfed_cw_boat"),cfed_cw_boat);

        FlatGenerationSettings.STRUCTURE_FEATURES.put(StructureRegistry.UNDERTREE_BLUEPRINT.get(),cfed_undertree_blueprint);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(StructureRegistry.CW_OLD_HOUSE_0.get(), cfed_cw_old_house_0);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(StructureRegistry.PIXARK_LIBRARY.get(),cfed_pixark_library);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(StructureRegistry.CW_GRAVE.get(),cfed_cw_grave);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(StructureRegistry.CW_TOWER.get(),cfed_cw_tower);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(StructureRegistry.CW_OLD_HOUSE.get(),cfed_cw_old_house);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(StructureRegistry.CW_BOAT.get(),cfed_cw_boat);
    }
}
