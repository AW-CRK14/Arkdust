package com.ardc.arkdust.CodeMigration.pre;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PreBlockItem extends BlockItem {

    public PreBlockItem(Block block, Item.Properties properties, boolean exp){
        super(block,properties);

    }

    public PreBlockItem(Block block, Item.Properties properties){
        super(block,properties);

    }
}
