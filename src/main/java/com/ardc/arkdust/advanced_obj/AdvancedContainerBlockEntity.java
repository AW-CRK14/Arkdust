package com.ardc.arkdust.advanced_obj;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.enums.SlotType;
import com.ardc.arkdust.helper.ListAndMapHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AdvancedContainerBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer{
    public static final Logger LOGGER = LogManager.getLogger(Utils.getLogName("BlockEntity/Container"));
    public final Map<SlotType,Integer> slot_map;
    public final int size;
    public final NonNullList<ItemStack> items;
    public AdvancedContainerBlockEntity(BlockEntityType<?> type , BlockPos pos, BlockState state, Map<SlotType,Integer> slot_map) {
        super(type,pos,state);
        this.slot_map = slot_map;
        int i = 0;
        for(int n : slot_map.values()){
            i+=n;
        }
        this.size = i;
        this.items = NonNullList.withSize(size,new ItemStack(Items.AIR));
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        ListTag listtag = new ListTag();
        items.forEach(itemStack -> listtag.add(itemStack.save(new CompoundTag())));
        nbt.put("items",listtag);

        LOGGER.info("Data saved!{block_entity:{},pos:{}({}),nbt:{}",this,this.worldPosition,new ChunkPos(this.worldPosition),nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        ListTag tag = nbt.getList("items",10);
        if(!tag.isEmpty()){
            for(int i = 0; i < tag.size(); ++i) {
                try {
                    setItem(i, ItemStack.of(tag.getCompound(i)));
                } catch (ArrayIndexOutOfBoundsException ignored) {}
            }
        }

        LOGGER.info("Data loaded!{block_entity:{},pos:{}({}),nbt:{}",this,this.worldPosition,new ChunkPos(this.worldPosition),nbt);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public void setItem(int index, ItemStack itemStack) {
        this.items.set(index, itemStack);
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty() ;
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return ContainerHelper.removeItem(this.items,index,count);
    }

    @Override
    public ItemStack getItem(int index) {
        return items.get(index);
    }

    public ItemStack getItem(SlotType type,int index) {
        if (!slot_map.containsKey(type)) {
            LOGGER.error("Can't get item as type:{} not exist", type);
            return ItemStack.EMPTY;
        } else if (index >= slot_map.get(type)){
            LOGGER.error("Can't get item as can't get item stack for index:{}.Actually type:{} has {} slot", index,type,slot_map.get(type));
            return ItemStack.EMPTY;
        }
        return items.get(indexHead(type)+index);
    }

    public int indexHead(SlotType type){
        int i = ListAndMapHelper.getIndexFromMap(slot_map, type);
        int k = 0;
        List<Integer> list = slot_map.values().stream().toList();
        for (int j = 0; j < i; j++) {
            k += list.get(j);
        }
        return k;
    }

    public List<ItemStack> getStacksForType(SlotType type){
        if(!slot_map.containsKey(type)) return List.of();
        List<ItemStack> list = new ArrayList<>();
        int h = indexHead(type);
        return list.subList(h,h+slot_map.get(type)-1);
    }

    @Override
    public boolean stillValid(Player p_18946_) {
        return true;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack stack = this.items.get(index).copy();
        if(stack.isEmpty()){
            return ItemStack.EMPTY;
        }else {
            this.items.set(index, ItemStack.EMPTY);
            return stack;
        }
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean canPlaceItemThroughFace(int p_19235_, ItemStack p_19236_, @Nullable Direction p_19237_) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int p_19239_, ItemStack p_19240_, Direction p_19241_) {
        return false;
    }

    @Override
    public int[] getSlotsForFace(Direction p_19238_) {
        return new int[0];
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int winId, Inventory playerInventory, Player player) {
        return this.canOpen(player) ? menu(winId, playerInventory, player) : null;
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return menu(id, inventory, Minecraft.getInstance().player);
    }

    public abstract AbstractContainerMenu menu(int winId, Inventory playerInventory, Player player);


}
