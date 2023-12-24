package com.ardc.arkdust.worldgen.structure.preobj;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.Optional;

public abstract class AboveWaterStructure extends Structure {
    public AboveWaterStructure(Structure.StructureSettings p_i231997_1_) {
        super(p_i231997_1_);
    }

    @Override
    public GenerationStep.Decoration step() {//与生成的位置有关，大概。
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }


    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        BlockPos center = context.chunkPos().getMiddleBlockPosition(0);
        center = center.atY(context.chunkGenerator().getFirstFreeHeight(center.getX(),center.getZ(), Heightmap.Types.WORLD_SURFACE,context.heightAccessor(),context.randomState()));
        return center.getY() <= 64 ? Optional.empty() : ifAboveWater(context,center);
    }

    public abstract Optional<GenerationStub> ifAboveWater(GenerationContext context,BlockPos surfaceCenterPos);
}
