package com.ardc.arkdust.gui;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.gui.widget.MenuCloseAllButton;
import com.ardc.arkdust.gui.widget.MenuReturnButton;
import com.ardc.arkdust.gui.widget.pre.AbstractBlackButton;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class ArdMainInfoScreen extends Screen {

    ResourceLocation background = new ResourceLocation(Utils.MOD_ID,"textures/gui/info/background/common.png");
    MenuCloseAllButton closeButton;
    MenuReturnButton returnButton;
    int timer;

    protected ArdMainInfoScreen(ITextComponent p_i51108_1_) {
        super(p_i51108_1_);
    }

    @Override
    protected void init(){
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        Entity entity = this.minecraft.getCameraEntity();
        this.closeButton = new MenuCloseAllButton(this.width,this.height,(b)->{
            if(entity instanceof PlayerEntity)
                ((PlayerEntity) entity).closeContainer();
        });
        this.buttons.add(this.closeButton);

        this.returnButton = new MenuReturnButton(this.width,this.height,(b)->this.onClose());
        this.buttons.add(this.returnButton);

        super.init();
    }

    public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks){
        this.minecraft.getTextureManager().bind(background);
        this.renderBackground(ms);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.color4f(1,1,1,0.1F*timer);
        this.blit(ms,0,0,0,0,this.width,this.height,this.width,this.height);
        this.returnButton.renderButton(ms,mouseX,mouseY,partialTicks);
        this.closeButton.renderButton(ms,mouseX,mouseY,partialTicks);
        this.minecraft.getTextureManager().bind(AbstractBlackButton.RESOURCE);
        this.blit(ms,(int)(this.width/7.1F),this.height/36+2,3*this.width,0,1,this.height/15-4,this.width*4,this.height);
        RenderSystem.disableBlend();
    }

    public boolean isPauseScreen() {
        return false;
    }

    public void tick() {
        super.tick();
        if(timer < 10)
            timer++;
    }

//    public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_){
//
//    }
}
