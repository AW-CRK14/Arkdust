package com.ardc.arkdust.gui.widget.pre;

import com.ardc.arkdust.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class AbstractBlackButton extends Button {
    public AbstractBlackButton(int fromX, int fromY, int addX, int addY, Component iTextComponent, Button.OnPress progress) {
        this(fromX,fromY,addX,addY,iTextComponent,progress,DEFAULT_NARRATION);
    }

    public AbstractBlackButton(int fromX, int fromY, int addX, int addY, Component iTextComponent, Button.OnPress progress, Button.CreateNarration tip) {
        super(fromX,fromY,addX,addY,iTextComponent,progress,tip);
    }

    public static final ResourceLocation RESOURCE = new ResourceLocation(Utils.MOD_ID,"textures/gui/info/widget/blackbutton.png");
    public static final ResourceLocation RETURN_RESOURCE = new ResourceLocation(Utils.MOD_ID,"textures/gui/info/material/menu_return.png");
    public static final ResourceLocation CLOSE_RESOURCE = new ResourceLocation(Utils.MOD_ID,"textures/gui/info/material/menu_close.png");

    @Override
    public void renderWidget(GuiGraphics stack, int mouseX, int mouseY, float p_230431_4_) {

        //设置渲染模式
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        //渲染内容
//        int i =this.width*getTextureX(mouseX,mouseY);
//        System.out.println("\nbutton test:" + i + "{width:"+this.width+",YImage:"+this.getYImage(this.isHovered())+"}");
        stack.blit(RESOURCE,this.getX(),this.getY(),this.width * getTextureX(mouseX,mouseY),0,this.width,this.height,this.width*4,this.height);
//        this.renderBg(stack, minecraft, mouseX, mouseY);
    }

    public int getTextureX(int mouthX,int mouthY){//0->锁定 2->上方 1->正常
        if(!this.active) return 0;
        return this.isMouseOver(mouthX,mouthY) ? 2 : 1;
    }


}
