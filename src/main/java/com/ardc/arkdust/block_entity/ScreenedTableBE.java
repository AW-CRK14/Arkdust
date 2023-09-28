package com.ardc.arkdust.block_entity;

import com.ardc.arkdust.registry.TileEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ScreenedTableBE extends BlockEntity {
    public int guarantee = 0;
    public boolean hasItem = false;
    public int dropNumUpgrade = 0;
    //0对标原物品，1对标返回物品
    public NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    public ScreenedTableBE(BlockPos pos, BlockState state) {
        super(TileEntityTypeRegistry.SCREENED_TABLE_BE.get(),pos,state);
    }

    //尝试放入方块
    public boolean tryToPlaceIn(Item fromItem, Item toItem){
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

    public ItemStack getKeyItem(){
        return items.get(0);
    }

    public ItemStack getLootItem(){
        return items.get(1);
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
    public void load(CompoundTag nbt) {
        guarantee = nbt.getInt("guarantee");
        dropNumUpgrade = Math.max(Math.min(nbt.getInt("dropNumUpgrade"), 12), 0);
        hasItem = nbt.getBoolean("hasItem");
        ContainerHelper.loadAllItems(nbt,items);
//        System.out.println("block on load with nbt:" + nbt);
//        System.out.println("items:"+items);
//        System.out.println("of0:"+items.get(0));
//        System.out.println("of1:"+items.get(1));
        super.load(nbt);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("guarantee", guarantee);
        nbt.putInt("dropNumUpgrade", dropNumUpgrade);
        nbt.putBoolean("hasItem", hasItem);
        ContainerHelper.saveAllItems(nbt, items);
    }
}
