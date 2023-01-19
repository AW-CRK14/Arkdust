package com.ardc.arkdust.worldgen.structure.structure.cworld;

import com.ardc.arkdust.RunHelper.PosHelper;
import com.ardc.arkdust.RunHelper.StructureHelper;
import com.ardc.arkdust.resourcelocation.LootTable;
import com.ardc.arkdust.Utils;
import com.ardc.arkdust.worldgen.structure.ArdStructureAddInfo;
import com.ardc.arkdust.worldgen.structure.StructurePieceTypeAdd;
import com.ardc.arkdust.worldgen.structure.preobj.CommonCWStart;
import com.ardc.arkdust.worldgen.structure.preobj.CommonCWTemplatePiece;
import com.mojang.serialization.Codec;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CWBoat extends Structure<NoFeatureConfig> implements ArdStructureAddInfo {

    public static final List<ResourceLocation> list = Arrays.asList(new ResourceLocation(Utils.MOD_ID,"cworld/boat/boat_1"),new ResourceLocation(Utils.MOD_ID,"cworld/boat/boat_2"),new ResourceLocation(Utils.MOD_ID,"cworld/boat/boat_3"),new ResourceLocation(Utils.MOD_ID,"cworld/boat/boat_4"));

    public CWBoat(Codec<NoFeatureConfig> p_i231997_1_) {
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

        return StructureHelper.isEachPlaceWater(chunkGenerator, PosHelper.getCenterAndSquareVertexPos(centerOfChunk,6,false,true)
        );//获取此位置是否为流体（防止生成在水上）

    }

    @Override
    public int spacing() {
        return 35;
    }

    @Override
    public int separation() {
        return 12;
    }

    @Override
    public int salt() {
        return 2719002;
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
            Piece piece = new Piece(templateManagerIn,list.get(random.nextInt(list.size())),centerPos, Rotation.getRandom(random),random);
            pieces.add(piece);
        }

        @Override
        public int yRemove() {
            return -2;
        }
    }

    public static class Piece extends CommonCWTemplatePiece {

        public Piece(TemplateManager templateManager, ResourceLocation structurePlace, BlockPos addPos, Rotation aRotation, Random random) {
            super(StructurePieceTypeAdd.CW_BOAT, templateManager, structurePlace, addPos, aRotation, random);
        }

        public Piece(TemplateManager templateManager, CompoundNBT nbt) {
            super(StructurePieceTypeAdd.CW_BOAT, templateManager, nbt);
        }

        @Override
        protected void handleDataMarker(String meta, BlockPos pos, IServerWorld world, Random random, MutableBoundingBox box) {
            switch (meta){
                case "chest":
                    if(random.nextFloat() <= 0.5) {
                        createChest(world,box,random,pos,LootTable.CW_BLUEPRINT_BOX,null);
                    }else{
                        createChest(world,box,random,pos,LootTable.GENERAL_SUPPLY_BOX_A,null);
                    }
                    break;
                case "fish_chest":
                    world.setBlock(pos, Blocks.BARREL.defaultBlockState().setValue(BarrelBlock.FACING, Direction.getRandom(random)),2);
                    TileEntity entity = world.getBlockEntity(pos);
                    if(entity instanceof LockableLootTileEntity){
                        ((LockableLootTileEntity) entity).setLootTable(LootTable.FISH,random.nextLong());
                    }
                    break;
            }
        }
    }
}
