package com.ardc.arkdust.playmethod.blueprint;

import com.ardc.arkdust.enums.BlueprintTypeEnum;
import com.ardc.arkdust.enums.BlueprintValueEnum;
import com.ardc.arkdust.helper.EnumHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class CWC7BlueprintItem extends BlueprintItem{
    private final int weight;
    public CWC7BlueprintItem(boolean explain, int weight) {
        super(explain);
        this.weight = weight;
    }

    @Override
    public BlueprintType blueprintType() {
        return BlueprintType.SPECIAL;
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> list) {
        if (this.allowdedIn(group)) {
            list.add(addDefaultInfo(BlueprintTypeEnum.C7_TELEPORT,BlueprintValueEnum.SPECIAL,1,weight));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(new TranslationTextComponent("pma.bp.weight").withStyle(TextFormatting.WHITE).append(String.valueOf(itemstack.getOrCreateTagElement("blueprint").getInt("weight"))));
    }
}
