package com.ardc.arkdust.gui.widget;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.gui.widget.pre.AbstractBlackButton;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TranslationTextComponent;

public class MenuReturnButton extends AbstractBlackButton {
    public MenuReturnButton(int screenX, int screenY, IPressable progress) {
        super(screenX/64,screenY/36,screenX/8,screenY/15,new TranslationTextComponent("gui." + Utils.MOD_ID + "menu.button.return"),progress);
    }

    @Override
    public void renderButton(MatrixStack stack, int mouseX, int mouseY, float p_230431_4_) {
        super.renderButton(stack,mouseX,mouseY,p_230431_4_);

        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bind(RETURN_RESOURCE);
        this.blit(stack, this.x, this.y, 0, 0, this.width, this.height,width,this.height);
    }
}
