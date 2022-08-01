package com.ardc.arkdust.BlockEntity;

import com.ardc.arkdust.TileEntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public class HandScreenedTableBE extends TileEntity {
    public int guarantee = 0;
    public boolean hasItem = false;
    public int dropNumUpgrade = 0;
    //0对标原物品，1对标返回物品
    public NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    public HandScreenedTableBE() {
        super(TileEntityTypeRegistry.aSFBlockCreatorBE.get());
    }

    //尝试放入方块
    public boolean tryToPlaceIn(Item fromItem,Item toItem){
        if(hasItem){
            return false;
        }else{
            hasItem = true;
            items.set(0,new ItemStack(fromItem));
            items.set(1,new ItemStack(toItem));
            setChanged();
            return true;
        }
    }

    //获取掉落物
    public ItemStack getLoot(){
        if(hasItem){
            hasItem = false;//将拥有物品写为无
            //增加保底内容
            guarantee += 1;
            //获取掉落物的ItemStack
            ItemStack lootItem = items.get(1);
            lootItem.setCount((int)getLootNum());
            //重置物品数组
            items.set(0,ItemStack.EMPTY);
            items.set(1,ItemStack.EMPTY);
            setChanged();
            return lootItem;
        }else{
            return ItemStack.EMPTY;
        }
    }

    //获取掉落数量
    public double getLootNum(){
        int doubleDrop;
        if(Math.max(Math.min(guarantee * 0.025 + (double) dropNumUpgrade/20, 1),0) >= Math.random()){
            doubleDrop = 2;
            guarantee = 0;
            setChanged();
        }else{
            doubleDrop = 1;
        }
        return dropNumUpgrade * 0.4 + 0.2 + doubleDrop;
    }


    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        guarantee = nbt.getInt("guarantee");
        dropNumUpgrade = Math.max(Math.min(nbt.getInt("dropNumUpgrade"), 12), 0);
        hasItem = nbt.getBoolean("hasItem");
        ItemStackHelper.loadAllItems(nbt,items);
        super.load(state, nbt);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.putInt("guarantee", guarantee);
        nbt.putInt("dropNumUpgrade", dropNumUpgrade);
        nbt.putBoolean("hasItem", hasItem);
        ItemStackHelper.saveAllItems(nbt, items);
        return super.save(nbt);
    }
}
