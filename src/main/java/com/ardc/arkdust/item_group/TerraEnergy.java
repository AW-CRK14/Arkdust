package com.ardc.arkdust.item_group;

import com.ardc.arkdust.registry.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class TerraEnergy extends ItemGroup {
    public TerraEnergy(){
        super("terra_energy");
    }
    @Override
    public ItemStack makeIcon(){
        return new ItemStack(ItemRegistry.e_oir_reactor_control_board.get());
    }
}