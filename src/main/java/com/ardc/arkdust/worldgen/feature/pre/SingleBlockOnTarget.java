package com.ardc.arkdust.worldgen.feature.pre;

import com.ardc.arkdust.worldgen.config.SingleBlockPlacementConfig;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class SingleBlockOnTarget extends Feature<SingleBlockPlacementConfig> {
    public SingleBlockOnTarget(Codec<SingleBlockPlacementConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SingleBlockPlacementConfig> context) {
        context.level().setBlock(context.origin(), context.config().setState.get(context.random().nextInt(context.config().setState.size())),2);
        return true;
    }
}
