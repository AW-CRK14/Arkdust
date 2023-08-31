package com.ardc.arkdust.worldgen.structure.structure.cworld;

import com.ardc.arkdust.advanced_obj.AATemJigsawPiece;
import com.ardc.arkdust.helper.ListAndMapHelper;
import com.ardc.arkdust.Utils;
import com.ardc.arkdust.helper.PosHelper;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.resourcelocation.LootTable;
import com.ardc.arkdust.resourcelocation.Tag;
import com.ardc.arkdust.worldgen.structure.ArdStructureAddInfo;
import com.ardc.arkdust.worldgen.structure.StructurePieceTypeAdd;
import com.ardc.arkdust.worldgen.structure.preobj.AAStructureStart;
import com.ardc.arkdust.worldgen.structure.processor.PreparedProcessor;
import com.ardc.arkdust.worldgen.structure.processor.structure_processor.WastelandMineshaftProcessor;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
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
import net.minecraft.world.gen.feature.jigsaw.JigsawOrientation;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.lwjgl.system.CallbackI;

import java.util.*;

public class GravellyWastelandMineshaft extends Structure<NoFeatureConfig> implements ArdStructureAddInfo {

    public GravellyWastelandMineshaft(Codec<NoFeatureConfig> p_i231997_1_) {
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
//        BlockPos centerOfChunk = new BlockPos(chunkX << 4, 0, chunkZ << 4);
//
//        return StructureHelper.isEachPlaceAvailable(chunkGenerator, Heightmap.Type.WORLD_SURFACE_WG,3,
//                PosHelper.getCenterAndSquareVertexPos(centerOfChunk,8,false,true)
//        );//获取此位置是否为流体（防止生成在水上）
        return true;
    }

    @Override
    public int spacing() {
        return 40;
    }

    @Override
    public int separation() {
        return 30;
    }

    @Override
    public int salt() {
        return 1472910;
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
            Piece piece = new Piece(templateManagerIn,new ResourceLocation(Utils.MOD_ID,"cworld/wasteland_mineshaft/center"),centerPos, Rotation.getRandom(random),8);
            iterateAATemJigsawPiece(piece,templateManagerIn);
            piece.clearMap();
        }

        @Override
        public BlockPos getCenterPos(int x, int z, int landHeight) {
            return new BlockPos(x,20,z);
        }

        @Override
        public int yRemove() {
            return 10;
        }

        @Override
        public boolean shouldMoveToCenter() {
            return false;
        }
    }

//    public static ResourceLocation randomLocation(Random random){
//        Random r = new Random(random.nextLong());
//        return r.nextFloat() >=0.35F ? H1.getRandomPart(random) : H2.getRandomPart(random);
//    }

    public static class Piece extends AATemJigsawPiece {
        public static Map<String,List<ResourceLocation>> randomPart = new HashMap<>();
        public static Map<String,List<ResourceLocation>> endPart = new HashMap<>();
        public static Map<String,List<ResourceLocation>> mustPart = new HashMap<>();
        static {
            ListAndMapHelper.tryAddElementToMapList(randomPart,"minecraft:shaft",ListAndMapHelper.string2RLListWithPath("cworld/wasteland_mineshaft/","shaft_2"));
            ListAndMapHelper.tryAddElementToMapList(randomPart,"minecraft:child_road",ListAndMapHelper.string2RLListWithPath("cworld/wasteland_mineshaft/childroad_","r1","r1","r4","r4","r2","r2","r2","r3","r4","r4","r4","r4","r5","r6","r6","r6","r7","r7","r1","r1","r4","r4","r2","r2","r2","r3","r4","r4","r4","r4","r5","r6","r6","r6","r7","r7","r9"));
//            ListAndMapHelper.tryAddElementToMapList(randomPart,"minecraft:child_road",ListAndMapHelper.string2RLListWithPath("cworld/wasteland_mineshaft/childroad_","r1","r1","r4","r4","r2","r2","r2","r3","r4","r4","r4","r4","r5","r6","r6","r6","r7","r7","r1","r1","r4","r4","r2","r2","r2","r3","r4","r4","r4","r4","r5","r6","r6","r6","r7","r7","r9","d0","d0","d0","d0","h1","h_box","h_furnace","h_rest","h1","h_box","h_furnace","h_rest","h1","h1","r8","r8","r8"));
            ListAndMapHelper.tryAddElementToMapList(randomPart,"minecraft:child_road_d",ListAndMapHelper.string2RLListWithPath("cworld/wasteland_mineshaft/childroad_","d1"));
            ListAndMapHelper.tryAddElementToMapList(randomPart,"minecraft:main_road",ListAndMapHelper.string2RLListWithPath("cworld/wasteland_mineshaft/main_road_","n","n","n","n","n","t","l","l"));
            ListAndMapHelper.tryAddElementToMapList(randomPart,"minecraft:pipeline",ListAndMapHelper.string2RLListWithPath("cworld/wasteland_mineshaft/pipeline_","r1","r2","r2","r2","r3","r4","r5","r6","r7","r1","r1","r1","r1","r1","r1","r1","r6","r6","d1","d2","d3","d4"));
            ListAndMapHelper.tryAddElementToMapList(endPart,"minecraft:main_road",ListAndMapHelper.string2RLListWithPath("cworld/wasteland_mineshaft/main_road_","e_1","e_1","e_1","e_2","e_2","e_3"));
            ListAndMapHelper.tryAddElementToMapList(endPart,"minecraft:child_road",ListAndMapHelper.string2RLListWithPath("cworld/wasteland_mineshaft/childroad_","e1","e2","e2","e3","e3","e3","e3"));
            ListAndMapHelper.tryAddElementToMapList(endPart,"minecraft:pipeline",ListAndMapHelper.string2RLListWithPath("cworld/wasteland_mineshaft/pipeline_","e1","e2","e3","d2","d3","d4","d1"));
            ListAndMapHelper.tryAddElementToMapList(endPart,"minecraft:child_road_d",ListAndMapHelper.string2RLListWithPath("cworld/wasteland_mineshaft/childroad_","he"));
//            ListAndMapHelper.tryAddElementToMapList(endPart,"minecraft:shaft",ListAndMapHelper.string2RLListWithPath("cworld/wasteland_mineshaft/","shaft_top"));
            ListAndMapHelper.tryAddElementToMapList(mustPart,"minecraft:pipeline",ListAndMapHelper.string2RLListWithPath("cworld/wasteland_mineshaft/pipeline_","m1","m1","m2","m3"));
            ListAndMapHelper.tryAddElementToMapList(mustPart,"minecraft:child_road",ListAndMapHelper.string2RLListWithPath("cworld/wasteland_mineshaft/childroad_","d0","d0","d0","d0","h1","h_box","h_furnace","h_rest","h1","h_box","h_furnace","h_rest","h1","h1","r8","r8","r8"));
        }

        public Piece(TemplateManager templateManager, ResourceLocation structurePlace, BlockPos addPos, Rotation rotation, int growth) {
            super(StructurePieceTypeAdd.GRAVELLY_WASTELAND_MINESHAFT, templateManager, structurePlace, addPos, rotation, BlockPos.ZERO, growth, BlockPos.ZERO,new MapNecessityTemJigsawPieceElement(randomPart,mustPart,endPart),null,"START");
            this.key2ElementMap.buildTemplateMap(templateManager);
        }

        public Piece(TemplateManager templateManager, ResourceLocation structurePlace, BlockPos addPos, Rotation rotation, BlockPos rotationPivotPoint, int growth,BlockPos ignorePos,MapNecessityTemJigsawPieceElement element,Map<ChunkPos,List<MutableBoundingBox>> bbMap,String generateFromKey) {
            super(StructurePieceTypeAdd.GRAVELLY_WASTELAND_MINESHAFT, templateManager, structurePlace, addPos, rotation, rotationPivotPoint, growth, ignorePos,element,bbMap,generateFromKey);
        }

        public Piece(TemplateManager templateManager, CompoundNBT nbt) {
            super(StructurePieceTypeAdd.GRAVELLY_WASTELAND_MINESHAFT, templateManager, nbt);
        }

        @Override
        public Pair<List<AATemJigsawPiece>, Boolean> specialPieceManager(TemplateManager manager, ResourceLocation resourceLocation, BlockPos connectPos, Template preparedTemplate, Template.BlockInfo jigsawInfo, BlockPos defaultSpawnPos, Rotation defaultRotation,Template.BlockInfo toJigsawInfo) {
//            boolean b = key2ElementMap.get("minecraft:pipeline").contains(this.templateLocation) || !jigsawInfo.nbt.getString("target").equals("minecraft:pipeline") || PosHelper.posToRandom(defaultSpawnPos).nextInt(2)==0;
//            boolean b = !jigsawInfo.nbt.getString("target").equals("minecraft:pipeline");
//            Utils.LOGGER.info("info:{RL:" + resourceLocation.toString() + ",generateFrom:" + this.generateFromKey + ",b:" + b + "}");
            if(!this.generateFromKey.equals("minecraft:pipeline") && jigsawInfo.nbt.getString("target").equals("minecraft:pipeline") && PosHelper.posAdd(connectPos)%3==0){
                return Pair.of(Collections.EMPTY_LIST,false);
//                return resourceLocation.getPath().equals("cworld/wasteland_mineshaft/shaft_2") ? Pair.of(Collections.singletonList(this.createPiece().create(manager,new ResourceLocation(Utils.MOD_ID,"cworld/wasteland_mineshaft/shaft_" + (connectPos.getY() >= 120 ? "top" : "2")),defaultSpawnPos,defaultRotation,PosHelper.posToRandom(connectPos),jigsawInfo.pos,3,connectPos,key2ElementMap,boundingBoxMap,jigsawInfo.nbt.getString("name"))),true) : null;
            }
            if(jigsawInfo.nbt.getString("target").equals("minecraft:shaft") && jigsawInfo.state.getValue(BlockStateProperties.ORIENTATION).front().equals(Direction.UP)){
                return Pair.of(Collections.singletonList(this.createPiece().create(manager,new ResourceLocation(Utils.MOD_ID,"cworld/wasteland_mineshaft/shaft_" + (connectPos.getY()>=66 ? "top" : "2")),defaultSpawnPos,defaultRotation,toJigsawInfo.pos,3,connectPos,key2ElementMap,boundingBoxMap,jigsawInfo.nbt.getString("target"))),true);
            }
            if(jigsawInfo.nbt.getString("target").equals("minecraft:child_road_d") && jigsawInfo.state.getValue(BlockStateProperties.ORIENTATION).front().equals(Direction.UP) && connectPos.getY() > 60){
                return Pair.of(Collections.singletonList(this.createPiece().create(manager,new ResourceLocation(Utils.MOD_ID,"cworld/wasteland_mineshaft/childroad_de"),defaultSpawnPos,defaultRotation,toJigsawInfo.pos,3,connectPos,key2ElementMap,boundingBoxMap,jigsawInfo.nbt.getString("target"))),true);
            }

            return null;
        }

        public Pair<Boolean,Integer> setChildGrowth(String key,ResourceLocation toLocation){
            if(!this.generateFromKey.equals("minecraft:child_road") && key.equals("minecraft:child_road"))
                return Pair.of(true,11);
            if(!this.generateFromKey.equals("minecraft:pipeline") && key.equals("minecraft:pipeline") && new Random().nextInt(4) == 0)
                return Pair.of(true,Math.max(this.growth,7));
            if(toLocation.getPath().equals("cworld/wasteland_mineshaft/childroad_d0"))
                return Pair.of(true,4);
            return Pair.of(false,0);
        }

        @Override
        protected void handleDataMarker(String key, BlockPos pos, IServerWorld world, Random random, MutableBoundingBox box) {
            switch (key){
                case "ground":
                case "pillar":
                    if(world.getBlockState(pos).getMaterial().isReplaceable())
                        world.setBlock(pos, Tag.Blocks.STRUCTURE$WASTELAND_MINESHAFT_GROUND.getRandomElement(random).defaultBlockState(),2);
                    return;
                case "pillar_down":
                    if(world.getBlockState(pos).getMaterial().isReplaceable()){
                        world.setBlock(pos, BlockRegistry.rushed_iron_block.get().defaultBlockState(),2);
                        BlockPos p = pos.below();
                        while (true){
                            if(world.getBlockState(p).getMaterial().isReplaceable()){
                                world.setBlock(p,Tag.Blocks.STRUCTURE$WASTELAND_MINESHAFT_PILLAR_DOWN.getRandomElement(random).defaultBlockState(),3);
                                p = p.below();
                            }else {
                                world.setBlock(p, Blocks.CHISELED_NETHER_BRICKS.defaultBlockState(),2);
                                break;
                            }
                        }
                    }
                    return;
                case "chest":
                    if(random.nextFloat()>0.3F) {
                        createChest(world, box, random, pos, LootTable.GENERAL_SUPPLY_BOX_A, null);
                        return;
                    }
                case "treasure":
                        createChest(world,box,random,pos,LootTable.CW_BLUEPRINT_BOX,null);
                    return;
                case "sundries":
                    world.setBlock(pos,Tag.Blocks.SUPPLY_BLOCK.getRandomElement(random).defaultBlockState(),2);
                    return;
                case "miner_chest":
                    createChest(world,box,random,pos,LootTable.MINER_CHEST_A,null);
            }
        }

        @Override
        public PlacementSettings placementSettingsAttach(PlacementSettings settings) {
            return settings.addProcessor(WastelandMineshaftProcessor.INSTANCE);
        }

        @Override
        public CreatePiece createPiece() {
            return Piece::new;
        }

        @Override
        public int limitGrowth() {
            return 7;
        }

        @Override
        public int minGrowthAllow() {
            return 7;
        }
    }
}
