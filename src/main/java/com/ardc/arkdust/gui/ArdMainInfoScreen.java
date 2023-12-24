package com.ardc.arkdust.gui;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.gui.widget.MenuCloseAllButton;
import com.ardc.arkdust.gui.widget.MenuReturnButton;
import com.ardc.arkdust.gui.widget.pre.AbstractBlackButton;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.Entity;

public class ArdMainInfoScreen extends Screen {

    public static ResourceLocation background = new ResourceLocation(Utils.MOD_ID,"textures/gui/info/background/common.png");
    public static ResourceLocation plank = new ResourceLocation(Utils.MOD_ID,"textures/gui/info/background/plank.png");
    public static ResourceLocation white_slash = new ResourceLocation(Utils.MOD_ID,"textures/gui/info/material/white_slash.png");
    MenuCloseAllButton closeButton;
    MenuReturnButton returnButton;
    protected int timer;

    public ArdMainInfoScreen(Component p_i51108_1_) {
        super(p_i51108_1_);
    }

    @Override
    protected void init(){
        timer = 0;
//        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        Entity entity = this.minecraft.getCameraEntity();
        this.closeButton = new MenuCloseAllButton(this.width,this.height,entity);
        this.addRenderableWidget(this.closeButton);

        this.returnButton = new MenuReturnButton(this.width,this.height,(b)-> this.onClose());
        this.addRenderableWidget(this.returnButton);

        super.init();
    }

    public void render(GuiGraphics ms, int mouseX, int mouseY, float partialTicks){
        super.render(ms,mouseX,mouseY,partialTicks);
        doTick();
//        this.renderBackground(ms);
//        ms.blit(background,0,0,0,0,this.width,this.height,this.width,this.height);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        defaultAlpha(ms);
        ms.blit(background,0,0,0,0,this.width,this.height,this.width,this.height);

        setAlpha(0.12F,ms);
        ms.blit(white_slash,0,0,0,0,this.width,this.height/8,this.height/2,this.height/8);

        defaultAlpha(ms);
        this.returnButton.renderWidget(ms,mouseX,mouseY,partialTicks);
        this.closeButton.renderWidget(ms,mouseX,mouseY,partialTicks);

        ms.blit(AbstractBlackButton.RESOURCE,(int)(this.width/7.1F),this.height/36+2,3*this.width,0,1,this.height/15-4,this.width*4,this.height);
    }

    public boolean isPauseScreen() {
        return false;
    }

    public void doTick() {
        super.tick();
        if(timer < 5)
            timer++;
    }

    public void defaultAlpha(GuiGraphics s){
        setAlpha(0.2F,s);
    }
    public void setAlpha(float num,GuiGraphics s){
        num = Math.max(0,Math.min(0.2F,num));
        s.setColor(1,1,1,num*timer);
    }
    public void drawScaledText(GuiGraphics stack,float scaleFactor,Component text,int color16,float rx,float ry,boolean shadow){
        float restore = (float) 1/scaleFactor;
        stack.pose().scale(scaleFactor,scaleFactor,scaleFactor);
        stack.setColor(1,1,1,0.2F*timer);
        RenderSystem.enableBlend();
        stack.drawString(this.font,text.getVisualOrderText(),rx*restore,ry*restore,color16,shadow);
//            this.font.drawInBatch(stack,text,rx*restore,ry*restore,color16);
        stack.pose().scale(restore,restore,restore);
    }

    public void drawScaledCenterText(GuiGraphics stack,float scaleFactor, Component textComponent, int color16, float centerX, float centerY,boolean shadow,boolean bottom) {
        FormattedCharSequence ireorderingprocessor = textComponent.getVisualOrderText();
        float restore = (float) 1/scaleFactor;
        if(bottom)
            centerY -= this.font.lineHeight * scaleFactor;
        stack.pose().scale(scaleFactor,scaleFactor,scaleFactor);
        stack.setColor(1,1,1,0.2F*timer);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        stack.drawString(this.font, ireorderingprocessor, (centerX - font.width(ireorderingprocessor) / 2F) *restore, centerY*restore, color16,shadow);
        stack.pose().scale(restore,restore,restore);
    }

    public static ResourceLocation getFromMaterial(String location){
        return new ResourceLocation(Utils.MOD_ID,"textures/gui/info/material/" + location + ".png");
    }

//    public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_){
//
//    }
}
