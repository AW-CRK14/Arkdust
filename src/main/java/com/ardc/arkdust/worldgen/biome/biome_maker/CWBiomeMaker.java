package com.ardc.arkdust.worldgen.biome.biome_maker;

import com.ardc.arkdust.registry.ParticleRegistry;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class CWBiomeMaker{
    public static Biome faultLineBiomeMaker(HolderGetter<PlacedFeature> f, HolderGetter<ConfiguredWorldCarver<?>> c){
        MobSpawnSettings.Builder mobInfoBuilder = new MobSpawnSettings.Builder();
        mobInfoBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 15, 2, 3));
        mobInfoBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.HUSK, 200, 1, 7));
        BiomeDefaultFeatures.monsters(mobInfoBuilder, 25, 120, 10,false);

//        BiomeGenerationSettings.Builder generationSettingBuilder = (new BiomeGenerationSettings.Builder(f,c)).surfaceBuilder(SurfaceBuilderRegistry.CW$FAULT_LINE::get);
        BiomeGenerationSettings.Builder generationSettingBuilder = (new BiomeGenerationSettings.Builder(f,c));

        BiomeDefaultFeatures.addDefaultCrystalFormations(generationSettingBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generationSettingBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generationSettingBuilder);
        BiomeDefaultFeatures.addDefaultSprings(generationSettingBuilder);
//        generationSettingBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, BuiltinStructures.MINESHAFT);
//        generationSettingBuilder.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
        OverworldBiomes.globalOverworldGeneration(generationSettingBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generationSettingBuilder);
        BiomeDefaultFeatures.addDefaultOres(generationSettingBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(generationSettingBuilder);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettingBuilder);
        BiomeDefaultFeatures.addBadlandExtraVegetation(generationSettingBuilder);
        generationSettingBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_LAVA);
        BiomeDefaultFeatures.addBadlandsTrees(generationSettingBuilder);
//        BiomeDefaultFeatures.addDefaultCarvers(generationSettingBuilder);

        return (new Biome.BiomeBuilder()).hasPrecipitation(false).temperature(1.7F).downfall(0.1F)
                .specialEffects((new BiomeSpecialEffects.Builder()).waterColor(11974616).waterFogColor(9211297).fogColor(13678511).skyColor(13681838).foliageColorOverride(10268042).grassColorOverride(12896433)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).ambientParticle(new AmbientParticleSettings((ParticleOptions) ParticleRegistry.FLYING_GRAVEL.get(),0.03F)).build())
//                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).ambientParticle(new ParticleEffectAmbience((IParticleData) ParticleRegistry.FLYING_GRAVEL.get(),0.07F)).build())
                .mobSpawnSettings(mobInfoBuilder.build()).generationSettings(generationSettingBuilder.build()).build();
    }
}
