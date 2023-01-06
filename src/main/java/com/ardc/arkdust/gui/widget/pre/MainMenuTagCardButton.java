package com.ardc.arkdust.gui.widget.pre;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.gui.ArdMainInfoScreen;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MainMenuTagCardButton extends Button {
    public MainMenuTagCardButton(int fromX, int fromY, int addX, int addY, String nameAddKey, IPressable progress,ResourceLocation bg,ResourceLocation logo,int tagColorIndex,AddRender iRender) {
        super(fromX,fromY,addX,addY,new TranslationTextComponent("gui." + Utils.MOD_ID + "menu.button.card." + nameAddKey),progress);
        this.BACKGROUND_RESOURCE = bg;
        this.LOGO_RESOURCE = logo;
        this.TAG_COLOR_INDEX = tagColorIndex;
        this.addRender = iRender;
    }

    public MainMenuTagCardButton(int fromX, int fromY, int addX, int addY, String nameAddKey, IPressable progress, ITooltip tip,ResourceLocation bg,ResourceLocation logo,int tagColorIndex,AddRender iRender) {
        super(fromX,fromY,addX,addY,new TranslationTextComponent("gui." + Utils.MOD_ID + "menu.button.card." + nameAddKey),progress,tip);
        this.BACKGROUND_RESOURCE = bg;
        this.LOGO_RESOURCE = logo;
        this.TAG_COLOR_INDEX = tagColorIndex;
        this.addRender = iRender;
    }

    public ResourceLocation BACKGROUND_RESOURCE;
    public ResourceLocation LOGO_RESOURCE;
    public int TAG_COLOR_INDEX;
    public float alphaFactor = 1;
    public AddRender addRender;
    public static final ResourceLocation TAG = new ResourceLocation(Utils.MOD_ID,"textures/gui/info/material/menu_tag_color.png");
    public static final ResourceLocation II = new ResourceLocation(Utils.MOD_ID,"textures/gui/info/material/ii_color.png");

    public void renderWithAlphaFac(MatrixStack stack, int mouseX, int mouseY, float p_230431_4_,float af){
        alphaFactor = af;
        renderButton(stack,mouseX,mouseY,p_230431_4_);
    }

    @Override
    public void renderButton(MatrixStack stack, int mouseX, int mouseY, float p_230431_4_) {
        TextureManager manager = Minecraft.getInstance().getTextureManager();
        //设置渲染模式
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        //渲染内容
        int rx = (int) (this.width*0.2F);

        if(BACKGROUND_RESOURCE!=null)
            manager.bind(BACKGROUND_RESOURCE);
        else
            manager.bind(ArdMainInfoScreen.plank);
        RenderSystem.color4f(1,1,1,0.95F*alphaFactor);
        blit(stack,this.x,this.y,0,0,this.width,this.height,this.width,this.height);


        manager.bind(TAG);
        RenderSystem.color4f(1,1,1,0.7F * alphaFactor);
        blit(stack,this.x,this.y,this.width*TAG_COLOR_INDEX,0,rx,this.height,this.width*10,this.height);

        if(LOGO_RESOURCE != null){
            manager.bind(LOGO_RESOURCE);
            RenderSystem.color4f(1,1,1,1 * alphaFactor);
            blit(stack,this.x,this.y,0,0,rx,rx,rx,rx);
        }

        if(addRender != null){
            RenderSystem.color4f(1,1,1,1 * alphaFactor);
            addRender.addR(this,this.x + rx + 3,this.y + 3,this.width - rx - 6,this.height -6,stack);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public interface AddRender {
        void addR(MainMenuTagCardButton p_onPress_1_,int autoX,int autoY,int autoAddX,int autoAddY,MatrixStack stack);
    }
}
