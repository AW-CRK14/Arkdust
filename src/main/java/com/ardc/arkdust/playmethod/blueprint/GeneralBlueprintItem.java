package com.ardc.arkdust.playmethod.blueprint;

import com.ardc.arkdust.enums.BlueprintTypeEnum;
import com.ardc.arkdust.enums.BlueprintValueEnum;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GeneralBlueprintItem extends BlueprintItem{
    public GeneralBlueprintItem() {
        super(false);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(new TranslationTextComponent("pma.bp.BP_GENERAL.explain"));
    }

    @Override
    public BlueprintType blueprintType() {
        return BlueprintType.GENERAL;
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> list) {
        if (group == ItemGroup.TAB_SEARCH){
            list.add(addDefaultInfo(BlueprintTypeEnum.OBJ,BlueprintValueEnum.COMMON,0,1));
        }else if (this.allowdedIn(group)) {
            for(BlueprintTypeEnum type : Arrays.stream(BlueprintTypeEnum.values()).filter((i)->!i.equals(BlueprintTypeEnum.NULL)).collect(Collectors.toList())){
                list.add(addDefaultInfo(type,type.maxValue,0,1));
            }
        }
    }

    public ItemStack addDefaultInfo(BlueprintTypeEnum type,BlueprintValueEnum value){
        return super.addDefaultInfo(type,value,0,1);
    }
}
