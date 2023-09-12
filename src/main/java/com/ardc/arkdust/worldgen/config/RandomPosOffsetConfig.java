package com.ardc.arkdust.worldgen.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.placement.IPlacementConfig;

import java.util.Random;

public class RandomPosOffsetConfig implements IPlacementConfig {
    public static final Codec<RandomPosOffsetConfig> CODEC = RecordCodecBuilder.create((codec)->
        codec.group(
                Codec.INT.fieldOf("x").forGetter((i)->i.r)
        ).apply(codec,RandomPosOffsetConfig::new)
    );

    public final int r;

    public RandomPosOffsetConfig(int range){
        r = range;
    }
}
