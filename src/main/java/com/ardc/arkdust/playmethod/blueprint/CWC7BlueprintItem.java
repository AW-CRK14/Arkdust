package com.ardc.arkdust.playmethod.blueprint;

import com.ardc.arkdust.enums.BlueprintTypeEnum;
import com.ardc.arkdust.enums.BlueprintValueEnum;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class CWC7BlueprintItem extends BlueprintItem{
    public final int weight;
    public CWC7BlueprintItem(boolean explain, int weight) {
        super(explain);
        this.weight = weight;
    }

    @Override
    public BlueprintType blueprintType() {
        return BlueprintType.SPECIAL;
    }

    public ItemStack addDefaultInfo(){
        return super.addDefaultInfo(BlueprintTypeEnum.C7_TELEPORT, BlueprintValueEnum.SPECIAL,1,weight);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("pma.bp.BP_SPECIAL").withStyle(ChatFormatting.BLUE));
        list.add(Component.translatable("pma.bp.type").withStyle(ChatFormatting.WHITE).append("C7_TELEPORT"));
        list.add(Component.translatable("pma.bp.value").append("SPECIAL").withStyle(BlueprintValueEnum.SPECIAL.formatting));
        list.add(Component.translatable("pma.bp.weight").append(String.valueOf(weight)));
    }
}
