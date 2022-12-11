package com.ardc.arkdust.CodeMigration.RunHelper;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.worldgen.feature.ArdStructureAddInfo;
import com.ardc.arkdust.worldgen.feature.structure.cworld.UnderTreeBlueprintBox;
import com.ardc.arkdust.worldgen.feature.structure_pool.UndertreeBlueprintPool;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class StructureRegistryHelper {

    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Utils.MOD_ID);

    public static void DimensionSettingRegistry(){
        for (RegistryObject<Structure<?>> registryObject : STRUCTURES.getEntries()){
            Structure<?> structure = registryObject.get();
            StructureSeparationSettings settings;
            if(structure instanceof ArdStructureAddInfo){
                settings = ((ArdStructureAddInfo) structure).getSSSetting();
            }else{
                settings = new StructureSeparationSettings(514,114,1919810);
            }

            DimensionStructuresSettings.DEFAULTS = ImmutableMap .<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.DEFAULTS).put(structure,settings).build();
            if(structure instanceof ArdStructureAddInfo) {
                switch (((ArdStructureAddInfo) structure).mode()){
                    case NOISY_REF:
                        Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder().addAll(Structure.NOISE_AFFECTING_FEATURES).add(structure).build();
                        break;
                    case OVERWORLD:
                        DimensionSettings.BUILTIN_OVERWORLD.structureSettings().structureConfig.put(structure,settings);
                        break;
                }
            }else{
                DimensionSettings.BUILTIN_OVERWORLD.structureSettings().structureConfig.put(structure,settings);
            }

            WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(setting -> {
                Map<Structure<?>, StructureSeparationSettings> structureMap = setting.getValue().structureSettings().structureConfig();
                /*
                 * Pre-caution in case a mod makes the structure map immutable like datapacks do.
                 * I take no chances myself. You never know what another mods does...
                 *
                 * structureConfig requires AccessTransformer  (See resources/META-INF/accesstransformer.cfg)
                 */
                if(structureMap instanceof ImmutableMap){
                    Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                    tempMap.put(structure, settings);
                    setting.getValue().structureSettings().structureConfig = tempMap;

                }
                else{
                    structureMap.put(structure, settings);
                }
            });
        }
    }

    public static <F extends Structure<?>> RegistryObject<F> setup(String name, F structure,GenerationStage.Decoration decoration) {
        Structure.STRUCTURES_REGISTRY.put(Utils.MOD_ID + ":" + name, structure);
        Structure.STEP.put(structure, decoration);
        return STRUCTURES.register(name,()->structure);
    }
}
