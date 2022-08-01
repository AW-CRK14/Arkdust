package com.ardc.arkdust.Items.blocks.ores;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;


public class Pau_ore extends Block{
    public Pau_ore(){
        super(Properties.of(Material.STONE).harvestTool(ToolType.PICKAXE).strength(8).harvestLevel(2).requiresCorrectToolForDrops());
        //strength是强度，等价于非官方版中的hardnessAndResistance
    }

}
