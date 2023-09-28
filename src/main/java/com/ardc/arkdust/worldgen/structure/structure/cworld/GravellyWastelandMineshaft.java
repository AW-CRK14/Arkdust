package com.ardc.arkdust.worldgen.structure.structure.cworld;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.advanced_obj.AATemJigsawPiece;
import com.ardc.arkdust.helper.ListAndMapHelper;
import com.ardc.arkdust.helper.PosHelper;
import com.ardc.arkdust.helper.TagHelper;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.resource.LootTable;
import com.ardc.arkdust.resource.Tag;
import com.ardc.arkdust.worldgen.structure.ExtraStructurePieceType;
import com.ardc.arkdust.worldgen.structure.processor.structure_processor.WastelandMineshaftProcessor;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.*;

import static com.ardc.arkdust.worldgen.structure.ExtraStructureType.CW$GRAVELLY_WASTELAND_MINESHAFT;

public class GravellyWastelandMineshaft extends Structure {

    public static final Codec<GravellyWastelandMineshaft> CODEC = simpleCodec(GravellyWastelandMineshaft::new);

    public GravellyWastelandMineshaft(Structure.StructureSettings settings) {
        super(settings);
    }

    @Override
    public GenerationStep.Decoration step() {//与生成的位置有关，大概。
        return GenerationStep.Decoration.UNDERGROUND_STRUCTURES;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {

        BlockPos center = PosHelper.randomSkew(context,12).above(context.random().nextInt(-16,32));

        return Optional.of(new GenerationStub(center,(builder)->AATemJigsawPiece.iterateAATemJigsawPiece(builder,
                new Piece(context.structureTemplateManager(),new ResourceLocation(Utils.MOD_ID,"cworld/wasteland_mineshaft/center"),center, Rotation.getRandom(context.random()),8),
                context.structureTemplateManager())));
    }

    @Override
    public StructureType<?> type() {
        return CW$GRAVELLY_WASTELAND_MINESHAFT;
    }


//    public static class Start extends AAStructureStart {
//
//        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
//            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
//        }
//
//        @Override
//        public void addChildren(TemplateManager templateManagerIn, List<StructurePiece> pieces, SharedSeedRandom random, BlockPos centerPos) {
//            Piece piece = new Piece(templateManagerIn,new ResourceLocation(Utils.MOD_ID,"cworld/wasteland_mineshaft/center"),centerPos, Rotation.getRandom(random),8);
//            iterateAATemJigsawPiece(piece,templateManagerIn);
//            piece.clearMap();
//        }
//
//        @Override
//        public BlockPos getCenterPos(int x, int z, int landHeight) {
//            return new BlockPos(x,20,z);
//        }
//
//        @Override
//        public int yRemove() {
//            return 10;
//        }
//
//        @Override
//        public boolean shouldMoveToCenter() {
//            return false;
//        }
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

        public Piece(StructureTemplateManager templateManager, ResourceLocation structurePlace, BlockPos addPos, Rotation rotation, int growth) {
            super(ExtraStructurePieceType.CW$GRAVELLY_WASTELAND_MINESHAFT, templateManager, structurePlace, addPos, rotation, BlockPos.ZERO, growth, BlockPos.ZERO,new MapNecessityTemJigsawPieceElement(randomPart,mustPart,endPart),null,"START");
        }

        public Piece(StructureTemplateManager templateManager, ResourceLocation structurePlace, BlockPos addPos, Rotation rotation, BlockPos rotationPivotPoint, int growth, BlockPos ignorePos, MapNecessityTemJigsawPieceElement element, Map<ChunkPos,List<BoundingBox>> bbMap, String generateFromKey) {
            super(ExtraStructurePieceType.CW$GRAVELLY_WASTELAND_MINESHAFT, templateManager, structurePlace, addPos, rotation, rotationPivotPoint, growth, ignorePos,element,bbMap,generateFromKey);
        }

        public Piece(StructureTemplateManager templateManager, CompoundTag nbt) {
            super(ExtraStructurePieceType.CW$GRAVELLY_WASTELAND_MINESHAFT, templateManager, nbt);
        }

        @Override
        public Pair<List<AATemJigsawPiece>, Boolean> specialPieceManager(StructureTemplateManager manager, ResourceLocation resourceLocation, BlockPos connectPos, StructureTemplate preparedTemplate, StructureTemplate.StructureBlockInfo jigsawInfo, BlockPos defaultSpawnPos, Rotation defaultRotation, StructureTemplate.StructureBlockInfo toJigsawInfo) {
//            boolean b = key2ElementMap.get("minecraft:pipeline").contains(this.templateLocation) || !jigsawInfo.nbt.getString("target").equals("minecraft:pipeline") || PosHelper.posToRandom(defaultSpawnPos).nextInt(2)==0;
//            boolean b = !jigsawInfo.nbt.getString("target").equals("minecraft:pipeline");
//            Utils.LOGGER.info("info:{RL:" + resourceLocation.toString() + ",generateFrom:" + this.generateFromKey + ",b:" + b + "}");
            if(!this.generateFromKey.equals("minecraft:pipeline") && jigsawInfo.nbt().getString("target").equals("minecraft:pipeline") && PosHelper.posAdd(connectPos)%3==0){
                return Pair.of(Collections.EMPTY_LIST,false);
//                return resourceLocation.getPath().equals("cworld/wasteland_mineshaft/shaft_2") ? Pair.of(Collections.singletonList(this.createPiece().create(manager,new ResourceLocation(Utils.MOD_ID,"cworld/wasteland_mineshaft/shaft_" + (connectPos.getY() >= 120 ? "top" : "2")),defaultSpawnPos,defaultRotation,PosHelper.posToRandom(connectPos),jigsawInfo.pos,3,connectPos,key2ElementMap,boundingBoxMap,jigsawInfo.nbt.getString("name"))),true) : null;
            }
            if(jigsawInfo.nbt().getString("target").equals("minecraft:shaft") && jigsawInfo.state().getValue(BlockStateProperties.ORIENTATION).front().equals(Direction.UP)){
                return Pair.of(Collections.singletonList(this.createPiece().create(manager,new ResourceLocation(Utils.MOD_ID,"cworld/wasteland_mineshaft/shaft_" + (connectPos.getY()>=66 ? "top" : "2")),defaultSpawnPos,defaultRotation,toJigsawInfo.pos(),3,connectPos,key2ElementMap,boundingBoxMap,jigsawInfo.nbt().getString("target"))),true);
            }
            if(jigsawInfo.nbt().getString("target").equals("minecraft:child_road_d") && jigsawInfo.state().getValue(BlockStateProperties.ORIENTATION).front().equals(Direction.UP) && connectPos.getY() > 60){
                return Pair.of(Collections.singletonList(this.createPiece().create(manager,new ResourceLocation(Utils.MOD_ID,"cworld/wasteland_mineshaft/childroad_de"),defaultSpawnPos,defaultRotation,toJigsawInfo.pos(),3,connectPos,key2ElementMap,boundingBoxMap,jigsawInfo.nbt().getString("target"))),true);
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
        protected void handleDataMarker(String meta, BlockPos pos, ServerLevelAccessor levelAccessor, RandomSource randomSource, BoundingBox boundingBox) {
            switch (meta){
                case "ground":
                case "pillar":
                    if(levelAccessor.getBlockState(pos).canBeReplaced())
                        levelAccessor.setBlock(pos, TagHelper.getRandomBlockElement(Tag.Blocks.STRUCTURE$WASTELAND_MINESHAFT_GROUND,randomSource).defaultBlockState(),2);
                    return;
                case "pillar_down":
                    if(levelAccessor.getBlockState(pos).canBeReplaced()){
                        levelAccessor.setBlock(pos, BlockRegistry.rushed_iron_block.get().defaultBlockState(),2);
                        BlockPos p = pos.below();
                        while (true){
                            if(levelAccessor.getBlockState(p).canBeReplaced()){
                                levelAccessor.setBlock(p,TagHelper.getRandomBlockElement(Tag.Blocks.STRUCTURE$WASTELAND_MINESHAFT_PILLAR_DOWN,randomSource).defaultBlockState(),2);
                                p = p.below();
                            }else {
                                levelAccessor.setBlock(p, Blocks.CHISELED_NETHER_BRICKS.defaultBlockState(),2);
                                break;
                            }
                        }
                    }
                    return;
                case "chest":
                    if(randomSource.nextFloat()>0.3F) {
                        createChest(levelAccessor, boundingBox, randomSource, pos, LootTable.GENERAL_SUPPLY_BOX_A, null);
                        return;
                    }
                case "treasure":
                        createChest(levelAccessor,boundingBox,randomSource,pos,LootTable.CW_BLUEPRINT_BOX,null);
                    return;
                case "sundries":
                    levelAccessor.setBlock(pos,TagHelper.getRandomBlockElement(Tag.Blocks.SUPPLY_BLOCK,randomSource).defaultBlockState(),2);
                    return;
                case "miner_chest":
                    createChest(levelAccessor,boundingBox,randomSource,pos,LootTable.MINER_CHEST_A,null);
            }
        }

        @Override
        public StructurePlaceSettings placementSettingsAttach(StructurePlaceSettings settings) {
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
