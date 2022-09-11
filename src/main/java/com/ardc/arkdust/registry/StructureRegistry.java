package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.worldgen.feature.structure.cworld.UnderTreeBlueprintBox;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class StructureRegistry {
    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Utils.MOD_ID);

    public static final RegistryObject<Structure<NoFeatureConfig>> UNDERTREE_BLUEPRINT = setup("undertree_blueprint",new UnderTreeBlueprintBox(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);

    public static <F extends Structure<?>> RegistryObject<F> setup(String name, F structure,GenerationStage.Decoration decoration) {
        Structure.STRUCTURES_REGISTRY.put(Utils.MOD_ID + ":" + name, structure);
        Structure.STEP.put(structure, decoration);
        return STRUCTURES.register(name,()->structure);
    }
    //                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.DEFAULTS).put(structure, sSSettings).build();
//        WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
//            Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig();
//
    /*
     * There are very few mods that relies on seeing your structure in the noise settings registry before the world is made.
     *
     * You may see some mods add their spacings to DimensionSettings.BUILTIN_OVERWORLD instead of the NOISE_GENERATOR_SETTINGS loop below but
     * that field only applies for the default overworld and won't add to other worldtypes or dimensions (like amplified or Nether).
     * So yeah, don't do DimensionSettings.BUILTIN_OVERWORLD. Use the NOISE_GENERATOR_SETTINGS loop below instead if you must.
     */
}
