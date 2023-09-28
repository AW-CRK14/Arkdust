package com.ardc.arkdust.preobject;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class PreBlockItem extends BlockItem {

    public PreBlockItem(Block block, Item.Properties properties, boolean exp){
        super(block,properties);

    }

    public PreBlockItem(Block block, Item.Properties properties){
        super(block,properties);

    }
}
