package com.ardc.arkdust.worldgen.structure.structure.story.moonfall;

import com.ardc.arkdust.helper.PosHelper;
import com.ardc.arkdust.helper.StructureHelper;
import com.ardc.arkdust.registry.StoryRegistry;
import com.ardc.arkdust.resourcelocation.LootTable;
import com.ardc.arkdust.Utils;
import com.ardc.arkdust.playmethod.story.blockanditem.StoryPointBE;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.worldgen.WorldStructureSavaData;
import com.ardc.arkdust.worldgen.structure.ArdStructureAddInfo;
import com.ardc.arkdust.worldgen.structure.StructurePieceTypeAdd;
import com.ardc.arkdust.worldgen.structure.preobj.AAStructureStart;
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
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class SMoonfallOasis extends Structure<NoFeatureConfig> implements ArdStructureAddInfo {

    public SMoonfallOasis(Codec<NoFeatureConfig> p_i231997_1_) {
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
                PosHelper.getCenterAndSquareVertexPos(centerOfChunk,6,false,true)
        );//获取此位置是否为流体（防止生成在水上）

    }

    @Override
    public int spacing() {
        return 240;
    }

    @Override
    public int separation() {
        return 800;
    }

    @Override
    public int salt() {
        return 382589659;
    }

    @Override
    public buildMode mode() {
        return buildMode.OVERWORLD;
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return Start::new;
    }

    public static class Start extends AAStructureStart {

        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override
        public void addChildren(TemplateManager templateManagerIn, List<StructurePiece> pieces, SharedSeedRandom random, BlockPos centerPos) {
            Piece piece = new Piece(templateManagerIn,new ResourceLocation(Utils.MOD_ID,"story/moonfall/oasis"),centerPos, Rotation.getRandom(random),random);
            pieces.add(piece);
        }

        @Override
        public int yRemove() {
            return -5;
        }
    }

    public static class Piece extends CommonCWTemplatePiece {

        public Piece(TemplateManager templateManager, ResourceLocation structurePlace, BlockPos addPos, Rotation aRotation, Random random) {
            super(StructurePieceTypeAdd.STORY_MOONFALL_OASIS, templateManager, structurePlace, addPos, aRotation, random);
        }

        public Piece(TemplateManager templateManager, CompoundNBT nbt) {
            super(StructurePieceTypeAdd.STORY_MOONFALL_OASIS, templateManager, nbt);
        }

        @Override
        protected void handleDataMarker(String meta, BlockPos pos, IServerWorld world, Random random, MutableBoundingBox box) {
            switch (meta){
                case "chest":
                    createChest(world,box,random,pos,LootTable.STORY_MOONFALL_CPLT_TREASURE,null);
                    break;
                case "fish":
                    world.setBlock(pos, Blocks.BARREL.defaultBlockState().setValue(BarrelBlock.FACING, Direction.getRandom(random)),2);
                    TileEntity entity = world.getBlockEntity(pos);
                    if(entity instanceof LockableLootTileEntity){
                        ((LockableLootTileEntity) entity).setLootTable(LootTable.FISH,random.nextLong());
                    }
                    break;
                case "story":
                    boolean b = true;
                    if(!world.getLevel().isClientSide) {
                        WorldStructureSavaData data = WorldStructureSavaData.get(world.getLevel());
                        b = !data.getData().getBoolean("SMoonfallOasis");
                        if (b) data.setData("SMoonfallOasis",true);
                    }
                    if(b) {
                        world.setBlock(pos, BlockRegistry.story_point.get().defaultBlockState(), 2);
                        TileEntity e = world.getBlockEntity(pos);
                        if (e instanceof StoryPointBE) {
                            ((StoryPointBE) e).setBagAndLevel(StoryRegistry.MOON_FALL, 1);
                        }
                    }
                    break;
                case "haisi":
                    break;
            }
        }
    }
}
