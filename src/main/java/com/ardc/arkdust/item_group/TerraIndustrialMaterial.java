package com.ardc.arkdust.item_group;

import com.ardc.arkdust.registry.ItemRegistry;
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