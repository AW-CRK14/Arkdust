package com.ardc.arkdust.worldgen.structure.structure.cworld;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.helper.BlockHelper;
import com.ardc.arkdust.helper.ListAndMapHelper;
import com.ardc.arkdust.helper.PosHelper;
import com.ardc.arkdust.helper.StructureHelper;
import com.ardc.arkdust.playmethod.story.blockanditem.StoryPointBE;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.resource.LootTable;
import com.ardc.arkdust.worldgen.structure.ExtraStructurePieceType;
import com.ardc.arkdust.worldgen.structure.preobj.CommonCWTemplatePiece;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

import static com.ardc.arkdust.worldgen.structure.ExtraStructureType.CW$GRAVE;

public class CWGrave extends Structure {

    public static final ResourceLocation[] SMALL_GRAVE = new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_1"),new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_2"),new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_3"),new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_8"),new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_9"),new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_10")};
    public static final ResourceLocation[] LARGE_GRAVE = new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_4"),new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_5"),new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_6"),new ResourceLocation(Utils.MOD_ID,"cworld/grave/cw_grave_7")};

    public static final Codec<CWGrave> CODEC = simpleCodec(CWGrave::new);

    public CWGrave(Structure.StructureSettings settings) {
        super(settings);
    }

    @Override
    public GenerationStep.Decoration step() {//与生成的位置有关，大概。
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {

        BlockPos center = PosHelper.randomSkew(context,12);

        if(StructureHelper.isEachPlaceAvailable(context.chunkGenerator(), Heightmap.Types.WORLD_SURFACE_WG,4, PosHelper.getCenterAndSquareVertexPos(center,6,false,true),context.heightAccessor(),context.randomState()))
            return Optional.of(new GenerationStub(center.below(2),(builder)->builder.addPiece(new Piece(context.structureTemplateManager(),context.random(),center.below(2)))));
        return Optional.empty();
    }

    @Override
    public StructureType<?> type() {
        return CW$GRAVE;
    }

    public static class Piece extends CommonCWTemplatePiece {
        public Piece(StructureTemplateManager templateManager, RandomSource random, BlockPos pos) {
            super(ExtraStructurePieceType.CW$GRAVE, templateManager, random, pos, ListAndMapHelper.multiListGetElement(new Random(random.nextLong()),SMALL_GRAVE,SMALL_GRAVE,LARGE_GRAVE));
        }

        public Piece(StructureTemplateManager manager, CompoundTag tag) {
            super(ExtraStructurePieceType.CW$GRAVE, manager,tag);
        }

        @Override
        protected void handleDataMarker(String meta, BlockPos pos, ServerLevelAccessor levelAccessor, RandomSource randomSource, BoundingBox boundingBox) {
            switch (meta){
                case "chest":
                    if(randomSource.nextFloat() <= 0.2) {
                        createChest(levelAccessor,boundingBox,randomSource,pos,LootTable.CW_BLUEPRINT_BOX,null);
                    }else{
                        createChest(levelAccessor,boundingBox,randomSource,pos,LootTable.getRandomLootTable(LootTable.SUNDRIES,randomSource),null);
                    }
                    break;
                case "must_chest":
                    createChest(levelAccessor,boundingBox,randomSource,pos,LootTable.CW_BLUEPRINT_BOX,null);
                    break;
                case "poor_random":
                    float f = randomSource.nextFloat();
                    if(f<=0.1F){
                        levelAccessor.setBlock(pos,randomRotationBlockState(BlockRegistry.lay0_white_skeleton_block, randomSource) ,2);
                        break;
                    }else if(f<=0.2F) {
                        levelAccessor.setBlock(pos,randomRotationBlockState(BlockRegistry.sit_white_skeleton_block, randomSource),2);
                        break;
                    }else if(f<=0.3F) {
                        levelAccessor.setBlock(pos,randomTombstone(randomSource),2);
                        levelAccessor.setBlock(pos.below(), Blocks.OBSERVER.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.UP),2);
                        levelAccessor.setBlock(pos.below().below(),Blocks.TNT.defaultBlockState(),2);
                        break;
                    }else if(f<=0.35F){
                        createChest(levelAccessor,boundingBox,randomSource,pos.below().below(),LootTable.CW_BLUEPRINT_BOX,null);
                        levelAccessor.setBlock(pos.below(),randomBlackStone(randomSource),2);
                        break;
                    }
                case "random":
                    switch (randomSource.nextInt(8)){
                        case 0:
                        case 1:
                            Skeleton entity = EntityType.SKELETON.create(levelAccessor.getLevel());
                            if(entity != null) {
                                entity.moveTo(pos.above(2), 0, 0);
                                entity.setPersistenceRequired();
                                entity.finalizeSpawn(levelAccessor, levelAccessor.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.STRUCTURE, null, null);
                                levelAccessor.addFreshEntityWithPassengers(entity);
                            }
                        case 2:
                        case 3:
                            if(randomSource.nextFloat() <= 0.5F) {
                                createChest(levelAccessor,boundingBox,randomSource,pos.below(),LootTable.CW_BLUEPRINT_BOX,null);
                            }else{
                                createChest(levelAccessor,boundingBox,randomSource,pos.below(),LootTable.getRandomLootTable(LootTable.SUNDRIES,randomSource),null);
                            }
                        case 4:
                            levelAccessor.setBlock(pos,randomTombstone(randomSource),2);
                            break;
                        case 5:
                            levelAccessor.setBlock(pos,randomBlackStone(randomSource),2);
                            break;
                        case 6:
                            levelAccessor.setBlock(pos,Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION,randomSource.nextInt(16)),2);
                            break;
                        case 7:
                            levelAccessor.setBlock(pos,Blocks.LANTERN.defaultBlockState(), 2);
                            break;
                    }
                    break;
                case "story_point":
                    levelAccessor.setBlock(pos,Blocks.BONE_BLOCK.defaultBlockState(), 2);
                    break;
                case "story_point_2":
                    levelAccessor.setBlock(pos,BlockRegistry.story_point.get().defaultBlockState(), 2);
                    BlockEntity entity = levelAccessor.getBlockEntity(pos);
                    ((StoryPointBE) entity).setTitleAndKey("cw_past","cw_past.things_apart");
            }
//            ServerWorld serverWorld = world.getLevel();
//            System.out.println(serverWorld.toString());
        }

        public static final List<RegistryObject<Block>> tombstoneList = Arrays.asList(BlockRegistry.tombstone_2_1,BlockRegistry.tombstone_2_2,BlockRegistry.tombstone_2_3);
        //用于获取随机的坟
        public static BlockState randomTombstone(RandomSource random){
            return randomRotationBlockState(tombstoneList.get(random.nextInt(tombstoneList.size())),random);
        }

        public static final BlockHelper.BlockAndRegistryObjectList blackStoneList = new BlockHelper.BlockAndRegistryObjectList(Arrays.asList(Blocks.POLISHED_BLACKSTONE,Blocks.POLISHED_BLACKSTONE_BRICKS,Blocks.CHISELED_POLISHED_BLACKSTONE,Blocks.POLISHED_BLACKSTONE,Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS,Blocks.POLISHED_BLACKSTONE_WALL,Blocks.POLISHED_BLACKSTONE_BRICK_WALL), Collections.singletonList(BlockRegistry.blackstone_sand));
        //用于获取随机黑石
        public static BlockState randomBlackStone(RandomSource random){
            return blackStoneList.getRandomBlock(random).defaultBlockState();
        }

    }
    //用于获取随机旋转的某个方块的状态



}
