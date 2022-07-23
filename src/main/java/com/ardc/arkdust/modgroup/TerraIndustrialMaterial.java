package com.ardc.arkdust.modgroup;

import com.ardc.arkdust.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class TerraIndustrialMaterial extends ItemGroup {
    public TerraIndustrialMaterial(){
        super("terra_industrial_material");
    }
    @Override
    public ItemStack makeIcon(){
        return new ItemStack(ItemRegistry.iron_structure_frame.get());
    }
}