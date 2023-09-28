package com.ardc.arkdust.worldgen.structure.structure.cworld;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.helper.PosHelper;
import com.ardc.arkdust.helper.StructureHelper;
import com.ardc.arkdust.helper.TagHelper;
import com.ardc.arkdust.resource.LootTable;
import com.ardc.arkdust.resource.Tag;
import com.ardc.arkdust.worldgen.structure.ExtraStructurePieceType;
import com.ardc.arkdust.worldgen.structure.ExtraStructureType;
import com.ardc.arkdust.worldgen.structure.preobj.CommonCWTemplatePiece;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

public class CWTower extends Structure {
    public static final Codec<CWTower> CODEC = simpleCodec(CWTower::new);

    public static final ResourceLocation T1 = new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_1_0");
    public static final StructureHelper.StructureAndVariation T2 = new StructureHelper.StructureAndVariation(new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_2_0"),0.2F,new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_2_1") ,new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_2_2")});
    public static final StructureHelper.StructureAndVariation T3 = new StructureHelper.StructureAndVariation(new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_3_0"),0.4F,new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_3_1")});
    public static final StructureHelper.StructureAndVariation T4 = new StructureHelper.StructureAndVariation(new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_4_0"),new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_4_1")});
    public static final StructureHelper.StructureAndVariation T5 = new StructureHelper.StructureAndVariation(new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_5_0"),0.3F,new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_5_1") ,new ResourceLocation(Utils.MOD_ID,"cworld/tower/tower_5_2")});

    public CWTower(Structure.StructureSettings p_i231997_1_) {
        super(p_i231997_1_);
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public StructureType<?> type() {
        return ExtraStructureType.CW$TOWER;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {

        BlockPos center = PosHelper.randomSkew(context,12);

        if(StructureHelper.isEachPlaceAvailable(context.chunkGenerator(), Heightmap.Types.WORLD_SURFACE_WG,3, PosHelper.getCenterAndSquareVertexPos(center,4,false,true),context.heightAccessor(),context.randomState()))
            return Optional.of(new GenerationStub(center.below(2),(builder)->builder.addPiece(new Piece(context.structureTemplateManager(),context.random(),center.below(2)))));
        return Optional.empty();

//        TemplateStructurePiece piece =
    }


    public static ResourceLocation randomLocation(RandomSource random){
        return switch (random.nextInt(10)) {
            case 0 -> T1;
            case 4, 5 -> T3.getRandomPart(random);
            case 6, 7 -> T4.getRandomPart(random);
            case 8, 9 -> T5.getRandomPart(random);
            default -> T2.getRandomPart(random);
        };
    }

    public static class Piece extends CommonCWTemplatePiece {

        public Piece(StructureTemplateManager templateManager, RandomSource random, BlockPos pos) {
            super(ExtraStructurePieceType.CW$TOWER, templateManager, random, pos,randomLocation(random));
        }

        public Piece(StructureTemplateManager manager, CompoundTag tag) {
            super(ExtraStructurePieceType.CW$TOWER, manager,tag);
        }

        @Override
        protected void handleDataMarker(String meta, BlockPos pos, ServerLevelAccessor levelAccessor, RandomSource randomSource, BoundingBox boundingBox) {
            switch (meta){
                case "chest":
                    createChest(levelAccessor,boundingBox,randomSource,pos,LootTable.CW_BLUEPRINT_BOX,null);
                    break;
                case "poor_chest":
                    if(randomSource.nextFloat() <= 0.3) {
                        createChest(levelAccessor,boundingBox,randomSource,pos,LootTable.CW_BLUEPRINT_BOX,null);
                    }else{
                        createChest(levelAccessor,boundingBox,randomSource,pos,LootTable.getRandomLootTable(LootTable.SUNDRIES,randomSource),null);
                    }
                    break;
                case "chestD":
                    if(randomSource.nextFloat() <= 0.2) {
                        createChest(levelAccessor,boundingBox,randomSource,pos,LootTable.CW_BLUEPRINT_BOX,null);
                    }else{
                        createChest(levelAccessor,boundingBox,randomSource,pos,LootTable.GENERAL_SUPPLY_BOX_A,null);
                    }
                    break;
                case "blockD":
                    levelAccessor.setBlock(pos, TagHelper.getRandomBlockElement(Tag.Blocks.SUPPLY_BLOCK,randomSource).defaultBlockState(),2);
            }
        }
    }
}
