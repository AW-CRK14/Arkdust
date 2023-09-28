package com.ardc.arkdust.blockstate;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FaceCullingWhenSameBlock extends Block {
    public FaceCullingWhenSameBlock(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean skipRendering(BlockState state, BlockState toState, Direction direction) {
        return state.equals(toState);
    }
}
