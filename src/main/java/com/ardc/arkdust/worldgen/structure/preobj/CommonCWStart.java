package com.ardc.arkdust.worldgen.structure.preobj;

import com.ardc.arkdust.RunHelper.StructureHelper;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;

public abstract class CommonCWStart extends StructureStart<NoFeatureConfig> {
    public CommonCWStart(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
        super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
    }

    @Override
    public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {
        int x = chunkX * 16;
        int z = chunkZ * 16;
        int landHeight = chunkGenerator.getBaseHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG);
        BlockPos centerPos = new BlockPos(x,Math.max(landHeight + yRemove(),1),z);
        addChildren(templateManagerIn,this.pieces,random,centerPos);
        this.calculateBoundingBox();
        StructureHelper.movePieceToCenter(centerPos,this.boundingBox,this.pieces);
        this.calculateBoundingBox();
    }

    public abstract void addChildren(TemplateManager templateManagerIn, List<StructurePiece> pieces, SharedSeedRandom random, BlockPos centerPos);

    public abstract int yRemove();
}
