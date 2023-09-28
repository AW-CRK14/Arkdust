package com.ardc.arkdust.gui.widget;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.helper.RenderHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class FactorySlot extends Slot {
    public static final ResourceLocation background_circle = new ResourceLocation(Utils.MOD_ID,"textures/gui/abs_factory/widget/background_c.png");
    public static final ResourceLocation background_empty = new ResourceLocation(Utils.MOD_ID,"textures/gui/abs_factory/widget/background_empty.png");
    public FactorySlot(Container inventory, int slot, int x, int y) {
        super(inventory, slot, x, y);
    }

    public void renderBackground(GuiGraphics stack, int renX, int renY, ItemStack itemStack){
        renderBackground(stack,renX,renY,1,itemStack);
    }
    public void renderBackground(GuiGraphics stack, int renX, int renY, float alpha, ItemStack itemStack){
        RenderSystem.enableBlend();
        Color color = RenderHelper.itemStackColor(itemStack);
        stack.setColor(color.getRed()/255F,color.getGreen()/255F,color.getBlue()/255F,alpha*0.4F);
        stack.blit(background_circle,renX-2,renY-2,0,0,20,20,20,20);
        RenderSystem.disableBlend();
        stack.setColor(1F,1F,1F,1F);
    }


    public void renderWhenEmpty(GuiGraphics stack,int renX,int renY,float alpha){
        RenderSystem.enableBlend();
        stack.setColor(1,1,1,alpha*0.4F);
        stack.blit(background_empty,renX-2,renY-2,0,0,20,32,20,32);
        RenderSystem.disableBlend();
        stack.setColor(1F,1F,1F,1F);
    }

    public void renderWhenOn(GuiGraphics stack,int renX,int renY,float alpha, ItemStack itemStack){
        renderBackground(stack,renX,renY,alpha*1.5F,itemStack);
    }

    public void renderWhenDrag(GuiGraphics stack,int renX,int renY,float alpha, ItemStack itemStack){
        renderBackground(stack,renX,renY,alpha*1.5F,itemStack);
    }
}
