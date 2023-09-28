package com.ardc.arkdust.playmethod.blueprint;

import com.ardc.arkdust.enums.BlueprintTypeEnum;
import com.ardc.arkdust.enums.BlueprintValueEnum;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class GeneralBlueprintItem extends BlueprintItem{
    public GeneralBlueprintItem() {
        super(false);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("pma.bp.BP_GENERAL.explain"));
    }

    @Override
    public BlueprintType blueprintType() {
        return BlueprintType.GENERAL;
    }

//    @Override
//    public void fillItemCategory(Group group, NonNullList<ItemStack> list) {//TODO
//        if (group == ItemGroup.TAB_SEARCH){
//            list.add(addDefaultInfo(BlueprintTypeEnum.OBJ,BlueprintValueEnum.COMMON,0,1));
//        }else if (this.allowdedIn(group)) {
//            for(BlueprintTypeEnum type : Arrays.stream(BlueprintTypeEnum.values()).filter((i)->!i.equals(BlueprintTypeEnum.NULL)).collect(Collectors.toList())){
//                list.add(addDefaultInfo(type,type.maxValue,0,1));
//            }
//        }
//    }

    public ItemStack addDefaultInfo(BlueprintTypeEnum type,BlueprintValueEnum value){
        return super.addDefaultInfo(type,value,0,1);
    }
}
