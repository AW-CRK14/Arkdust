package com.ardc.arkdust.worldgen.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SingleBlockPlacementConfig implements FeatureConfiguration {
    public static final Codec<SingleBlockPlacementConfig> CODEC = RecordCodecBuilder.create((codec)->
        codec.group(
                BlockState.CODEC.listOf().fieldOf("state").forGetter((i)->i.setState)
        ).apply(codec, SingleBlockPlacementConfig::new)
    );
    public final List<BlockState> setState;

    public SingleBlockPlacementConfig(List<BlockState> setBlock){
        this.setState = setBlock;
    }
}
