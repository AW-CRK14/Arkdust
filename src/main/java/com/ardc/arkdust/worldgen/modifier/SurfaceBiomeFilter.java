package com.ardc.arkdust.worldgen.modifier;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.*;

public class SurfaceBiomeFilter extends PlacementFilter {
    public static final Codec<SurfaceBiomeFilter> CODEC = Codec.unit(SurfaceBiomeFilter::new);
    public static final SurfaceBiomeFilter INSTANCE = new SurfaceBiomeFilter();

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource randomSource, BlockPos pos) {
        PlacedFeature placedfeature = context.topFeature().orElseThrow(() -> new IllegalStateException("Tried to biome check an unregistered feature, or a feature that should not restrict the biome"));
        Holder<Biome> holder = context.getLevel().getBiome(context.getLevel().getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG,pos));
        return context.generator().getBiomeGenerationSettings(holder).hasFeature(placedfeature);
    }

    @Override
    public PlacementModifierType<?> type() {
        return ExtraPlacementModifierType.SURFACE_BIOME;
    }
}
