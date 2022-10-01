package com.ardc.arkdust.worldgen.feature;

import net.minecraft.util.Direction;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

import java.util.List;

public interface ArdStructureAddInfo {
    int spacing();

    int separation();

    int salt();

    default StructureSeparationSettings getSSSetting(){
        return new StructureSeparationSettings(Math.max(spacing(),10), Math.min(spacing()-5,separation()), salt());
    }

    buildMode mode();

    enum buildMode{
        NOISY_REF,
        OVERWORLD,
        NONE
    }
}
