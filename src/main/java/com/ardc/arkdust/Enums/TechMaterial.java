package com.ardc.arkdust.Enums;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public enum TechMaterial {
    C_IRON(1,1.5F,75,-15,0.3F, Items.IRON_INGOT);

    private final int materialLevel;
    private final float hardness;
    private final Item matIngot;
    private final float tempResistanceMax;//温度抗性
    private final float tempResistanceMin;
    private final float humiResistance;//湿度抗性

    TechMaterial(int materialLevel,float materialHardness,float tempResistanceMax,float tempResistanceMin,float humiResistance,Item ingot){
        this.materialLevel = materialLevel;
        this.hardness = materialHardness;
        this.matIngot = ingot;
        this.tempResistanceMax = tempResistanceMax;
        this.tempResistanceMin = tempResistanceMin;
        this.humiResistance = humiResistance;
    }

    public float getMaterialHardness(){return this.hardness;}

    public Item getItem(){return this.matIngot;}
}
