package com.ardc.arkdust.gui;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.gui.widget.FactorySlot;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.common.MinecraftForge;

public abstract class ArdFactorBlockScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

    public static final ResourceLocation player = new ResourceLocation(Utils.MOD_ID,"textures/gui/abs_factory/player_inventory.png");

    public int yOffset = -40;
    public ArdFactorBlockScreen(T p_i51105_1_, Inventory p_i51105_2_, Component p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
        initialization();
    }

    protected abstract ResourceLocation getUI();
    public ResourceLocation getPlayerUI(){
        return player;
    };

    protected abstract void initialization();

    @Override
    protected void renderBg(GuiGraphics stack, float tick, int x, int y) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        this.leftPos = (this.width - imageWidth)/2;
        this.topPos = (this.height - imageHeight)/2 + yOffset;
        stack.blit(getUI(),leftPos,topPos,0,0, imageWidth, imageHeight, imageWidth, imageHeight);

        renderPlayerInventoryBackground(stack);

        RenderSystem.disableBlend();
    }

    public void renderPlayerInventoryBackground(GuiGraphics stack){
        stack.blit(getPlayerUI(),this.width/2-82,topPos+92,0,0, 166, 82, 166, 82);
    }

    @Override
    protected void renderLabels(GuiGraphics p_230451_1_, int p_230451_2_, int p_230451_3_) {

    }

    public void renderFacSlot(GuiGraphics stack, FactorySlot slot, int mouseX, int mouseY, float alpha) {
        int i = slot.x;
        int j = slot.y;
        ItemStack itemstack = slot.getItem();
        boolean flag = false;
        boolean flag1 = slot == this.clickedSlot && !this.draggingItem.isEmpty() && !this.isSplittingStack;
        ItemStack itemstack1 = this.menu.getCarried();
        String s = null;
        if (slot == this.clickedSlot && !this.draggingItem.isEmpty() && this.isSplittingStack && !itemstack.isEmpty()) {
            itemstack = itemstack.copyWithCount(itemstack.getCount() / 2);
        } else if (this.isQuickCrafting && this.quickCraftSlots.contains(slot) && !itemstack1.isEmpty()) {
            if (this.quickCraftSlots.size() == 1) {
                return;
            }

            if (AbstractContainerMenu.canItemQuickReplace(slot, itemstack1, true) && this.menu.canDragTo(slot)) {
                flag = true;
                int k = Math.min(itemstack1.getMaxStackSize(), slot.getMaxStackSize(itemstack1));
                int l = slot.getItem().isEmpty() ? 0 : slot.getItem().getCount();
                int i1 = AbstractContainerMenu.getQuickCraftPlaceCount(this.quickCraftSlots, this.quickCraftingType, itemstack1) + l;
                if (i1 > k) {
                    i1 = k;
                    s = ChatFormatting.YELLOW.toString() + k;
                }

                itemstack = itemstack1.copyWithCount(i1);
            } else {
                this.quickCraftSlots.remove(slot);
                this.recalculateQuickCraftRemaining();
            }
        }


        stack.pose().pushPose();
        stack.pose().translate(0.0F, 0.0F, 100.0F);

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
            stack.renderItem(itemstack, i, j);
            //TODO
        }

        stack.pose().popPose();
    }

    private int counter;
    private void renderTick(){
        if(counter < 10) counter++;
    }
    public void render(GuiGraphics stack, int mouseX, int mouseY, float tick) {
        renderTick();
        float alpha = counter/10F;

//        stack.pose().pushPose();

        RenderSystem.enableBlend();
        stack.setColor(1,1,1,alpha);

        renderBackground(stack);

        //±³¾°äÖÈ¾
        this.renderBg(stack, tick, mouseX, mouseY);
        MinecraftForge.EVENT_BUS.post(new ContainerScreenEvent.Render.Background(this, stack, mouseX, mouseY));
        RenderSystem.disableDepthTest();

        int i = this.leftPos;
        int j = this.topPos;


        RenderSystem.enableBlend();
        stack.setColor(1,1,1,alpha);

        //super
        for(Renderable renderable : this.renderables) {
            renderable.render(stack, mouseX, mouseY, tick);
        }


        stack.pose().pushPose();
        stack.pose().translate((float)i, (float)j, 0.0F);
        this.hoveredSlot = null;


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
                    int l = slot.x;
                    int i2 = slot.y;
                    if (this.hoveredSlot.isHighlightable()) {
                        renderSlotHighlight(stack, l, i2, 0, getSlotColor(i1));
                    }
                }
            }
        }



        this.renderLabels(stack, mouseX, mouseY);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.ContainerScreenEvent.Render.Foreground(this, stack, mouseX, mouseY));
        ItemStack itemstack = this.draggingItem.isEmpty() ? this.menu.getCarried() : this.draggingItem;


        if (!itemstack.isEmpty()) {
            int i2 = this.draggingItem.isEmpty() ? 8 : 16;
            String s = null;
            if (!this.draggingItem.isEmpty() && this.isSplittingStack) {
                itemstack = itemstack.copyWithCount(Mth.ceil((float)itemstack.getCount() / 2.0F));
            } else if (this.isQuickCrafting && this.quickCraftSlots.size() > 1) {
                itemstack = itemstack.copyWithCount(this.quickCraftingRemainder);
                if (itemstack.isEmpty()) {
                    s = ChatFormatting.YELLOW + "0";
                }
            }

            this.renderFloatingItem(stack, itemstack, mouseX - i - 8, mouseY - j - i2, s);
        }

        if (!this.snapbackItem.isEmpty()) {
            float f = (float)(Util.getMillis() - this.snapbackTime) / 100.0F;
            if (f >= 1.0F) {
                f = 1.0F;
                this.snapbackItem = ItemStack.EMPTY;
            }

            int j2 = this.snapbackEnd.x - this.snapbackStartX;
            int k2 = this.snapbackEnd.y - this.snapbackStartY;
            int j1 = this.snapbackStartX + (int)((float)j2 * f);
            int k1 = this.snapbackStartY + (int)((float)k2 * f);
            this.renderFloatingItem(stack, this.snapbackItem, j1, k1, (String)null);
        }

        stack .pose().popPose();
        RenderSystem.enableDepthTest();
    }

//    @Override
//    public int getSlotColor(int p_getSlotColor_1_) {
//        return 0;
//    }
}
