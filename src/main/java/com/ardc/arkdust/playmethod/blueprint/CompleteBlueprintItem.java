package com.ardc.arkdust.playmethod.blueprint;

import com.ardc.arkdust.enums.BlueprintTypeEnum;
import com.ardc.arkdust.enums.BlueprintValueEnum;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
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
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
//        list.add(new TranslationTextComponent("pma.bp.level", level).withStyle(TextFormatting.BLUE));
    }

    @Override
    public BlueprintType blueprintType() {
        return BlueprintType.COMPLETE;
    }
}
