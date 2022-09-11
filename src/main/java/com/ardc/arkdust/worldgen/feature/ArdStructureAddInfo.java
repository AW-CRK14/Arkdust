package com.ardc.arkdust.worldgen.feature;

import net.minecraft.world.gen.settings.StructureSeparationSettings;

public interface ArdStructureAddInfo {
    int spacing();

    int separation();

    int salt();

    default StructureSeparationSettings getSSSetting(){
        return new StructureSeparationSettings(Math.max(spacing(),10), Math.min(spacing()-5,separation()), salt());
    }
}
