package com.ardc.arkdust.worldgen.biome.biome_maker;

import com.ardc.arkdust.particle.environment.FlyingGravelParticle;
import com.ardc.arkdust.registry.FeatureRegistry;
import com.ardc.arkdust.registry.ParticleRegistry;
import com.ardc.arkdust.registry.SurfaceBuilderRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureFeatures;

public class CWBiomeMaker{
    public static Biome faultLineBiomeMaker(){
        MobSpawnInfo.Builder mobInfoBuilder = new MobSpawnInfo.Builder();
        mobInfoBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 15, 2, 3));
        mobInfoBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.HUSK, 200, 1, 7));
        DefaultBiomeFeatures.monsters(mobInfoBuilder, 25, 120, 10);

        BiomeGenerationSettings.Builder generationSettingBuilder = (new BiomeGenerationSettings.Builder()).surfaceBuilder(SurfaceBuilderRegistry.CW$FAULT_LINE::get);
        generationSettingBuilder.addStructureStart(StructureFeatures.MINESHAFT);
        generationSettingBuilder.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
        DefaultBiomeFeatures.addDefaultCarvers(generationSettingBuilder);
        DefaultBiomeFeatures.addDefaultMonsterRoom(generationSettingBuilder);
        DefaultBiomeFeatures.addDefaultUndergroundVariety(generationSettingBuilder);
        DefaultBiomeFeatures.addDefaultOres(generationSettingBuilder);
        DefaultBiomeFeatures.addDefaultSoftDisks(generationSettingBuilder);
        DefaultBiomeFeatures.addDefaultMushrooms(generationSettingBuilder);
        DefaultBiomeFeatures.addBadlandExtraVegetation(generationSettingBuilder);
        generationSettingBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA);
        DefaultBiomeFeatures.addBadlandsTrees(generationSettingBuilder);
        DefaultBiomeFeatures.addDefaultCarvers(generationSettingBuilder);

        return (new Biome.Builder()).precipitation(Biome.RainType.NONE).biomeCategory(Biome.Category.MESA).depth(0.3F).scale(0.02F).temperature(1.7F).downfall(0.0F)
                .specialEffects((new BiomeAmbience.Builder()).waterColor(11974616).waterFogColor(9211297).fogColor(13678511).skyColor(13681838).foliageColorOverride(10268042).grassColorOverride(12896433)
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).ambientParticle(new ParticleEffectAmbience(new FlyingGravelParticle.Type(),0.03F)).build())
//                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).ambientParticle(new ParticleEffectAmbience((IParticleData) ParticleRegistry.FLYING_GRAVEL.get(),0.07F)).build())
                .mobSpawnSettings(mobInfoBuilder.build()).generationSettings(generationSettingBuilder.build()).build();
    }
}
