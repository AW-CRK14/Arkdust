package com.ardc.arkdust.worldgen.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SingleBlockPlacementConfig implements IFeatureConfig {
    public static final Codec<SingleBlockPlacementConfig> CODEC = RecordCodecBuilder.create((codec)->
        codec.group(
                Codec.BOOL.fieldOf("surface").forGetter((i)->i.surface),
                Codec.INT.fieldOf("y").forGetter((i)->i.y),
                Codec.INT.fieldOf("offset").forGetter((i)->i.yRandomOffset),
                BlockState.CODEC.listOf().fieldOf("place").forGetter((i)->i.canPlaceOn.stream().map(Block::defaultBlockState).collect(Collectors.toList())),
                BlockState.CODEC.listOf().fieldOf("state").forGetter((i)->i.setState)
        ).apply(codec, SingleBlockPlacementConfig::new)
    );

    public final boolean surface;
    public final int y;
    public final int yRandomOffset;
    public final List<Block> canPlaceOn;
    public final List<BlockState> setState;

    public SingleBlockPlacementConfig(int yOffset,int yRandom,boolean generateOnSurface,List<Block> canPlaceOn,List<BlockState> setBlock){
        this.surface = generateOnSurface;
        this.y = yOffset;
        this.yRandomOffset = yRandom;
        this.canPlaceOn = canPlaceOn;
        this.setState = setBlock;
    }

    public SingleBlockPlacementConfig(Block canPlaceOn,BlockState setBlock){
        this.surface = true;
        this.y = 0;
        this.yRandomOffset = 0;
        this.canPlaceOn = Collections.singletonList(canPlaceOn);
        this.setState = Collections.singletonList(setBlock);
    }

    public SingleBlockPlacementConfig(List<Block> canPlaceOn,BlockState setBlock){
        this.surface = true;
        this.y = 0;
        this.yRandomOffset = 0;
        this.canPlaceOn = canPlaceOn;
        this.setState = Collections.singletonList(setBlock);
    }

    public SingleBlockPlacementConfig(boolean generateOnSurface,int yOffset,int yRandom,List<BlockState> canPlaceOnState,List<BlockState> setBlock){
        this(yOffset,yRandom,generateOnSurface,canPlaceOnState.stream().map(BlockState::getBlock).collect(Collectors.toList()), setBlock);
    }
}
