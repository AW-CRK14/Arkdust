package com.ardc.arkdust.preobject;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class PreItem extends Item {
    public final boolean explain;
    public PreItem(Properties properties){
        super(properties);
        this.explain = false;
    }

    public PreItem(Properties properties,boolean exp){
        super(properties);
        this.explain = exp;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        if(explain) {
            if (!Screen.hasShiftDown()) {
                list.add(Component.translatable("explain.exp." + this.getDescriptionId()));
            } else {
                list.add(Component.translatable("basmes.way_to_get").withStyle(ChatFormatting.GOLD));
                list.add(Component.translatable("explain.get." + this.getDescriptionId()));
            }
        }
    }
}
