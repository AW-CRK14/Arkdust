package com.ardc.arkdust.playmethod.story.blockanditem;

import com.ardc.arkdust.playmethod.story.Story;
import com.ardc.arkdust.registry.StoryRegistry;
import com.ardc.arkdust.registry.TileEntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class StoryPointBE extends TileEntity {
    public String translateKey;
    public String title;
    public Story bag;
    public int stage;
    public boolean bagMode;
    //title��translateKey�����Ϊ��ʱ�����ʹ��
    //bag��stage�����Ϊ����ϵͳ�ռ�ʹ��


    public StoryPointBE() {
        super(TileEntityTypeRegistry.STORY_POINT_BE.get());
    }

    public void setTitleAndKey(String title, String key) {
        bagMode = false;
        this.translateKey = key;
        this.title = title;
    }
    public void setBagAndLevel(String bag,int level) {
        bagMode = true;
        this.bag = Story.nameToBag(bag);
        this.stage = level;
    }
    public void setBagAndLevel(Story bag, int level) {
        bagMode = true;
        this.bag = bag;
        this.stage = level;
    }

    //NBTת������
    public void load(BlockState state, CompoundNBT nbt) {
        parseSaveData(nbt);
        super.load(state, nbt);
    }
    public CompoundNBT save(CompoundNBT nbt) {
        return super.save(createSaveData(nbt));
    }
    public boolean copyFromItemStack(ItemStack stack) {//��Ʒջת��nbt
        CompoundNBT nbt = stack.getTagElement("BlockEntityTag");
        parseSaveData(nbt);
        return true;
    }

    //�����ٴ�����
    private void initialize(){
        if(bag == null) bag = StoryRegistry.DEFAULT;
        if(translateKey == null || translateKey.equals("")) translateKey = "null";
        if(title == null || title.equals("")) title = "null";
    }
    public CompoundNBT createSaveData(CompoundNBT nbt) {
        initialize();
        nbt.putBoolean("bagMode",bagMode);
        nbt.putString("transKey", translateKey);
        nbt.putString("title", title);
        nbt.putString("bag", bag.getName().toString());
        nbt.putInt("level", stage);
        return nbt;
    }
    public void parseSaveData(CompoundNBT nbt){
        if(nbt == null) return;
        bagMode = nbt.getBoolean("bagMode");
        translateKey = nbt.getString("transKey");
        title = nbt.getString("title");
        bag = Story.nameToBag(nbt.getString("bag"));
        stage = nbt.getInt("level");
        initialize();
    }


    //��Ϣ��ʾ����
    public TranslationTextComponent getTitleContext() {
        initialize();
        if (!bagMode)
            return new TranslationTextComponent("story.trans.title.common." + title);
        return new TranslationTextComponent("story.trans.title.$" + bag.getName());
    }

    public TranslationTextComponent getContext() {
        initialize();
        if (bag == null)
            return new TranslationTextComponent("story.trans.common." + translateKey);
        return new TranslationTextComponent("story.trans.$" + bag.getName() + "#" + stage);
    }
}
