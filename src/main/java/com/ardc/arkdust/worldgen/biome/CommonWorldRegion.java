package com.ardc.arkdust.worldgen.biome;

import com.ardc.arkdust.Utils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class CommonWorldRegion extends Region {
    public CommonWorldRegion(int weight) {
        super(new ResourceLocation(Utils.MOD_ID,"cw_region_provider"), RegionType.OVERWORLD,weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        super.addBiomes(registry, mapper);
        this.addBiome(mapper, ParameterUtils.Temperature.HOT, ParameterUtils.Humidity.ARID, ParameterUtils.Continentalness.INLAND, ParameterUtils.Erosion.EROSION_1, ParameterUtils.Weirdness.FULL_RANGE, ParameterUtils.Depth.SURFACE,0,BiomeKey.CW$FAULT_LINE);
    }

//    public static SurfaceRules.RuleSource build(){
//        return SurfaceRules.sequence(
//                SurfaceRules.ifTrue(surfacerules$conditionsource14, SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, SANDSTONE))
//        );
//    }
}
