package com.ardc.arkdust.worldgen.placement;

import com.ardc.arkdust.worldgen.config.RandomPosOffsetConfig;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.SimplePlacement;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.stream.Stream;

public class RandomOffsetPlacement extends SimplePlacement<RandomPosOffsetConfig> {
    public RandomOffsetPlacement(Codec<RandomPosOffsetConfig> configCodec) {
        super(configCodec);
    }

    @Override
    protected Stream<BlockPos> place(Random random, RandomPosOffsetConfig config, BlockPos pos) {
        return Stream.of(pos.offset(random.nextInt(2*config.r)-config.r,0,random.nextInt(2*config.r)-config.r));
    }
}
