package com.ardc.arkdust.playmethod.story.blockanditem;

import com.ardc.arkdust.playmethod.story.Story;
import com.ardc.arkdust.registry.StoryRegistry;
import com.ardc.arkdust.registry.TileEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class StoryPointBE extends BlockEntity {
    public String translateKey = "null";
    public String title = "null";
    public Story bag = StoryRegistry.DEFAULT;
    public int stage;
    //title与translateKey组合作为临时剧情点使用
    //bag与stage组合作为剧情系统收集使用


    public StoryPointBE(BlockPos pos, BlockState state) {
        super(TileEntityTypeRegistry.STORY_POINT_BE.get(),pos,state);
        setChanged();
    }

    public void setTitleAndKey(String title, String key) {
        this.translateKey = key;
        this.title = title;
    }
    public void setBagAndLevel(String bag,int level) {
        this.bag = Story.nameToBag(bag);
        this.stage = level;
    }
    public void setBagAndLevel(Story bag, int level) {
        this.bag = bag;
        this.stage = level;
    }

    //NBT转换部分
    public void load(CompoundTag nbt) {
        translateKey = nbt.getString("transKey");
        title = nbt.getString("title");
        bag = Story.nameToBag(nbt.getString("bag"));
        stage = nbt.getInt("level");
        super.load(nbt);
    }
    public void saveAdditional(CompoundTag nbt) {
        nbt.putString("transKey", translateKey);
        nbt.putString("title", title);
        nbt.putString("bag", bag.getName().toString());
        nbt.putInt("level", stage);
    }

    public boolean copyFromItemStack(ItemStack stack) {//物品栈转移nbt
        CompoundTag nbt = stack.getTagElement("BlockEntityTag");
        load(nbt);
        return true;
    }

    public boolean checkBagMode(){
        return !bag.equals(StoryRegistry.DEFAULT) && stage <= bag.length && stage>= 0;
    }

    //信息显示部分
    public Component getTitleContext(int stage) {
        if (checkBagMode())
            return bag.createMessage(stage,false);
        return Component.translatable("story.trans.title.common." + title);
    }

    public Component getContext() {
        if (checkBagMode())
            return Component.translatable("story.trans.$" + bag.getName() + "#" + stage);
        return Component.translatable("story.trans.common." + translateKey);
    }
}
