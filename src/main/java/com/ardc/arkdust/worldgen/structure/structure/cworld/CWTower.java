package com.ardc.arkdust.worldgen.structure.structure.cworld;

import com.ardc.arkdust.RunHelper.PosHelper;
import com.ardc.arkdust.RunHelper.StructureHelper;
import com.ardc.arkdust.resourcelocation.LootTable;
import com.ardc.arkdust.resourcelocation.Tag;
import com.ardc.arkdust.Utils;
import com.ardc.arkdust.worldgen.structure.ArdStructureAddInfo;
import com.ardc.arkdust.worldgen.structure.StructurePieceTypeAdd;
import com.ardc.arkdust.worldgen.structure.preobj.CommonCWStart;
import com.ardc.arkdust.worldgen.structure.preobj.CommonCWTemplatePiece;
import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
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
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class CWTower extends Structure<NoFeatureConfig> implements ArdStructureAddInfo {

    public static final ResourceLocation T1 = new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_1_0");
    public static final StructureHelper.StructureAndVariation T2 = new StructureHelper.StructureAndVariation(new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_2_0"),0.2F,new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_2_1") ,new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_2_2")});
    public static final StructureHelper.StructureAndVariation T3 = new StructureHelper.StructureAndVariation(new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_3_0"),0.4F,new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_3_1")});
    public static final StructureHelper.StructureAndVariation T4 = new StructureHelper.StructureAndVariation(new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_4_0"),new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_4_1")});
    public static final StructureHelper.StructureAndVariation T5 = new StructureHelper.StructureAndVariation(new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_5_0"),0.3F,new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_5_1") ,new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_5_2")});

    public CWTower(Codec<NoFeatureConfig> p_i231997_1_) {
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
        return 55;
    }

    @Override
    public int separation() {
        return 30;
    }

    @Override
    public int salt() {
        return 294698463;
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
        Random rr = new Random(random.nextLong());
        switch (random.nextInt(10)){
            case 0:
                return T1;
            case 4:
            case 5:
                return T3.getRandomPart(rr);
            case 6:
            case 7:
                return T4.getRandomPart(rr);
            case 8:
            case 9:
                return T5.getRandomPart(rr);
            default:
                return T2.getRandomPart(rr);
        }
    }

    public static class Piece extends CommonCWTemplatePiece {

        public Piece(TemplateManager templateManager, ResourceLocation structurePlace, BlockPos addPos, Rotation aRotation, Random random) {
            super(StructurePieceTypeAdd.CW_TOWER, templateManager, structurePlace, addPos, aRotation, random);
        }

        public Piece(TemplateManager templateManager, CompoundNBT nbt) {
            super(StructurePieceTypeAdd.CW_TOWER, templateManager, nbt);
        }

        public boolean postProcess(ISeedReader p_230383_1_, StructureManager p_230383_2_, ChunkGenerator p_230383_3_, Random p_230383_4_, MutableBoundingBox p_230383_5_, ChunkPos p_230383_6_, BlockPos p_230383_7_) {
            this.placeSettings.addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_AND_AIR);
            return super.postProcess(p_230383_1_, p_230383_2_, p_230383_3_, p_230383_4_, p_230383_5_, p_230383_6_, p_230383_7_);
        }

        @Override
        protected void handleDataMarker(String meta, BlockPos pos, IServerWorld world, Random random, MutableBoundingBox box) {
            Random r = PosHelper.posToRandom(pos);
            switch (meta){
                case "chest":
                    createChest(world,box,random,pos,LootTable.CW_BLUEPRINT_BOX,null);
                    break;
                case "poor_chest":
                    if(r.nextFloat() <= 0.3) {
                        createChest(world,box,r,pos,LootTable.CW_BLUEPRINT_BOX,null);
                    }else{
                        createChest(world,box,r,pos,LootTable.getRandomLootTable(LootTable.SUNDRIES,random),null);
                    }
                    break;
                case "chestD":
                    if(r.nextFloat() <= 0.2) {
                        createChest(world,box,r,pos,LootTable.CW_BLUEPRINT_BOX,null);
                    }else{
                        createChest(world,box,r,pos,LootTable.GENERAL_SUPPLY_BOX_A,null);
                    }
                    break;
                case "blockD":
                    world.setBlock(pos, Tag.Blocks.SUPPLY_BLOCK.getRandomElement(r).defaultBlockState(),2);
            }
        }
    }
}
