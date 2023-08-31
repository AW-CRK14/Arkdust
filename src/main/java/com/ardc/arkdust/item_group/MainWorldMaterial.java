package com.ardc.arkdust.item_group;

import com.ardc.arkdust.registry.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class MainWorldMaterial extends ItemGroup {
    public MainWorldMaterial(){
        super("main_world_material");
    }
    @Override
    public ItemStack makeIcon(){
        return new ItemStack(ItemRegistry.dt_crystal.get());
    }
}
