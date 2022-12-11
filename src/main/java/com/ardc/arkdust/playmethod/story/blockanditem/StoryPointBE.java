package com.ardc.arkdust.playmethod.story.blockanditem;

import com.ardc.arkdust.playmethod.story.StoryBag;
import com.ardc.arkdust.registry.TileEntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class StoryPointBE extends TileEntity {
    public String translateKey;
    public String title;
    public StoryBag bag;
    public int bagLevel;

    //0对标原物品，1对标返回物品
    public StoryPointBE() {
        super(TileEntityTypeRegistry.aStoryPointBE.get());
    }

    public void setKey(String key) {
        translateKey = key;
    }

    public void setTitle(String ti) {
        title = ti;
    }

    public void setBagAndLevel(String bag,int level) {
        this.bag = StoryBag.nameToBag(bag);
        this.bagLevel = level;
    }
    public void setBagAndLevel(StoryBag bag,int level) {
        this.bag = bag;
        this.bagLevel = level;
    }

    //用于检查方块nbt是不是空的
    public boolean isEmpty() {
        return (translateKey == null || translateKey.equals("null")) && bag == null;
    }
    public boolean isBagState(){
        return bag != null;
    }


    //NBT转换部分
    public CompoundNBT dataToNBT() {
        CompoundNBT nbt = new CompoundNBT();
        return dataToNBT(nbt);
    }
    public CompoundNBT dataToNBT(CompoundNBT nbt) {//创建nbt数据包
        nbt = createSaveData(nbt);
        return nbt;
    }
    public void load(BlockState state, CompoundNBT nbt) {
        parsingSaveData(nbt);
        super.load(state, nbt);
    }
    public CompoundNBT save(CompoundNBT nbt) {
        nbt = createSaveData(nbt);
        return super.save(nbt);
    }
    public boolean copyFromItemStack(ItemStack stack) {//物品栈转移nbt
        CompoundNBT nbt = stack.getTagElement("BlockEntityTag");
        parsingSaveData(nbt);
//        System.out.println("[key:" + translateKey + ",title:" + title + ",level" + bagLevel + "]");
        return true;
    }

    //数据再处理部分
    public CompoundNBT createSaveData(CompoundNBT nbt) {
        nbt.putString("transKey", translateKey == null || translateKey.equals("") ? "null" : translateKey);
        nbt.putString("title", title == null || title.equals("") ? "null" : title);
        if (bag != null) {
            nbt.putString("bag", bag.getName());
            nbt.putInt("level", bagLevel);
        }
        return nbt;
    }
    public void parsingSaveData(CompoundNBT nbt){
        if(nbt == null) return;
        translateKey = nbt.getString("transKey");
        title = nbt.getString("title");
        bag = StoryBag.nameToBag(nbt.getString("bag"));
        bagLevel = nbt.getInt("level");
    }


    //信息显示部分
    public TranslationTextComponent getTitleContext() {
        if (bag == null)
            return new TranslationTextComponent("story.trans.title.common." + (title == null ? "null" : title));
        return new TranslationTextComponent("story.trans.title.$" + bag.getName(), level);
    }

    public TranslationTextComponent getContext() {
        if (bag == null)
            return new TranslationTextComponent("story.trans.common." + (translateKey == null ? "null" : translateKey));
        return new TranslationTextComponent("story.trans.$" + bag.getName() + "#" + bagLevel);
    }
}
