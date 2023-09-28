package com.ardc.arkdust.playmethod.blueprint;

import com.ardc.arkdust.enums.BlueprintTypeEnum;
import com.ardc.arkdust.enums.BlueprintValueEnum;
import com.ardc.arkdust.helper.EnumHelper;
import com.ardc.arkdust.preobject.PreItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public abstract class BlueprintItem extends PreItem implements IBlueprintItem{
    public BlueprintItem(boolean explain) {
        super(new Properties().fireResistant().rarity(Rarity.RARE), explain);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        CompoundTag nbt = itemstack.getOrCreateTagElement("blueprint");
        list.add(Component.translatable("pma.bp.BP_"+nbt.getString("blueprint_type")).withStyle(ChatFormatting.BLUE));
        list.add(Component.translatable("pma.bp.type").withStyle(ChatFormatting.WHITE).append(nbt.getString("type")));
        String value = nbt.getString("value");
        list.add(Component.translatable("pma.bp.value").withStyle(ChatFormatting.WHITE).append(value).withStyle(EnumHelper.valueOfOrDefault(BlueprintValueEnum.class,value,BlueprintValueEnum.COMMON).formatting));
//        list.add(new TranslationTextComponent("pma.bp.level").withStyle(TextFormatting.WHITE).append(nbt.getString("level")));
//        list.add(new TranslationTextComponent("pma.bp.weight").withStyle(TextFormatting.WHITE).append(nbt.getString("weight")));
    }

    public ItemStack addDefaultInfo(BlueprintTypeEnum type,BlueprintValueEnum value,int level,int weight){
        ItemStack stack = new ItemStack(this);
        CompoundTag nbt = new CompoundTag();
        nbt.putString("type",type.name());
        nbt.putString("value",value.name());
        nbt.putInt("level",level);
        nbt.putInt("weight",weight);
        nbt.putString("blueprint_type",this.blueprintType().name());
        stack.getOrCreateTag().put("blueprint",nbt);
        return stack;
    }
}
