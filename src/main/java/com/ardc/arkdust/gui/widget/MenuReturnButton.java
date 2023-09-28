package com.ardc.arkdust.gui.widget;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.gui.widget.pre.AbstractBlackButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class MenuReturnButton extends AbstractBlackButton {
    public MenuReturnButton(int screenX, int screenY, Button.OnPress progress) {
        super(screenX/64,screenY/36,screenX/8,screenY/15, Component.translatable("gui." + Utils.MOD_ID + "menu.button.return"),progress);
    }

    @Override
    public void renderWidget(GuiGraphics stack, int mouseX, int mouseY, float p_230431_4_) {
        super.renderWidget(stack,mouseX,mouseY,p_230431_4_);

        Minecraft minecraft = Minecraft.getInstance();
        stack.blit(RETURN_RESOURCE, this.getX(), this.getY(), 0, 0, this.width, this.height,width,this.height);
    }
}
