package com.ardc.arkdust.worldgen.modifier;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.worldgen.modifier.SurfaceBiomeFilter;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class ExtraPlacementModifierType {
    public static void bootstrap(){}

    public static final PlacementModifierType<SurfaceBiomeFilter> SURFACE_BIOME = register("surface_biome", SurfaceBiomeFilter.CODEC);

    private static <P extends PlacementModifier> PlacementModifierType<P> register(String name, Codec<P> codec) {
        return Registry.register(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, new ResourceLocation(Utils.MOD_ID,name), () -> codec);
    }
}
