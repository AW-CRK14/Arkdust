package com.ardc.arkdust.gui.widget;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.advanced_obj.AdvancedContainerBlockEntity;
import com.ardc.arkdust.gui.ArdFactorBlockScreen;
import com.ardc.arkdust.helper.RenderHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.awt.*;

public class RenderOverrideSlot extends Slot {
    public static final ResourceLocation background_circle = new ResourceLocation(Utils.MOD_ID,"textures/gui/abs_factory/widget/background_c.png");
    public static final ResourceLocation background_empty = new ResourceLocation(Utils.MOD_ID,"textures/gui/abs_factory/widget/background_empty.png");

    public RenderOverrideSlot(Container inventory, int slot, int x, int y) {
        super(inventory, slot, x, y);
    }

    public BlockEntity getBlockEntity() {
        return container instanceof AdvancedContainerBlockEntity be ? be : null;
    }

    @Override
    public void setChanged() {
        if(getBlockEntity() != null) getBlockEntity().setChanged();
        super.setChanged();
    }

    public void renderBackground(GuiGraphics stack, int renX, int renY, float alpha, ItemStack itemStack){
//        stack.pose().pushPose();
//        RenderSystem.enableBlend();
//        RenderSystem.disableDepthTest();
//        Color color = RenderHelper.itemStackColor(itemStack);
//        stack.setColor(color.getRed()/255F,color.getGreen()/255F,color.getBlue()/255F,alpha*0.4F);
//        stack.blit(background_circle,renX-2,renY-2,0,0,20,20,20,20);
//        RenderSystem.disableBlend();
//        RenderSystem.enableDepthTest();
//        stack.setColor(1F,1F,1F,1F);
//        stack.pose().popPose();
    }


    public void renderWhenEmpty(GuiGraphics stack,int renX,int renY,float alpha){
//        RenderSystem.enableBlend();
//        stack.setColor(1,1,1,alpha*0.4F);
//        stack.blit(background_empty,renX-2,renY-2,0,0,20,32,20,32);
//        RenderSystem.disableBlend();
//        stack.setColor(1F,1F,1F,1F);
    }

    public void renderWhenOn(GuiGraphics stack,int renX,int renY,float alpha, ItemStack itemStack,int color,int mouseX, int mouseY){
        if(isHighlightable()){
            AbstractContainerScreen.renderSlotHighlight(stack,renX,renY, 0,color);
        }
//        renderBackground(stack,renX,renY,alpha,itemStack);
    }

    public void renderWhenDrag(GuiGraphics stack,int renX,int renY,float alpha, ItemStack itemStack){
        renderBackground(stack,renX,renY,alpha*1.5F,itemStack);
    }

    public void renderWhenNotEmpty(GuiGraphics stack, int renX, int renY,int mouseX, int mouseY, ItemStack itemStack, Font font,String defaultText, float alpha){
        stack.setColor(1,1,1,alpha);
        renderItemStack(stack,renX, renY,itemStack,alpha);
        renderItemStackDecoration(stack, renX, renY,itemStack,font,defaultText,alpha);
    }

    public void renderItemStack(GuiGraphics stack,int renX,int renY, ItemStack itemStack, float alpha){
        stack.renderItem(itemStack,renX,renY);
    }

    public void renderItemStackDecoration(GuiGraphics stack, int renX, int renY, ItemStack itemStack, Font font,String defaultText, float alpha){
        stack.renderItemDecorations(font, itemStack, renX, renY, defaultText);
    }

    public static class ScaledSlot extends RenderOverrideSlot {
        public final float scale;
        public final float countScale;
        public ScaledSlot(Inventory inventory, int slot, int x, int y, float scale, float countScale) {
            super(inventory, slot, x, y);
            this.scale = scale;
            this.countScale = countScale;
        }

        public void renderItemStack(GuiGraphics stack,int renX,int renY, ItemStack itemStack, float alpha){
            RenderHelper.renderItem(stack,itemStack,renX,renY,scale);
        }

        public void renderItemStackDecoration(GuiGraphics stack, int renX, int renY, ItemStack itemStack, Font font,String defaultText, float alpha){
            RenderHelper.renderItemDecorations(font,stack,itemStack,renX,renY,defaultText,countScale);
        }
    }

    public static class EmpIndSlot extends RenderOverrideSlot{
        public static final ResourceLocation EMPTY = new ResourceLocation(Utils.MOD_ID,"textures/gui/aefi/widget/add.png");
        public static final ResourceLocation SLOT = new ResourceLocation(Utils.MOD_ID,"textures/gui/aefi/widget/slot.png");
        public static final ResourceLocation SLOT_BELOW = new ResourceLocation(Utils.MOD_ID,"textures/gui/aefi/widget/slot_below.png");

        public EmpIndSlot(AdvancedContainerBlockEntity inventory, int slot, int x, int y) {
            super(inventory, slot, x, y);
        }

        @Override
        public void renderWhenEmpty(GuiGraphics stack, int renX, int renY, float alpha) {
            stack.setColor(1,1,1,alpha/2);
            stack.blit(EMPTY,renX+6,renY+6,0,0,4,4,4,4);
//            stack.setColor(1,1,1,1);
        }

        @Override
        public void renderWhenOn(GuiGraphics stack, int renX, int renY, float alpha, ItemStack itemStack, int color,int mouseX, int mouseY) {
            if(itemStack.isEmpty())
                renderWhenEmpty(stack,renX, renY,2 * alpha);
            else
                renderWhenNotEmpty(stack,renX,renY,mouseX,mouseY,itemStack, ArdFactorBlockScreen.MSYH,null,2 * alpha);
        }

        public void renderWhenNotEmpty(GuiGraphics stack, int renX, int renY,int mouseX, int mouseY, ItemStack itemStack, Font font,String defaultText, float alpha){
            stack.setColor(1,1,1,1);
            renderItemStack(stack,renX, renY,itemStack,alpha);
            stack.setColor(1,1,1,Math.min(alpha*0.85F,1));
            RenderSystem.enableBlend();
            stack.pose().pushPose();
            stack.pose().translate(renX,renY,0);
            stack.pose().scale(0.8F,0.8F,1);
            stack.blit(SLOT,0,0,0,0,20,19,20,20);
            renderItemStackDecoration(stack, renX, renY,itemStack,font,defaultText,alpha/2);
            stack.pose().popPose();
        }

        public void renderItemStackDecoration(GuiGraphics stack, int renX, int renY, ItemStack itemStack, Font font,String defaultText, float alpha){
            RenderHelper.renderEmpIndItemDecorations(ArdFactorBlockScreen.MSYH,stack,itemStack,renX,renY,defaultText,alpha);
        }

        @Override
        public void renderItemStack(GuiGraphics stack, int renX, int renY, ItemStack itemStack, float alpha) {
            RenderHelper.renderItem(stack,itemStack,renX,renY-2,0.7F);
        }
    }
}
