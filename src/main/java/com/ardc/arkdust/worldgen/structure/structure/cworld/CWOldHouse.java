package com.ardc.arkdust.worldgen.structure.structure.cworld;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.helper.PosHelper;
import com.ardc.arkdust.helper.StructureHelper;
import com.ardc.arkdust.resource.LootTable;
import com.ardc.arkdust.worldgen.structure.ExtraStructurePieceType;
import com.ardc.arkdust.worldgen.structure.preobj.AboveWaterStructure;
import com.ardc.arkdust.worldgen.structure.preobj.CommonCWTemplatePiece;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

import static com.ardc.arkdust.worldgen.structure.ExtraStructureType.CW$OLD_HOUSE;

public class CWOldHouse extends AboveWaterStructure {

    public static final StructureHelper.StructureAndVariation H0 = new StructureHelper.StructureAndVariation(new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_1_0"),0.2F,new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_1_1"),new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_1_2"),new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_1_3"),new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_1_4")});
    public static final StructureHelper.StructureAndVariation H1 = new StructureHelper.StructureAndVariation(new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_2_0"),0.2F,new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_2_1"),new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_2_2"),new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_2_3"),new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_2_4"),new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_2_5")});
    public static final StructureHelper.StructureAndVariation H2 = new StructureHelper.StructureAndVariation(new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_3_0"),0.4F,new ResourceLocation[]{new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_3_1"),new ResourceLocation(Utils.MOD_ID,"cworld/old_house/old_house_3_2")});

    public static final Codec<CWOldHouse> CODEC = simpleCodec(CWOldHouse::new);

    public CWOldHouse(Structure.StructureSettings settings) {
        super(settings);
    }

    public Optional<GenerationStub> ifAboveWater(GenerationContext context, BlockPos surfaceCenterPos) {
        return Optional.of(new GenerationStub(surfaceCenterPos,(builder)->builder.addPiece(new Piece(context,surfaceCenterPos))));
    }

    @Override
    public StructureType<?> type() {
        return CW$OLD_HOUSE;
    }
    public static ResourceLocation randomLocation(RandomSource random){
        return switch (random.nextInt(3)){
            case 0 -> H0.getRandomPart(random);
            case 1 -> H1.getRandomPart(random);
            default -> H2.getRandomPart(random);
        };
    }

    public static class Piece extends CommonCWTemplatePiece {
//        private final LazyValue<BlockIgnoreStructureProcessor> STRUCTURE_BLOCK_AND_ARD_IGNBLOCK = new LazyValue<>(()->new BlockIgnoreStructureProcessor(ImmutableList.of(Blocks.STRUCTURE_BLOCK, BlockRegistry.structure_ignore_block.get()))) ;


        public Piece(GenerationContext context, BlockPos pos) {
            super(ExtraStructurePieceType.CW$OLD_HOUSE, context, pos,randomLocation(context.random()),true,-3);
        }

        public Piece(StructureTemplateManager manager, CompoundTag tag) {
            super(ExtraStructurePieceType.CW$OLD_HOUSE, manager,tag);
        }

        @Override
        protected void handleDataMarker(String meta, BlockPos pos, ServerLevelAccessor levelAccessor, RandomSource randomSource, BoundingBox boundingBox) {
            switch (meta) {
                case "chest":
//                    createChest(world, box, random, pos, LootTable.CW_BLUEPRINT_BOX, null);
//                    break;
                case "poor_chest":
                    if (randomSource.nextFloat() <= 0.3F) {
                        createChest(levelAccessor, boundingBox, randomSource, pos, LootTable.CW_BLUEPRINT_BOX, null);
                    } else {
                        createChest(levelAccessor, boundingBox, randomSource, pos, LootTable.getRandomLootTable(LootTable.SUNDRIES, randomSource), null);
                    }
                    break;
            }
        }
    }
}
