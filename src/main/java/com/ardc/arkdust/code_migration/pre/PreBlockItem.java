package com.ardc.arkdust.code_migration.pre;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class PreBlockItem extends BlockItem {
    public final String explain;
    public PreBlockItem(Block block, Item.Properties properties){
        super(block,properties);
        this.explain = "block." + this.getRegistryName();
    }

    public PreBlockItem(Block block, Item.Properties properties, String explain){
        super(block,properties);
        this.explain = explain;
    }

    public String getExplain(){
        return this.explain;
    }
}
