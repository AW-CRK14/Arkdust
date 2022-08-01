package com.ardc.arkdust.ModGroup;

import com.ardc.arkdust.ItemRegistry;
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
    //makeIcon是官混版写法，和教程中不同。教程中的不可用
}
