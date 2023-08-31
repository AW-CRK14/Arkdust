package com.ardc.arkdust.worldgen.structure.structure.cworld;

import com.ardc.arkdust.helper.PosHelper;
import com.ardc.arkdust.helper.StructureHelper;
import com.ardc.arkdust.Utils;
import com.ardc.arkdust.worldgen.structure.ArdStructureAddInfo;
import com.mojang.serialization.Codec;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class CWTestBridge extends Structure<NoFeatureConfig> implements ArdStructureAddInfo {

    public CWTestBridge(Codec<NoFeatureConfig> p_i231997_1_) {
        super(p_i231997_1_);
    }

    @Override
    public GenerationStage.Decoration step() {//与生成的位置有关，大概。
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource,
                                     long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ,
                                     Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
        BlockPos centerOfChunk = new BlockPos(chunkX << 4, 0, chunkZ << 4);

        return StructureHelper.isEachPlaceAvailable(chunkGenerator, Heightmap.Type.WORLD_SURFACE_WG,4,
                PosHelper.getCenterAndSquareVertexPos(centerOfChunk,3,false,true));//获取此位置是否为流体（防止生成在水上）

    }

    @Override
    public int spacing() {
        return 80;
    }

    @Override
    public int separation() {
        return 20;
    }

    @Override
    public int salt() {
        return 382470;
    }

    @Override
    public buildMode mode() {
        return buildMode.OVERWORLD;
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return Start::new;
    }

    public static class Start extends StructureStart<NoFeatureConfig> {

        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override
        public void generatePieces(DynamicRegistries registries, ChunkGenerator generator, TemplateManager manager, int x, int z, Biome biome, NoFeatureConfig featureConfig) {
            BlockPos pos = new BlockPos(x*16+4,0,z*16+4);

            JigsawManager.addPieces(registries,
                    new VillageConfig(()->registries.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(new ResourceLocation(Utils.MOD_ID,"cw/test_bridge/main")),8),
                    AbstractVillagePiece::new,generator,manager,pos,this.pieces,this.random,false,true);

            this.calculateBoundingBox();
        }
    }
}
