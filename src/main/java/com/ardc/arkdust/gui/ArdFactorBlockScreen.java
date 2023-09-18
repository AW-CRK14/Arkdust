package com.ardc.arkdust.gui;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.gui.widget.FactorySlot;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public abstract class ArdFactorBlockScreen<T extends Container> extends ContainerScreen<T> {

    public static final ResourceLocation player = new ResourceLocation(Utils.MOD_ID,"textures/gui/abs_factory/player_inventory.png");

    public int yOffset = -40;
    public ArdFactorBlockScreen(T p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
        initialization();
    }

    protected abstract ResourceLocation getUI();
    public ResourceLocation getPlayerUI(){
        return player;
    };

    protected abstract void initialization();

    @Override
    protected void renderBg(MatrixStack stack, float tick, int x, int y) {
        this.minecraft.getTextureManager().bind(getUI());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        this.leftPos = (this.width - imageWidth)/2;
        this.topPos = (this.height - imageHeight)/2 + yOffset;
        blit(stack,leftPos,topPos,0,0, imageWidth, imageHeight, imageWidth, imageHeight);

        renderPlayerInventoryBackground(stack);

        RenderSystem.disableBlend();
    }

    public void renderPlayerInventoryBackground(MatrixStack stack){
        this.minecraft.getTextureManager().bind(getPlayerUI());
        blit(stack,this.width/2-82,topPos+92,0,0, 166, 82, 166, 82);
    }

    @Override
    protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {

    }

    public void renderFacSlot(MatrixStack stack, FactorySlot slot, int mouseX, int mouseY, float alpha) {
        int i = slot.x;
        int j = slot.y;
        ItemStack itemstack = slot.getItem();
        boolean flag = false;
        boolean flag1 = slot == this.clickedSlot && !this.draggingItem.isEmpty() && !this.isSplittingStack;
        ItemStack itemstack1 = this.minecraft.player.inventory.getCarried();
        String s = null;
        if (slot == this.clickedSlot && !this.draggingItem.isEmpty() && this.isSplittingStack && !itemstack.isEmpty()) {
            itemstack = itemstack.copy();
            itemstack.setCount(itemstack.getCount() / 2);
        } else if (this.isQuickCrafting && this.quickCraftSlots.contains(slot) && !itemstack1.isEmpty()) {
            if (this.quickCraftSlots.size() == 1) {
                return;
            }

            if (Container.canItemQuickReplace(slot, itemstack1, true) && this.menu.canDragTo(slot)) {
                itemstack = itemstack1.copy();
                flag = true;
                Container.getQuickCraftSlotCount(this.quickCraftSlots, this.quickCraftingType, itemstack, slot.getItem().isEmpty() ? 0 : slot.getItem().getCount());
                int k = Math.min(itemstack.getMaxStackSize(), slot.getMaxStackSize(itemstack));
                if (itemstack.getCount() > k) {
                    s = TextFormatting.YELLOW.toString() + k;
                    itemstack.setCount(k);
                }
            } else {
                this.quickCraftSlots.remove(slot);
                this.recalculateQuickCraftRemaining();
            }
        }



        this.setBlitOffset(100);
        this.itemRenderer.blitOffset = 100.0F;

        slot.renderBackground(stack,i,j,alpha,itemstack);

        if (itemstack.isEmpty() && slot.isActive()) {
            slot.renderWhenEmpty(stack,i,j,alpha);
        }

//        if(isHovering(slot,mouseX,mouseY) && slot.isActive()){
//            slot.renderWhenOn(stack,i,j,0.4F,itemstack);
//        }

        if (!flag1) {
            if (flag) {
                slot.renderWhenDrag(stack,i,j,alpha,itemstack);
            }

            RenderSystem.enableDepthTest();
            this.itemRenderer.renderAndDecorateItem(this.minecraft.player, itemstack, i, j);
            //TODO
        }

        this.itemRenderer.blitOffset = 0.0F;
        this.setBlitOffset(0);
    }

    private int counter;
    private void renderTick(){
        if(counter < 10) counter++;
    }
    public void render(MatrixStack stack, int mouseX, int mouseY, float tick) {
        renderTick();
        float alpha = counter/10F;
        RenderSystem.enableBlend();
        RenderSystem.color4f(1,1,1,alpha);

        renderBackground(stack);

        //±³¾°äÖÈ¾
        this.renderBg(stack, tick, mouseX, mouseY);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiContainerEvent.DrawBackground(this, stack, mouseX, mouseY));
        RenderSystem.disableRescaleNormal();
        RenderSystem.disableDepthTest();

        int i = this.leftPos;
        int j = this.topPos;


        RenderSystem.enableBlend();
        RenderSystem.color4f(1,1,1,alpha);

        //super
        for (Widget button : this.buttons) {
            button.render(stack, mouseX, mouseY, tick);
        }


        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)i, (float)j, 0.0F);
        RenderSystem.enableRescaleNormal();
        this.hoveredSlot = null;
        RenderSystem.glMultiTexCoord2f(33986, 240.0F, 240.0F);



        for(int i1 = 0; i1 < this.menu.slots.size(); ++i1) {
            Slot slot = this.menu.slots.get(i1);
            boolean facFlag = slot instanceof FactorySlot;

            if (slot.isActive()) {
                if(facFlag){
                    this.renderFacSlot(stack, (FactorySlot) slot,mouseX,mouseY,alpha);
                } else {
                    this.renderSlot(stack, slot);
                }
            }

            if (this.isHovering(slot, mouseX, mouseY) && slot.isActive()) {
                this.hoveredSlot = slot;
                if(facFlag){
                    ((FactorySlot) slot).renderWhenOn(stack,slot.x,slot.y,0.4F * alpha,slot.getItem());
                }else {
                    RenderSystem.disableDepthTest();
                    int j1 = slot.x;
                    int k1 = slot.y;
                    RenderSystem.colorMask(true, true, true, false);
                    int slotColor = this.getSlotColor(i1);
                    this.fillGradient(stack, j1, k1, j1 + 16, k1 + 16, slotColor, slotColor);
                    RenderSystem.colorMask(true, true, true, true);
                    RenderSystem.enableDepthTest();
                }
            }
        }



        this.renderLabels(stack, mouseX, mouseY);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiContainerEvent.DrawForeground(this, stack, mouseX, mouseY));
        PlayerInventory playerinventory = this.minecraft.player.inventory;
        ItemStack itemstack = this.draggingItem.isEmpty() ? playerinventory.getCarried() : this.draggingItem;


        if (!itemstack.isEmpty()) {

            int k2 = this.draggingItem.isEmpty() ? 8 : 16;
            String s = null;
            if (!this.draggingItem.isEmpty() && this.isSplittingStack) {
                itemstack = itemstack.copy();
                itemstack.setCount(MathHelper.ceil((float)itemstack.getCount() / 2.0F));
            } else if (this.isQuickCrafting && this.quickCraftSlots.size() > 1) {
                itemstack = itemstack.copy();
                itemstack.setCount(this.quickCraftingRemainder);
                if (itemstack.isEmpty()) {
                    s = "" + TextFormatting.YELLOW + "0";
                }
            }

            this.renderFloatingItem(itemstack, mouseX - i - 8, mouseY - j - k2, s);
        }



        if (!this.snapbackItem.isEmpty()) {
            float f = (float)(Util.getMillis() - this.snapbackTime) / 100.0F;
            if (f >= 1.0F) {
                f = 1.0F;
                this.snapbackItem = ItemStack.EMPTY;
            }

            int l2 = this.snapbackEnd.x - this.snapbackStartX;
            int i3 = this.snapbackEnd.y - this.snapbackStartY;
            int l1 = this.snapbackStartX + (int)((float)l2 * f);
            int i2 = this.snapbackStartY + (int)((float)i3 * f);
            this.renderFloatingItem(this.snapbackItem, l1, i2, null);
        }



        RenderSystem.popMatrix();
        RenderSystem.enableDepthTest();
    }

//    @Override
//    public int getSlotColor(int p_getSlotColor_1_) {
//        return 0;
//    }
}
