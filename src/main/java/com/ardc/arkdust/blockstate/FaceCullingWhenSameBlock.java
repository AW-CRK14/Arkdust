package com.ardc.arkdust.blockstate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
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
