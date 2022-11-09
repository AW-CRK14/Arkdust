package com.ardc.arkdust.worldgen.feature.structure.cworld;

import com.ardc.arkdust.CodeMigration.RunHelper.BlockHelper;
import com.ardc.arkdust.CodeMigration.RunHelper.DirectionHelper;
import com.ardc.arkdust.CodeMigration.RunHelper.PosHelper;
import com.ardc.arkdust.CodeMigration.RunHelper.StructureHelper;
import com.ardc.arkdust.CodeMigration.resourcelocation.LootTable;
import com.ardc.arkdust.NewPlayingMethod.story.blockanditem.StoryPointBE;
import com.ardc.arkdust.Utils;
import com.ardc.arkdust.preobject.BlockState.RotateBlock;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.worldgen.feature.ArdStructureAddInfo;
import com.ardc.arkdust.worldgen.feature.StructurePieceTypeAdd;
import com.mojang.serialization.Codec;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
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
import net.minecraftforge.fml.RegistryObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CWGrave extends Structure<NoFeatureConfig> implements ArdStructureAddInfo {

    public static final ResourceLocation[] SMALL_GRAVE = new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_1"),new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_2"),new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_3"),new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_8"),new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_9"),new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_10")};
    public static final ResourceLocation[] LARGE_GRAVE = new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_4"),new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_5"),new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_6"),new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_7")};

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

        return StructureHelper.isEachPlaceAvailable(chunkGenerator, Heightmap.Type.WORLD_SURFACE_WG,3,
                PosHelper.getCenterAndSquareVertexPos(centerOfChunk,8,false,true)
        );//获取此位置是否为流体（防止生成在水上）

    }

    @Override
    public int spacing() {
        return 45;
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
//        Piece piece = new Piece(templateManager,new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_10")},centerPos, Rotation.getRandom(random),random);
        Piece piece = new Piece(templateManager,random.nextFloat() >= 0.2F ? SMALL_GRAVE : LARGE_GRAVE,centerPos, Rotation.getRandom(random),random);
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
            Random r = PosHelper.posToRandom(pos);
            switch (dataName){
                case "chest":
                    if(r.nextFloat() <= 0.2) {
                        createChest(world,box,r,pos,LootTable.CW_BLUEPRINT_BOX,null);
                    }else{
                        createChest(world,box,r,pos,LootTable.getRandomLootTable(LootTable.SUNDRIES,preRandom),null);
                    }
                    break;
                case "must_chest":
                    createChest(world,box,r,pos,LootTable.CW_BLUEPRINT_BOX,null);
                    break;
                case "poor_random":
                    float f = r.nextFloat();
                    if(f<=0.1F){
                        world.setBlock(pos,randomRotationBlockState(BlockRegistry.lay0_white_skeleton_block, r) ,2);
                        break;
                    }else if(f<=0.2F) {
                        world.setBlock(pos,randomRotationBlockState(BlockRegistry.sit_white_skeleton_block, r),2);
                        break;
                    }else if(f<=0.3F) {
                        world.setBlock(pos,randomTombstone(r),2);
                        world.setBlock(pos.below(),Blocks.OBSERVER.defaultBlockState().setValue(DirectionalBlock.FACING,Direction.UP),2);
                        world.setBlock(pos.below().below(),Blocks.TNT.defaultBlockState(),2);
                        break;
                    }else if(f<=0.35F){
                        createChest(world,box,r,pos.below().below(),LootTable.CW_BLUEPRINT_BOX,null);
                        world.setBlock(pos.below(),randomBlackStone(r),2);
                        break;
                    }
                case "random":
                    switch (r.nextInt(8)){
                        case 0:
                        case 1:
                            SkeletonEntity entity = EntityType.SKELETON.create(world.getLevel());
                            if(entity != null) {
                                entity.moveTo(pos.above(2), 0, 0);
                                entity.setPersistenceRequired();
                                entity.finalizeSpawn(world, world.getCurrentDifficultyAt(entity.blockPosition()), SpawnReason.STRUCTURE, null, null);
                                world.addFreshEntityWithPassengers(entity);
                            }
                        case 2:
                        case 3:
                            Random newR = new Random();
                            newR.setSeed(r.nextLong());
                            if(newR.nextFloat() <= 0.5F) {
                                createChest(world,box,r,pos.below(),LootTable.CW_BLUEPRINT_BOX,null);
                            }else{
                                createChest(world,box,r,pos.below(),LootTable.getRandomLootTable(LootTable.SUNDRIES,preRandom),null);
                            }
                        case 4:
                            world.setBlock(pos,randomTombstone(r),2);
                            break;
                        case 5:
                            world.setBlock(pos,randomBlackStone(r),2);
                            break;
                        case 6:
                            world.setBlock(pos,Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION,r.nextInt(16)),2);
                            break;
                        case 7:
                            world.setBlock(pos,Blocks.LANTERN.defaultBlockState(), 2);
                            break;
                    }
                    break;
                case "story_point":
                    world.setBlock(pos,Blocks.BONE_BLOCK.defaultBlockState(), 2);
                    break;
                case "story_point_2":
                    world.setBlock(pos,BlockRegistry.story_point.get().defaultBlockState(), 2);
                    TileEntity entity = world.getBlockEntity(pos);
                    if(entity instanceof StoryPointBE){
                        ((StoryPointBE) entity).setKey("cw_past.things_apart");
                        ((StoryPointBE) entity).setTitle("cw_past");
                    }
            }
//            ServerWorld serverWorld = world.getLevel();
//            System.out.println(serverWorld.toString());
        }
    }

    //用于获取随机旋转的某个方块的状态
    public static BlockState randomRotationBlockState(RegistryObject<Block> block, Random random){
        BlockState state = block.get().defaultBlockState();
        if(state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)){
            Direction direction = DirectionHelper.RandomDirection(DirectionHelper.direcList.HORIZON_DIRECTION,random);
            state = state.setValue(block.get() instanceof RotateBlock ? RotateBlock.HORIZONTAL_FACING : BlockStateProperties.HORIZONTAL_FACING,direction);
//            System.out.println("Direction:" + direction + "\nBlockState:" + state.toString());
        }
        return state;
    }


    public static final List<RegistryObject<Block>> tombstoneList = Arrays.asList(BlockRegistry.tombstone_2_1,BlockRegistry.tombstone_2_2,BlockRegistry.tombstone_2_3);
    //用于获取随机的坟
    public static BlockState randomTombstone(Random random){
        return randomRotationBlockState(tombstoneList.get(random.nextInt(tombstoneList.size())),random);
    }

    public static final BlockHelper.BlockAndRegistryObjectList blackStoneList = new BlockHelper.BlockAndRegistryObjectList(Arrays.asList(Blocks.POLISHED_BLACKSTONE,Blocks.POLISHED_BLACKSTONE_BRICKS,Blocks.CHISELED_POLISHED_BLACKSTONE,Blocks.POLISHED_BLACKSTONE,Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS,Blocks.POLISHED_BLACKSTONE_WALL,Blocks.POLISHED_BLACKSTONE_BRICK_WALL), Collections.singletonList(BlockRegistry.blackstone_sand));
    //用于获取随机黑石
    public static BlockState randomBlackStone(Random random){
        return blackStoneList.getRandomBlock(random).defaultBlockState();
    }
}
