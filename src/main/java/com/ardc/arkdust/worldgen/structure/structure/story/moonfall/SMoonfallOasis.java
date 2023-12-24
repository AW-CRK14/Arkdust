package com.ardc.arkdust.worldgen.structure.structure.story.moonfall;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.helper.PosHelper;
import com.ardc.arkdust.playmethod.story.blockanditem.StoryPointBE;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.registry.StoryRegistry;
import com.ardc.arkdust.resource.LootTable;
import com.ardc.arkdust.worldgen.WorldStructureSavaData;
import com.ardc.arkdust.worldgen.structure.ExtraStructurePieceType;
import com.ardc.arkdust.worldgen.structure.preobj.CommonCWTemplatePiece;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

import static com.ardc.arkdust.worldgen.structure.ExtraStructureType.STORE$MF$OASIS;

public class SMoonfallOasis extends Structure {

    public static final Codec<SMoonfallOasis> CODEC = simpleCodec(SMoonfallOasis::new);

    public SMoonfallOasis(Structure.StructureSettings settings) {
        super(settings);
    }

    @Override
    public GenerationStep.Decoration step() {//与生成的位置有关，大概。
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        BlockPos center = context.chunkPos().getMiddleBlockPosition(0);
        return Optional.of(new GenerationStub(center.below(2),(builder)->builder.addPiece(new Piece(context,center))));
    }

    @Override
    public StructureType<?> type() {
        return STORE$MF$OASIS;
    }

    public static class Piece extends CommonCWTemplatePiece {

        public Piece(GenerationContext context, BlockPos pos) {
            super(ExtraStructurePieceType.STORE$MF$OASIS,context, pos, new ResourceLocation(Utils.MOD_ID,"story/moonfall/oasis"),true,-4);
            this.placeSettings = this.placeSettings().popProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
        }

        public Piece(StructureTemplateManager manager, CompoundTag tag) {
            super(ExtraStructurePieceType.STORE$MF$OASIS, manager,tag);
            this.placeSettings = this.placeSettings().popProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);

        }

        @Override
        protected void handleDataMarker(String meta, BlockPos pos, ServerLevelAccessor levelAccessor, RandomSource randomSource, BoundingBox boundingBox) {
            switch (meta){
                case "chest":
                    createChest(levelAccessor,boundingBox,randomSource,pos,LootTable.STORY_MOONFALL_CPLT_TREASURE,null);
                    break;
                case "fish":
                    levelAccessor.setBlock(pos, Blocks.BARREL.defaultBlockState().setValue(BarrelBlock.FACING, Direction.getRandom(randomSource)),2);
                    BlockEntity entity = levelAccessor.getBlockEntity(pos);
                    if(entity instanceof RandomizableContainerBlockEntity){
                        ((RandomizableContainerBlockEntity) entity).setLootTable(LootTable.FISH,randomSource.nextLong());
                    }
                    break;
                case "story":
                    boolean b = true;
                    if(!levelAccessor.getLevel().isClientSide) {
                        WorldStructureSavaData data = WorldStructureSavaData.get(levelAccessor.getLevel());
                        b = !data.getData().getBoolean("SMoonfallOasis");
                        if (b) data.setData("SMoonfallOasis",true);
                    }
                    if(b) {
                        levelAccessor.setBlock(pos, BlockRegistry.story_point.get().defaultBlockState(), 2);
                        BlockEntity e = levelAccessor.getBlockEntity(pos);
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
