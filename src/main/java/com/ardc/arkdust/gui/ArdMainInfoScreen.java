package com.ardc.arkdust.gui;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.gui.widget.MenuCloseAllButton;
import com.ardc.arkdust.gui.widget.MenuReturnButton;
import com.ardc.arkdust.gui.widget.pre.AbstractBlackButton;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ArdMainInfoScreen extends Screen {

    public static ResourceLocation background = new ResourceLocation(Utils.MOD_ID,"textures/gui/info/background/common.png");
    public static ResourceLocation plank = new ResourceLocation(Utils.MOD_ID,"textures/gui/info/background/plank.png");
    public static ResourceLocation white_slash = new ResourceLocation(Utils.MOD_ID,"textures/gui/info/material/white_slash.png");
    MenuCloseAllButton closeButton;
    MenuReturnButton returnButton;
    protected int timer;

    public ArdMainInfoScreen(ITextComponent p_i51108_1_) {
        super(p_i51108_1_);
    }

    @Override
    protected void init(){
        timer = 0;
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        Entity entity = this.minecraft.getCameraEntity();
        this.closeButton = new MenuCloseAllButton(this.width,this.height,(b)->{
            if(entity instanceof PlayerEntity)
                ((PlayerEntity) entity).closeContainer();
        });
        this.addButton(this.closeButton);

        this.returnButton = new MenuReturnButton(this.width,this.height,(b)-> this.onClose());
        this.addButton(this.returnButton);

        super.init();
    }

    public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks){
        this.minecraft.getTextureManager().bind(background);
        this.renderBackground(ms);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        defaultAlpha();
        this.blit(ms,0,0,0,0,this.width,this.height,this.width,this.height);

        this.minecraft.getTextureManager().bind(white_slash);

        setAlpha(0.12F);
        this.blit(ms,0,0,0,0,this.width,this.height/8,this.height/2,this.height/8);


        defaultAlpha();
        this.returnButton.renderButton(ms,mouseX,mouseY,partialTicks);
        this.closeButton.renderButton(ms,mouseX,mouseY,partialTicks);

        this.minecraft.getTextureManager().bind(AbstractBlackButton.RESOURCE);
        this.blit(ms,(int)(this.width/7.1F),this.height/36+2,3*this.width,0,1,this.height/15-4,this.width*4,this.height);
    }

    public boolean isPauseScreen() {
        return false;
    }

    public void tick() {
        super.tick();
        if(timer < 5)
            timer++;
    }

    public void defaultAlpha(){
        setAlpha(0.2F);
    }
    public void setAlpha(float num){
        num = Math.max(0,Math.min(0.2F,num));
        RenderSystem.color4f(1,1,1,num*timer);
    }
    public void drawScaledText(MatrixStack stack,float scaleFactor,ITextComponent text,int color16,float rx,float ry,boolean shadow){
        float restore = (float) 1/scaleFactor;
        stack.scale(scaleFactor,scaleFactor,scaleFactor);
        RenderSystem.color4f(1,1,1,0.2F*timer);
        RenderSystem.enableAlphaTest();
        if(shadow)
            this.font.drawShadow(stack,text,rx*restore,ry*restore,color16);
        else
            this.font.draw(stack,text,rx*restore,ry*restore,color16);
        stack.scale(restore,restore,restore);
    }

    public void drawScaledCenterText(MatrixStack stack,float scaleFactor, ITextComponent textComponent, int color16, float centerX, float centerY,boolean shadow,boolean bottom) {
        IReorderingProcessor ireorderingprocessor = textComponent.getVisualOrderText();
        float restore = (float) 1/scaleFactor;
        if(bottom)
            centerY -= this.font.lineHeight * scaleFactor;
        stack.scale(scaleFactor,scaleFactor,scaleFactor);
        RenderSystem.color4f(1,1,1,0.2F*timer);
        RenderSystem.enableAlphaTest();
        if(shadow)
            this.font.drawShadow(stack, ireorderingprocessor, (centerX - font.width(ireorderingprocessor) / 2F) *restore, centerY*restore, color16);
        else
            this.font.draw(stack, ireorderingprocessor, (centerX - font.width(ireorderingprocessor) / 2F) *restore, centerY*restore, color16);
        stack.scale(restore,restore,restore);
    }

    public static ResourceLocation getFromMaterial(String location){
        return new  ResourceLocation(Utils.MOD_ID,"textures/gui/info/material/" + location + ".png");
    }

//    public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_){
//
//    }
}
