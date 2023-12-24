package com.ardc.arkdust.worldgen.structure.structure.cworld;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.helper.PosHelper;
import com.ardc.arkdust.helper.StructureHelper;
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
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

import static com.ardc.arkdust.worldgen.structure.ExtraStructureType.CW$UNDERTREE_BLUEPRINT;

public class UnderTreeBlueprintBox extends AboveWaterStructure {

    public static final Codec<UnderTreeBlueprintBox> CODEC = simpleCodec(UnderTreeBlueprintBox::new);

    public UnderTreeBlueprintBox(Structure.StructureSettings settings) {
        super(settings);
    }

    @Override
    public Optional<GenerationStub> ifAboveWater(GenerationContext context, BlockPos surfaceCenterPos) {
        return Optional.of(new GenerationStub(surfaceCenterPos,(builder)-> builder.addPiece(new Tem(context,surfaceCenterPos))));
    }

    @Override
    public StructureType<?> type() {
        return CW$UNDERTREE_BLUEPRINT;
    }

    public static class Tem extends CommonCWTemplatePiece {
        public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(Utils.MOD_ID,"cworld/undertree_blueprint/undertree_blueprint_1");
        public Tem(GenerationContext context, BlockPos pos) {
            super(ExtraStructurePieceType.CW$UNDERTREE_BLUEPRINT_BOX,context, pos,RESOURCE_LOCATION,true,-3);
        }

        public Tem(StructureTemplateManager manager,CompoundTag tag) {
            super(ExtraStructurePieceType.CW$UNDERTREE_BLUEPRINT_BOX, manager,tag);
        }
    }
}
