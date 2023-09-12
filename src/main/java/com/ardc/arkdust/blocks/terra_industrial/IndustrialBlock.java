package com.ardc.arkdust.blocks.terra_industrial;

import com.ardc.arkdust.preobject.PreBlock;
import com.ardc.arkdust.enums.TechMaterial;

public class IndustrialBlock extends PreBlock {
    public final TechMaterial material;
    public final float hardnessAdd;
    public IndustrialBlock(Properties properties, TechMaterial materialInput) {
        super(properties);
        material = materialInput;
        hardnessAdd = 0;
    }
    public IndustrialBlock(Properties properties, TechMaterial materialInput, float hardnessAddInput) {
        super(properties);
        material = materialInput;
        hardnessAdd = hardnessAddInput;
    }

    public float getTrulyHardness(){
        return material.getMaterialHardness() + hardnessAdd;
    }
}
