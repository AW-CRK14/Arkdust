package com.ardc.arkdust.item_group;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class GroupGenerator extends ItemGroup {
    private Supplier<ItemStack> stackSupplier;
    public GroupGenerator(String name, Supplier<ItemStack> stack){
        super(name);
        this.stackSupplier = stack;
    }

    @Override
    public ItemStack makeIcon() {
        return stackSupplier.get();
    }
}
