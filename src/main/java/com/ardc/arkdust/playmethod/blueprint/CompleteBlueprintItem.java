package com.ardc.arkdust.playmethod.blueprint;

import com.ardc.arkdust.enums.BlueprintTypeEnum;
import com.ardc.arkdust.enums.BlueprintValueEnum;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class CompleteBlueprintItem extends BlueprintItem{
    public CompleteBlueprintItem(boolean explain, BlueprintValueEnum value, BlueprintTypeEnum type, int level, int weight) {
        super(explain);
    }

    public void setTargetId(ResourceLocation id){

    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
//        list.add(new TranslationTextComponent("pma.bp.level", level).withStyle(TextFormatting.BLUE));
    }

    @Override
    public BlueprintType blueprintType() {
        return BlueprintType.COMPLETE;
    }
}
