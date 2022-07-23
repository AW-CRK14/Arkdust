package com.ardc.arkdust.items.blocks;

import com.ardc.arkdust.code_migration.pre.PreBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class Explaining_block extends PreBlock {
    public Explaining_block(){
        super(Properties.of(Material.HEAVY_METAL).strength(2,120).lightLevel((level)->11).harvestTool(ToolType.PICKAXE));
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

//    @Nullable
//    @Override
//    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
//        return new Explaining_blockTE();
//    }

}
