package com.ardc.arkdust.preobject.pre;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class PreBlockItem extends BlockItem {

    public PreBlockItem(Block block, Item.Properties properties, boolean exp){
        super(block,properties);

    }

    public PreBlockItem(Block block, Item.Properties properties){
        super(block,properties);

    }
}
