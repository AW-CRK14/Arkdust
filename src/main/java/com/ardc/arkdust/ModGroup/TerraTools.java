package com.ardc.arkdust.ModGroup;

import com.ardc.arkdust.registry.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class TerraTools extends ItemGroup {
    public TerraTools(){
        super("terra_tools");
    }
    @Override
    public ItemStack makeIcon(){
        return new ItemStack(ItemRegistry.pau_sword.get());
    }//makeIcon是官方写法，和教程中不同。教程中的不可用
}
