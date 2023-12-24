package com.ardc.arkdust.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TestBlockC extends Block {
    public TestBlockC(){
        super(Properties.of().strength(2));
        //strength是强度，等价于非官方版中的hardnessAndResistance

    }
    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
    {
        return 5;
    }
}
