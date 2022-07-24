package com.ardc.arkdust.type;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public enum TechMaterial {
    C_IRON(1, Items.IRON_INGOT);

    private final float hardness;
    private final Item matIngot;

    TechMaterial(float materialHardness, Item Ingot){
        this.hardness = materialHardness;
        this.matIngot = Ingot;
    };

    public float getMaterialHardness(){return this.hardness;}

    public Item getItem(){return this.matIngot;}
}
