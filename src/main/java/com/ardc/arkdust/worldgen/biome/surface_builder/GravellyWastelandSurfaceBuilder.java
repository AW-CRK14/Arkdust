package com.ardc.arkdust.worldgen.biome.surface_builder;

import com.ardc.arkdust.registry.SurfaceBuilderRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

public class GravellyWastelandSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    public GravellyWastelandSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    public void apply(Random random, IChunk iChunk, Biome biome, int chunkX, int chunkZ, int y, double noiseValue, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        SurfaceBuilderConfig privateConfig;
        if (noiseValue > 2.2D) {
            privateConfig = SurfaceBuilderRegistry.FUNGUS;
        }else if(noiseValue > 1.7D){
//            privateConfig = new Random((long)noiseValue +chunkX<<8+ chunkZ<<4+y+ random.nextLong()).nextFloat() * 0.5F > noiseValue - 1.7D ? SurfaceBuilder.CONFIG_GRAVEL : SurfaceBuilderRegistry.FUNGUS;
            privateConfig = random.nextFloat() * 0.5F > noiseValue - 1.7D ? SurfaceBuilder.CONFIG_GRAVEL : SurfaceBuilderRegistry.FUNGUS;
        } else if (noiseValue > 0.8D) {
            privateConfig = SurfaceBuilder.CONFIG_GRAVEL;
        }else if (noiseValue > 0.1D){
            privateConfig = random.nextFloat() * 0.5F > noiseValue - 0.1D ? SurfaceBuilderRegistry.COBBLESTONE_COVER : SurfaceBuilder.CONFIG_GRAVEL;
        } else if (noiseValue > -1.6D) {
            privateConfig = SurfaceBuilderRegistry.COBBLESTONE_COVER;
        } else {
            privateConfig = SurfaceBuilder.CONFIG_STONE;
        }
        SurfaceBuilder.DEFAULT.apply(random, iChunk, biome, chunkX, chunkZ, y, noiseValue, defaultBlock, defaultFluid, seaLevel, seed, privateConfig);

    }
}
