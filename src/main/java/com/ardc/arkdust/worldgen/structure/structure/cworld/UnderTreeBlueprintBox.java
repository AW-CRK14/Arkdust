package com.ardc.arkdust.worldgen.structure.structure.cworld;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.helper.PosHelper;
import com.ardc.arkdust.helper.StructureHelper;
import com.ardc.arkdust.worldgen.structure.ExtraStructurePieceType;
import com.ardc.arkdust.worldgen.structure.preobj.CommonCWTemplatePiece;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

import static com.ardc.arkdust.worldgen.structure.ExtraStructureType.CW$UNDERTREE_BLUEPRINT;

public class UnderTreeBlueprintBox extends Structure {

    public static final Codec<UnderTreeBlueprintBox> CODEC = simpleCodec(UnderTreeBlueprintBox::new);

    public UnderTreeBlueprintBox(Structure.StructureSettings settings) {
        super(settings);
    }

    @Override
    public GenerationStep.Decoration step() {//与生成的位置有关，大概。
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {

        BlockPos center = PosHelper.randomSkew(context,12);
        Holder<Biome> biome = context.biomeSource().getNoiseBiome(QuartPos.fromBlock(center.getX()), QuartPos.fromBlock(center.getY()), QuartPos.fromBlock(center.getZ()), context.randomState().sampler());
        if(!StructureHelper.checkPosNoWater(context,center) || biome.value().getBaseTemperature() >= 1.2F) return Optional.empty();

        return Optional.of(new GenerationStub(center,(builder)->builder.addPiece(new Tem(context.structureTemplateManager(),context.random(),center))));
//        TemplateStructurePiece piece =
    }

    @Override
    public StructureType<?> type() {
        return CW$UNDERTREE_BLUEPRINT;
    }

    public static class Tem extends CommonCWTemplatePiece {
        public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(Utils.MOD_ID,"cworld/undertree_blueprint/undertree_blueprint_1");
        public Tem(StructureTemplateManager templateManager, RandomSource random, BlockPos pos) {
            super(ExtraStructurePieceType.CW$UNDERTREE_BLUEPRINT_BOX, templateManager, random, pos,RESOURCE_LOCATION);
        }

        public Tem(StructureTemplateManager manager,CompoundTag tag) {
            super(ExtraStructurePieceType.CW$UNDERTREE_BLUEPRINT_BOX, manager,tag);
        }
    }
}
