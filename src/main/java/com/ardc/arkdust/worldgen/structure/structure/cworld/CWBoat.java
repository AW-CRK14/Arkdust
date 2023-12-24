package com.ardc.arkdust.worldgen.structure.structure.cworld;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.helper.StructureHelper;
import com.ardc.arkdust.resource.LootTable;
import com.ardc.arkdust.worldgen.structure.ExtraStructurePieceType;
import com.ardc.arkdust.worldgen.structure.ExtraStructureType;
import com.ardc.arkdust.worldgen.structure.preobj.CommonCWTemplatePiece;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CWBoat extends Structure {
    public static final Codec<CWBoat> CODEC = simpleCodec(CWBoat::new);
    private static final Logger BOAT_STRUCTURE_DEBUGGER = LogManager.getLogger();

    public static final List<ResourceLocation> list = Arrays.asList(new ResourceLocation(Utils.MOD_ID,"cworld/boat/boat_1"),new ResourceLocation(Utils.MOD_ID,"cworld/boat/boat_2"),new ResourceLocation(Utils.MOD_ID,"cworld/boat/boat_3"),new ResourceLocation(Utils.MOD_ID,"cworld/boat/boat_4"));

    public CWBoat(Structure.StructureSettings p_i231997_1_) {
        super(p_i231997_1_);
    }

    @Override
    public GenerationStep.Decoration step() {//与生成的位置有关，大概。
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        BlockPos pos = context.chunkPos().getMiddleBlockPosition(61);
        return Optional.of(new Structure.GenerationStub(pos,(builder)->builder.addPiece(new Piece(context,list,pos))));
    }

    @Override
    public StructureType<?> type() {
        return ExtraStructureType.CW$BOAT;
    }

    public static class Piece extends CommonCWTemplatePiece {

        public Piece(GenerationContext context, List<ResourceLocation> resourceLocation, BlockPos addPos) {
            super(ExtraStructurePieceType.CW$BOAT,context,addPos,resourceLocation);
        }

        public Piece(StructureTemplateManager templateManager, CompoundTag nbt) {
            super(ExtraStructurePieceType.CW$BOAT, templateManager, nbt);
        }

//        @Override
//        public void postProcess(WorldGenLevel p_226899_, StructureManager p_226900_, ChunkGenerator p_226901_, RandomSource p_226902_, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
////            BOAT_STRUCTURE_DEBUGGER.info("CWBoatPiece post process:{pos:{},chunk:{}}}",blockPos,chunkPos);
//            super.postProcess(p_226899_, p_226900_, p_226901_, p_226902_, boundingBox, chunkPos, blockPos);
//        }

        @Override
        protected void handleDataMarker(String meta, BlockPos pos, ServerLevelAccessor world, RandomSource random, BoundingBox box) {
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
                    BlockEntity entity = world.getBlockEntity(pos);
                    if(entity instanceof RandomizableContainerBlockEntity){
                        ((RandomizableContainerBlockEntity) entity).setLootTable(LootTable.FISH,random.nextLong());
                    }
                    break;
            }
        }
    }
}
