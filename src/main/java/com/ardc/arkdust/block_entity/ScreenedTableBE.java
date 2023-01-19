package com.ardc.arkdust.block_entity;

import com.ardc.arkdust.registry.TileEntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public class ScreenedTableBE extends TileEntity {
    public int guarantee = 0;
    public boolean hasItem = false;
    public int dropNumUpgrade = 0;
    //0�Ա�ԭ��Ʒ��1�Ա귵����Ʒ
    public NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    public ScreenedTableBE() {
        super(TileEntityTypeRegistry.aScreenedTableBE.get());
    }

    //���Է��뷽��
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

    //��ȡ������
    public ItemStack getLoot(){
        if(hasItem){
            hasItem = false;//��ӵ����ƷдΪ��
            //���ӱ�������
            guarantee += 1;
            //��ȡ�������ItemStack
            ItemStack lootItem = items.get(1);
            lootItem.setCount((int)getLootNum());
            //������Ʒ����
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

    //��ȡ��������
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
//        System.out.println("block on load with nbt:" + nbt);
//        System.out.println("items:"+items);
//        System.out.println("of0:"+items.get(0));
//        System.out.println("of1:"+items.get(1));
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