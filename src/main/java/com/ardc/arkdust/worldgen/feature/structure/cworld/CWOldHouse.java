package com.ardc.arkdust.worldgen.feature.structure.cworld;

import com.ardc.arkdust.CodeMigration.RunHelper.PosHelper;
import com.ardc.arkdust.CodeMigration.RunHelper.StructureHelper;
import com.ardc.arkdust.CodeMigration.resourcelocation.LootTable;
import com.ardc.arkdust.CodeMigration.resourcelocation.Tag;
import com.ardc.arkdust.Utils;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.worldgen.feature.ArdStructureAddInfo;
import com.ardc.arkdust.worldgen.feature.StructurePieceTypeAdd;
import com.ardc.arkdust.worldgen.feature.StructureProcessorListAdd;
import com.ardc.arkdust.worldgen.feature.preobj.CommonCWStart;
import com.ardc.arkdust.worldgen.feature.preobj.CommonCWTemplatePiece;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraftforge.common.util.Lazy;

import java.util.List;
import java.util.Random;

public class CWOldHouse extends Structure<NoFeatureConfig> implements ArdStructureAddInfo {

    public static final StructureHelper.StructureAndVariation H1 = new StructureHelper.StructureAndVariation(new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_2_0"),0.2F,new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_2_1"),new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_2_2"),new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_2_3"),new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_2_4"),new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_2_5")});
    public static final StructureHelper.StructureAndVariation H2 = new StructureHelper.StructureAndVariation(new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_3_0"),0.4F,new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_3_1"),new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_3_2")});

    public CWOldHouse(Codec<NoFeatureConfig> p_i231997_1_) {
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

        return StructureHelper.isEachPlaceAvailable(chunkGenerator, Heightmap.Type.WORLD_SURFACE_WG,3,
                PosHelper.getCenterAndSquareVertexPos(centerOfChunk,8,false,true)
        );//获取此位置是否为流体（防止生成在水上）

    }

    @Override
    public int spacing() {
        return 60;
    }

    @Override
    public int separation() {
        return 20;
    }

    @Override
    public int salt() {
        return 247384829;
    }

    @Override
    public buildMode mode() {
        return buildMode.OVERWORLD;
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return Start::new;
    }

    public static class Start extends CommonCWStart {

        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override
        public void addChildren(TemplateManager templateManagerIn, List<StructurePiece> pieces, SharedSeedRandom random, BlockPos centerPos) {
//            Piece piece = new Piece(templateManagerIn,new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_4_0"),centerPos, Rotation.getRandom(random),random);
            Piece piece = new Piece(templateManagerIn,randomLocation(random),centerPos, Rotation.getRandom(random),random);
            pieces.add(piece);
        }

        @Override
        public int yRemove() {
            return -3;
        }
    }

    public static ResourceLocation randomLocation(Random random){
        Random r = new Random(random.nextLong());
        return r.nextFloat() >=0.35F ? H1.getRandomPart(random) : H2.getRandomPart(random);
    }

    public static class Piece extends CommonCWTemplatePiece {
//        private final LazyValue<BlockIgnoreStructureProcessor> STRUCTURE_BLOCK_AND_ARD_IGNBLOCK = new LazyValue<>(()->new BlockIgnoreStructureProcessor(ImmutableList.of(Blocks.STRUCTURE_BLOCK, BlockRegistry.structure_ignore_block.get()))) ;


        public Piece(TemplateManager templateManager, ResourceLocation structurePlace, BlockPos addPos, Rotation aRotation, Random random) {
            super(StructurePieceTypeAdd.CW_OLD_HOUSE, templateManager, structurePlace, addPos, aRotation, random);
        }

        public Piece(TemplateManager templateManager, CompoundNBT nbt) {
            super(StructurePieceTypeAdd.CW_OLD_HOUSE, templateManager, nbt);
        }

        public boolean postProcess(ISeedReader p_230383_1_, StructureManager p_230383_2_, ChunkGenerator p_230383_3_, Random p_230383_4_, MutableBoundingBox p_230383_5_, ChunkPos p_230383_6_, BlockPos p_230383_7_) {
            this.placeSettings.addProcessor(StructureProcessorListAdd.STRUCTURE_BLOCK_AND_ARD_IGNBLOCK.get());
            return super.postProcess(p_230383_1_, p_230383_2_, p_230383_3_, p_230383_4_, p_230383_5_, p_230383_6_, p_230383_7_);
        }

        @Override
        protected void handleDataMarker(String meta, BlockPos pos, IServerWorld world, Random random, MutableBoundingBox box) {
            Random r = PosHelper.posToRandom(pos);
            switch (meta) {
                case "chest":
//                    createChest(world, box, random, pos, LootTable.CW_BLUEPRINT_BOX, null);
//                    break;
                case "poor_chest":
                    if (r.nextFloat() <= 0.3F) {
                        createChest(world, box, r, pos, LootTable.CW_BLUEPRINT_BOX, null);
                    } else {
                        createChest(world, box, r, pos, LootTable.getRandomLootTable(LootTable.SUNDRIES, random), null);
                    }
                    break;
            }
        }
    }
}
