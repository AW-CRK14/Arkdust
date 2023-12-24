package com.ardc.arkdust.advanced_obj;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;
@Deprecated
public class AASupplierItemStack {
    public Supplier<Item> item;
    public int num;
    public ItemStackExtraOperation operation;
    public interface ItemStackExtraOperation {
        void operation(ItemStack stack);
        ItemStackExtraOperation empty = (i)->{};
    }

    public AASupplierItemStack(Supplier<Item> item){
        this.item = item;
        this.num = 1;
        this.operation = ItemStackExtraOperation.empty;
    }

    public AASupplierItemStack(Supplier<Item> item,int num){
        this.item = item;
        this.num = num;
        this.operation = ItemStackExtraOperation.empty;
    }

    public AASupplierItemStack(Supplier<Item> item,int num,ItemStackExtraOperation operation){
        this.item = item;
        this.num = num;
        this.operation = operation;
    }

    public ItemStack create(){
        ItemStack stack = new ItemStack(item.get(),num);
        operation.operation(stack);
        return stack;
    }
}
