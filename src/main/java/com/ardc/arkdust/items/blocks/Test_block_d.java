package com.ardc.arkdust.items.blocks;

import com.ardc.arkdust.code_migration.Material;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class Test_block_d extends Block{
    public Test_block_d(){
        super(Properties.of(Material.TEST1).strength(10));
        //strength是强度，等价于非官方版中的hardnessAndResistance

    }
    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face)
    {
        return 2;
    }
}
