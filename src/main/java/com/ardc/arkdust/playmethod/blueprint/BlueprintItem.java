package com.ardc.arkdust.playmethod.blueprint;

import com.ardc.arkdust.enums.BlueprintTypeEnum;
import com.ardc.arkdust.enums.BlueprintValueEnum;
import com.ardc.arkdust.helper.EnumHelper;
import com.ardc.arkdust.preobject.pre.PreItem;
import com.ardc.arkdust.registry.ModGroupRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public abstract class BlueprintItem extends PreItem implements IBlueprintItem{
    public BlueprintItem(boolean explain) {
        super(new Properties().fireResistant().rarity(Rarity.RARE).tab(ModGroupRegistry.TERRA_BLUEPRINT), explain);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        CompoundNBT nbt = itemstack.getOrCreateTagElement("blueprint");
        list.add(new TranslationTextComponent("pma.bp.BP_"+nbt.getString("blueprint_type")).withStyle(TextFormatting.BLUE));
        list.add(new TranslationTextComponent("pma.bp.type").withStyle(TextFormatting.WHITE).append(nbt.getString("type")));
        String value = nbt.getString("value");
        list.add(new TranslationTextComponent("pma.bp.value").withStyle(TextFormatting.WHITE).append(value).withStyle(EnumHelper.valueOfOrDefault(BlueprintValueEnum.class,value,BlueprintValueEnum.COMMON).formatting));
//        list.add(new TranslationTextComponent("pma.bp.level").withStyle(TextFormatting.WHITE).append(nbt.getString("level")));
//        list.add(new TranslationTextComponent("pma.bp.weight").withStyle(TextFormatting.WHITE).append(nbt.getString("weight")));
    }

    public ItemStack addDefaultInfo(BlueprintTypeEnum type,BlueprintValueEnum value,int level,int weight){
        ItemStack stack = new ItemStack(this);
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("type",type.name());
        nbt.putString("value",value.name());
        nbt.putInt("level",level);
        nbt.putInt("weight",weight);
        nbt.putString("blueprint_type",this.blueprintType().name());
        stack.getOrCreateTag().put("blueprint",nbt);
        return stack;
    }
}
