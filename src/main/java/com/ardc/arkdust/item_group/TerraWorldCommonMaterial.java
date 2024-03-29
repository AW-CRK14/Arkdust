package com.ardc.arkdust.item_group;

import com.ardc.arkdust.registry.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class TerraWorldCommonMaterial extends ItemGroup {
    public TerraWorldCommonMaterial(){
        super("terra_world_common_material");
    }

    @Override
    public ItemStack makeIcon(){
        return new ItemStack(ItemRegistry.originium_dust.get());
    }
}