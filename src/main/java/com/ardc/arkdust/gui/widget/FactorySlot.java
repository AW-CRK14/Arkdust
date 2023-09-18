package com.ardc.arkdust.gui.widget;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.block_entity.SeRcuMachineBE;
import com.ardc.arkdust.helper.RenderHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class FactorySlot extends Slot {
    public static final ResourceLocation background_circle = new ResourceLocation(Utils.MOD_ID,"textures/gui/abs_factory/widget/background_c.png");
    public static final ResourceLocation background_empty = new ResourceLocation(Utils.MOD_ID,"textures/gui/abs_factory/widget/background_empty.png");
    public FactorySlot(IInventory inventory, int slot, int x, int y) {
        super(inventory, slot, x, y);
    }

    public void renderBackground(MatrixStack stack,int renX,int renY,ItemStack itemStack){
        renderBackground(stack,renX,renY,1,itemStack);
    }
    public void renderBackground(MatrixStack stack, int renX, int renY, float alpha, ItemStack itemStack){
        RenderSystem.enableBlend();
        Color color = RenderHelper.itemStackColor(itemStack);
        RenderSystem.color4f(color.getRed()/255F,color.getGreen()/255F,color.getBlue()/255F,alpha*0.4F);
        Minecraft.getInstance().getTextureManager().bind(background_circle);
        Screen.blit(stack,renX-2,renY-2,0,0,20,20,20,20);
        RenderSystem.disableBlend();
        RenderSystem.color4f(1F,1F,1F,1F);
    }


    public void renderWhenEmpty(MatrixStack stack,int renX,int renY,float alpha){
        RenderSystem.enableBlend();
        RenderSystem.color4f(1,1,1,alpha*0.4F);
        Minecraft.getInstance().getTextureManager().bind(background_empty);
        Screen.blit(stack,renX-2,renY-2,0,0,20,32,20,32);
        RenderSystem.disableBlend();
        RenderSystem.color4f(1F,1F,1F,1F);
    }

    public void renderWhenOn(MatrixStack stack,int renX,int renY,float alpha, ItemStack itemStack){
        renderBackground(stack,renX,renY,alpha*1.5F,itemStack);
    }

    public void renderWhenDrag(MatrixStack stack,int renX,int renY,float alpha, ItemStack itemStack){
        renderBackground(stack,renX,renY,alpha*1.5F,itemStack);
    }
}
