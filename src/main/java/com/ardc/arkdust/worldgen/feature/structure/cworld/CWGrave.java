package com.ardc.arkdust.worldgen.feature.structure.cworld;

import com.ardc.arkdust.CodeMigration.RunHelper.BlockPosHelper;
import com.ardc.arkdust.CodeMigration.RunHelper.DirectionHelper;
import com.ardc.arkdust.CodeMigration.RunHelper.PosHelper;
import com.ardc.arkdust.CodeMigration.RunHelper.StructureHelper;
import com.ardc.arkdust.CodeMigration.resourcelocation.LootTable;
import com.ardc.arkdust.Utils;
import com.ardc.arkdust.worldgen.feature.ArdStructureAddInfo;
import com.ardc.arkdust.worldgen.feature.StructurePieceTypeAdd;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.*;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;

public class CWGrave extends Structure<NoFeatureConfig> implements ArdStructureAddInfo {

    public static final ResourceLocation[] SMALL_GRAVE = new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_1"),new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_2"),new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_3")};
    public static final ResourceLocation[] LARGE_GRAVE = new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_4")};

    public CWGrave(Codec<NoFeatureConfig> p_i231997_1_) {
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

        return StructureHelper.isEachPlaceAvailable(chunkGenerator, Heightmap.Type.WORLD_SURFACE_WG,2,
                BlockPosHelper.getCenterAndSquareVertexPos(centerOfChunk,8,false,true)
        );//获取此位置是否为流体（防止生成在水上）

    }

    @Override
    public int spacing() {
        return 70;
    }

    @Override
    public int separation() {
        return 20;
    }

    @Override
    public int salt() {
        return 24801274;
    }

    @Override
    public buildMode mode() {
        return buildMode.OVERWORLD;
//        return buildMode.NOISY_REF;
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
        public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {
            int x = chunkX * 16;
            int z = chunkZ * 16;
            int landHeight = chunkGenerator.getBaseHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG);
            BlockPos centerPos = new BlockPos(x,Math.max(landHeight-2,1),z);
            addChildren(templateManagerIn,this.pieces,random,centerPos);
            this.calculateBoundingBox();
            StructureHelper.movePieceToCenter(centerPos,this.boundingBox,this.pieces);
            this.calculateBoundingBox();
        }
    }

    public static void addChildren(TemplateManager templateManager, List<StructurePiece> pieceList, Random random, BlockPos centerPos) {
        Piece piece = new Piece(templateManager,random.nextFloat() >= 0.15F ? SMALL_GRAVE : LARGE_GRAVE,centerPos, Rotation.getRandom(random),random);
//        Piece piece = new Piece(templateManager, LARGE_GRAVE,centerPos, Rotation.getRandom(random),random);
        pieceList.add(piece);
    }

    public static class Piece extends TemplateStructurePiece{

        public final ResourceLocation templateLocation;
        public final Rotation rotation;

        public Piece(TemplateManager templateManager, ResourceLocation[] structurePlace, BlockPos addPos, Rotation aRotation, Random random) {
            super(StructurePieceTypeAdd.CW_GRAVE, 3);
            this.templateLocation = structurePlace[random.nextInt(structurePlace.length)];
            this.templatePosition = addPos;
            this.rotation = aRotation;
            this.loadTemplate(templateManager);
        }

        protected void addAdditionalSaveData(CompoundNBT nbt) {
            super.addAdditionalSaveData(nbt);
            nbt.putString("Template", this.templateLocation.toString());
            nbt.putString("Rot", this.rotation.name());
        }

        public Piece(TemplateManager templateManager, CompoundNBT nbt) {
            super(StructurePieceTypeAdd.CW_GRAVE, nbt);
            this.templateLocation = new ResourceLocation(nbt.getString("Template"));
            this.rotation = Rotation.valueOf(nbt.getString("Rot"));
            this.loadTemplate(templateManager);
//            System.out.println("piece loading test\nnbt:" + nbt.toString() + "\ntemplateManager:" + templateManager.toString() + "\ntemplate location:" + templateLocation + "\ntemplate:" + template + "\nsetting:" + placeSettings);
        }

        public boolean postProcess(ISeedReader p_230383_1_, StructureManager p_230383_2_, ChunkGenerator p_230383_3_, Random p_230383_4_, MutableBoundingBox p_230383_5_, ChunkPos p_230383_6_, BlockPos p_230383_7_) {
            this.placeSettings.addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_AND_AIR);
            return super.postProcess(p_230383_1_, p_230383_2_, p_230383_3_, p_230383_4_, p_230383_5_, p_230383_6_, p_230383_7_);
        }

        private void loadTemplate(TemplateManager templateManager) {
            Template template = templateManager.getOrCreate(this.templateLocation);
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setRotationPivot(new BlockPos(16,0,16)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            this.setup(template, this.templatePosition, placementsettings);
        }

        @Override
        protected void handleDataMarker(String dataName, BlockPos pos, IServerWorld world, Random preRandom, MutableBoundingBox box) {
            if ("chest".equals(dataName)) {
                Random r = PosHelper.posToRandom(pos);
                if(r.nextFloat() <= 0.2) {
                    createChest(world,box,r,pos,LootTable.CW_BLUEPRINT_BOX,null);
                }else{
                    createChest(world,box,r,pos,LootTable.getRandomLootTable(LootTable.SUNDRIES,preRandom),null);
                }
            }
//            ServerWorld serverWorld = world.getLevel();
//            System.out.println(serverWorld.toString());
        }
    }
}
