package com.ardc.arkdust.playmethod.blueprint;

import com.ardc.arkdust.enums.BlueprintTypeEnum;
import com.ardc.arkdust.enums.BlueprintValueEnum;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class BlueprintTarget {
    public final BlueprintValueEnum value;
    public final BlueprintTypeEnum type;
    public final int level;
    public final ResourceLocation regId;
    public final List<ResourceLocation> dependence;

    BlueprintTarget(ResourceLocation id, BlueprintTypeEnum type, BlueprintValueEnum value, int level, List<ResourceLocation> dependence) {
        this.regId = id;
        this.type = type;
        this.value = value;
        this.level = level;
        this.dependence = dependence;
    }
}
