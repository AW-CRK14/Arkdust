package com.ardc.arkdust.gui.widget.pre;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.gui.ArdMainInfoScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MainMenuTagCardButton extends Button {
    public MainMenuTagCardButton(int fromX, int fromY, int addX, int addY, String nameAddKey, Button.OnPress progress, ResourceLocation bg, ResourceLocation logo, int tagColorIndex, AddRender iRender) {
        super(fromX,fromY,addX,addY, Component.translatable("gui." + Utils.MOD_ID + "menu.button.card." + nameAddKey),progress,DEFAULT_NARRATION);
        if(bg!=null) this.BACKGROUND_RESOURCE = bg;
        this.LOGO_RESOURCE = logo;
        this.TAG_COLOR_INDEX = tagColorIndex;
        this.addRender = iRender;
    }

    public MainMenuTagCardButton(int fromX, int fromY, int addX, int addY, String nameAddKey, Button.OnPress progress, Button.CreateNarration tip,ResourceLocation bg,ResourceLocation logo,int tagColorIndex,AddRender iRender) {
        super(fromX,fromY,addX,addY,Component.translatable("gui." + Utils.MOD_ID + "menu.button.card." + nameAddKey),progress,tip);
        if(bg!=null) this.BACKGROUND_RESOURCE = bg;
        this.LOGO_RESOURCE = logo;
        this.TAG_COLOR_INDEX = tagColorIndex;
        this.addRender = iRender;
    }

    public ResourceLocation BACKGROUND_RESOURCE = ArdMainInfoScreen.plank;
    public ResourceLocation LOGO_RESOURCE;
    public int TAG_COLOR_INDEX;
    public float alphaFactor = 1;
    public AddRender addRender;
    public static final ResourceLocation TAG = new ResourceLocation(Utils.MOD_ID,"textures/gui/info/material/menu_tag_color.png");
    public static final ResourceLocation II = new ResourceLocation(Utils.MOD_ID,"textures/gui/info/material/ii_color.png");

    public void renderWithAlphaFac(GuiGraphics stack, int mouseX, int mouseY, float p_230431_4_, float af){
        alphaFactor = af;
        renderWidget(stack,mouseX,mouseY,p_230431_4_);
    }

    @Override
    public void renderWidget(GuiGraphics stack, int mouseX, int mouseY, float p_230431_4_) {
        //设置渲染模式
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        //渲染内容
        int rx = (int) (this.width*0.2F);

        stack.setColor(1,1,1,0.95F*alphaFactor);
        stack.blit(BACKGROUND_RESOURCE,this.getX(),this.getY(),0,0,this.width,this.height,this.width,this.height);


        stack.setColor(1,1,1,0.7F * alphaFactor);
        stack.blit(TAG,this.getX(),this.getY(),this.width*TAG_COLOR_INDEX,0,rx,this.height,this.width*10,this.height);

        if(LOGO_RESOURCE != null){
            stack.setColor(1,1,1,1 * alphaFactor);
            stack.blit(LOGO_RESOURCE,this.getX(),this.getY(),0,0,rx,rx,rx,rx);
        }

        if(addRender != null){
            stack.setColor(1,1,1,1 * alphaFactor);
            addRender.addR(this,this.getX() + rx + 3,this.getY() + 3,this.width - rx - 6,this.height -6,stack);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public interface AddRender {
        void addR(MainMenuTagCardButton p_onPress_1_,int autoX,int autoY,int autoAddX,int autoAddY,GuiGraphics stack);
    }
}
