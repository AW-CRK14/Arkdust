package com.ardc.arkdust.NewPlayingMethod.story.blockanditem;

import com.ardc.arkdust.NewPlayingMethod.story.StoryBag;
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
    public int bagLevel = -1;//TODO 存储型剧情系统
    //0对标原物品，1对标返回物品
    public StoryPointBE() {
        super(TileEntityTypeRegistry.aStoryPointBE.get());
    }

    public void setKey(String key){
        translateKey = key;
    }

    public void setTitle(String ti){
        title = ti;
    }

    //用于检查方块nbt是不是空的
    public boolean isEmpty(){
        return (translateKey == null || translateKey.equals("null")) && bag == null;
    }

    //测试用：用于将物品栈的nbt复制到此方块实体
    public boolean copyFromItemStack(ItemStack stack){
        CompoundNBT nbt = stack.getTagElement("BlockEntityTag");
        if(nbt == null) return false;
        translateKey = nbt.getString("transKey");
        title = nbt.getString("title");
        bagLevel = nbt.getInt("level");
        System.out.println("[key:" + translateKey + ",title:" + title + ",level" + bagLevel + "]");
        return true;
    }

    //用于转换为物品保存
    public CompoundNBT dataToNBT(){
        CompoundNBT nbt = new CompoundNBT();
        return dataToNBT(nbt);
    }
    public CompoundNBT dataToNBT(CompoundNBT nbt){
        nbt.putString("transKey",translateKey == null ? "null" : translateKey);
        nbt.putString("title",title == null ? "null" : title);
        nbt.putString("bag","unopened");//TODO bag项存储
        nbt.putInt("level",bagLevel);
        return nbt;
    }

    public TranslationTextComponent getTitleContext(){
        if(bag == null)
            return new TranslationTextComponent("story.trans.title.common." + (title==null ? "null" : title));
        return new TranslationTextComponent("story.trans.title.$" + bag.name(),level);
    }

    public TranslationTextComponent getContext(){
        if(bag == null)
            return new TranslationTextComponent("story.trans.common." + (translateKey==null ? "null" : translateKey));
        return new TranslationTextComponent("story.trans.$" + bag.name() + "#" + bagLevel);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        translateKey = nbt.getString("key");
        title = nbt.getString("title");
        //TODO 存储型剧情系统
        super.load(state, nbt);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.putString("key",translateKey==null?"null":translateKey);
        nbt.putString("title",title==null?"null":title);
        //TODO 存储型剧情系统
        return super.save(nbt);
    }
}
