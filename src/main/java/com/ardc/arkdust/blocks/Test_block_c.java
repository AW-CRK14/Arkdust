package com.ardc.arkdust.blocks;

import com.ardc.arkdust.obj_property.ExtraMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class Test_block_c extends Block{
    public Test_block_c(){
        super(Properties.of(ExtraMaterial.TEST1).strength(2));
        //strength是强度，等价于非官方版中的hardnessAndResistance

    }
    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face)
    {
        return 5;
    }
}
