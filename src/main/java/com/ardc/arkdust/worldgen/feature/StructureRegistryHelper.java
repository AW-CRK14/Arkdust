package com.ardc.arkdust.worldgen.feature;

import com.ardc.arkdust.worldgen.feature.structure_pool.UndertreeBlueprintPool;
import com.ardc.arkdust.registry.StructureRegistry;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;

public class StructureRegistryHelper {

    public static void JigsawRegistryList(){
        JigsawPatternRegistry.register(UndertreeBlueprintPool.pool);

    }

    public static void DimensionSettingRegistry(){
        for (RegistryObject<Structure<?>> registryObject : StructureRegistry.STRUCTURES.getEntries()){
            Structure<?> structure = registryObject.get();
            StructureSeparationSettings settings;
            if(registryObject.get() instanceof ArdStructureAddInfo){
                settings = ((ArdStructureAddInfo) registryObject.get()).getSSSetting();
            }else{
                settings = new StructureSeparationSettings(514,114,1919810);
            }
            DimensionStructuresSettings.DEFAULTS = ImmutableMap .<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.DEFAULTS).put(structure,settings).build();
            DimensionSettings.BUILTIN_OVERWORLD.structureSettings().structureConfig.put(structure,settings);
//            WorldGenRegistries.NOISE_GENERATOR_SETTINGS = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.DEFAULTS).put(structure, settings).build();
        }
    }
}
