package com.ardc.arkdust.gui.widget;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.gui.widget.pre.AbstractBlackButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class MenuCloseAllButton extends AbstractBlackButton {
    public MenuCloseAllButton(int screenX, int screenY, Entity entity) {
        super((int)(screenX/7.1F),screenY/36,(int)(screenX/6.4F),screenY/15, Component.translatable("gui." + Utils.MOD_ID + "menu.button.close_all"),
                (b)->{if(entity instanceof Player) ((Player) entity).closeContainer();
        });
    }

    @Override
    public void renderWidget(GuiGraphics stack, int mouseX, int mouseY, float p_230431_4_) {
        super.renderWidget(stack,mouseX,mouseY,p_230431_4_);

        stack.blit(CLOSE_RESOURCE, this.getX(), this.getY(), 0, 0, this.width, this.height,width,this.height);
    }
}
