package com.ardc.arkdust.gui.widget.pre;

import com.ardc.arkdust.Utils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class AbstractBlackButton extends Button {
    public AbstractBlackButton(int fromX, int fromY, int addX, int addY, ITextComponent iTextComponent, IPressable progress) {
        super(fromX,fromY,addX,addY,iTextComponent,progress);
    }

    public AbstractBlackButton(int fromX, int fromY, int addX, int addY, ITextComponent iTextComponent, IPressable progress, ITooltip tip) {
        super(fromX,fromY,addX,addY,iTextComponent,progress,tip);
    }

    public static final ResourceLocation RESOURCE = new ResourceLocation(Utils.MOD_ID,"textures/gui/widget/blackbutton.png");
    public static final ResourceLocation RETURN_RESOURCE = new ResourceLocation(Utils.MOD_ID,"textures/gui/material/menu_return.png");
    public static final ResourceLocation CLOSE_RESOURCE = new ResourceLocation(Utils.MOD_ID,"textures/gui/material/menu_close.png");

    @Override
    public void renderButton(MatrixStack stack, int mouseX, int mouseY, float p_230431_4_) {
        Minecraft minecraft = Minecraft.getInstance();//获取minecraft实例
        minecraft.getTextureManager().bind(RESOURCE);//绑定材质
        //设置渲染模式
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        //渲染内容
        RenderSystem.color4f(1,1,1,1);
        this.blit(stack, this.x, this.y,this.width*this.getYImage(this.isHovered()), 0, this.width, this.height,this.width*4,this.height);//UV部分:0->锁定 1->按下 2->正常
        this.renderBg(stack, minecraft, mouseX, mouseY);
    }


}
