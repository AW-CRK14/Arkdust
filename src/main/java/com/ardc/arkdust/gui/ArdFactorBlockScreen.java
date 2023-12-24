package com.ardc.arkdust.gui;



import com.ardc.arkdust.Utils;
import com.ardc.arkdust.advanced_obj.AdvancedContainerMenu;
import com.ardc.arkdust.gui.widget.RenderOverrideSlot;
import com.ardc.arkdust.helper.RenderHelper;
import com.ardc.arkdust.network.blockentity.NoticeBlockSaveDataNetwork;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.common.MinecraftForge;

public abstract class ArdFactorBlockScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

    public static final Font MSYH = RenderHelper.getFontInstance(new ResourceLocation(Utils.MOD_ID,"msyh"));
    public static final Font BENDER = RenderHelper.getFontInstance(new ResourceLocation(Utils.MOD_ID,"bender"));

    public static final ResourceLocation PLAYER = new ResourceLocation(Utils.MOD_ID,"textures/gui/aefi/player_inventory.png");
    public static final ResourceLocation BACKGROUND = new ResourceLocation(Utils.MOD_ID,"textures/gui/aefi/background.png");

    public record MacInfo(ResourceLocation macTypeId/*TODO*/ , int level , boolean access , boolean project){
        public MacInfo(ResourceLocation macTypeId , int level ){this(macTypeId,level,false,false);}
    }


    public static final float BACKGROUND_SCALE = 0.0945F;

    public final MacInfo MAC_INFO;
    public int yOffset = -55;
    public ArdFactorBlockScreen(T p_i51105_1_, Inventory p_i51105_2_, Component p_i51105_3_,MacInfo macInfo) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
        initialization();
        this.MAC_INFO = macInfo;
    }

    protected abstract ResourceLocation getUI();
    public ResourceLocation getPlayerUI(){return PLAYER;};
    public ResourceLocation getMacLogo(){return new ResourceLocation(MAC_INFO.macTypeId.getNamespace(),"textures/gui/aefi/mac_logo/"+ MAC_INFO.macTypeId.getPath() + ".png");}
    public MutableComponent getTrans(){return Component.translatable("machine."+  MAC_INFO.macTypeId.getNamespace() + ".name." + MAC_INFO.macTypeId.getPath());}

    protected void initialization(){
//        this.imageWidth = (int) (4410* BACKGROUND_SCALE);
//        this.imageHeight = (int) (1560* BACKGROUND_SCALE);
        this.imageWidth = 416;
        this.imageHeight = 147;
    };

    private static final float oversample = 0.1F;
    private static final float oversampleScale = 1/ oversample;


    @Override
    public void onClose() {
        super.onClose();
        if(this.getMenu() instanceof AdvancedContainerMenu menu && menu.tileEntity != null){
            menu.tileEntity.setChanged();
//            NoticeBlockSaveDataNetwork.INSTANCE.sendToServer(new NoticeBlockSaveDataNetwork.Pack(menu.pos));
        }
    }

    @Override
    protected void renderBg(GuiGraphics stack, float tick, int x, int y) {

        this.leftPos = (this.width - imageWidth)/2;
        this.topPos = (this.height - imageHeight)/2 + yOffset;

        renderPlayerInventoryBackground(stack);
        renderAEFIBackground(stack);
        stack.pose().pushPose();
        stack.pose().translate(126,24,0);
        stack.pose().scale(oversample,oversample,1);
//        stack.blit(getUI(), (int) (leftPos + imageWidth * 0.256F + UIRenderOffset(false)), (int) (topPos + imageHeight * 0.142F + UIRenderOffset(false)),0,0, (int) (imageWidth * 0.747F), (int)(imageHeight*0.725F),  (int) (imageWidth * 0.747F), (int)(imageHeight*0.725F));
        stack.blit(getUI(), (int) (leftPos*oversampleScale+UIRenderOffset(false) -204), (int) (topPos*oversampleScale+UIRenderOffset(true)-34),0,0,(int) ((311 * oversampleScale - 6) * UIRenderScale()),(int) ((106 * oversampleScale - 5)* UIRenderScale()),(int) ((311 * oversampleScale -6) * UIRenderScale()),(int) ((106 * oversampleScale -5)* UIRenderScale()));
        stack.pose().popPose();
    }

    public void renderPlayerInventoryBackground(GuiGraphics stack){
        stack.blit(getPlayerUI(),this.width/2-99,topPos+150,0,0, 198, 104, 198, 104);
    }

    public void renderAEFIBackground(GuiGraphics stack){
        stack.blit(BACKGROUND,leftPos,topPos,0,0, imageWidth, imageHeight, imageWidth, imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics p_230451_1_, int p_230451_2_, int p_230451_3_) {

    }
    @Deprecated
    public int UIRenderOffset(boolean isY){return 0;}
    public float UIRenderScale(){return 1;}

    public void renderFacSlot(GuiGraphics stack, RenderOverrideSlot slot, int mouseX, int mouseY, float alpha) {
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
//        stack.pose().translate(0.0F, 0.0F, 100.0F);

        stack.setColor(1,1,1,alpha);
        RenderSystem.enableBlend();
//        RenderSystem.disableDepthTest();
        slot.renderBackground(stack,i,j,alpha,itemstack);

        if (itemstack.isEmpty() && slot.isActive()) {
            slot.renderWhenEmpty(stack,i,j,alpha);
        }

        if (!flag1) {
            if (flag) {
                slot.renderWhenDrag(stack,i,j,alpha,itemstack);
            }

            if(!itemstack.isEmpty() && slot.isActive()){
                slot.renderWhenNotEmpty(stack,i, j,mouseX,mouseY,itemstack,this.font,s,alpha);
            }
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
//        float alpha = 1;
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        stack.setColor(1,1,1,alpha);
        stack.flush();


        //render background
//        renderBackground(stack);
        this.renderBg(stack, tick, mouseX, mouseY);
        MinecraftForge.EVENT_BUS.post(new ContainerScreenEvent.Render.Background(this, stack, mouseX, mouseY));

        int i = this.leftPos;
        int j = this.topPos;


        //render title
        stack.pose().pushPose();
        stack.pose().translate(i,j,0);
        stack.blit(getMacLogo(),1,1,0,0,18,18,18,18);
        stack.pose().translate(23,13,0);
        stack.pose().scale(1.25F,1.25F,1);
        stack.drawString(MSYH,getTrans(),0,0,0x2E2E2E,false);
        if(MAC_INFO.level > 0){
            stack.pose().translate(MSYH.width(getTrans()) + 1.5F,2.5F,0);
            stack.pose().scale(0.8F,0.8F,1);
            stack.pose().pushPose();
            stack.pose().scale(0.4F,0.4F,1);

            stack.fill(0,-12,1,0,0xFF8C8C8C);
            stack.pose().popPose();
            stack.pose().translate(2,-2.5F,0);
            stack.drawString(BENDER,"Lv." + MAC_INFO.level,0,0,0xFF8C8C8C,false);
        }
        stack.pose().popPose();



        //super
        for(Renderable renderable : this.renderables) {
            renderable.render(stack, mouseX, mouseY, tick);
        }


        stack.pose().pushPose();
        stack.pose().translate((float)i, (float)j, 0.0F);
        stack.setColor(1,1,1,alpha);

        this.hoveredSlot = null;


        for(int i1 = 0; i1 < this.menu.slots.size(); ++i1) {
            Slot slot = this.menu.slots.get(i1);
            boolean facFlag = slot instanceof RenderOverrideSlot;

            if (slot.isActive()) {
                if(facFlag){
                    this.renderFacSlot(stack, (RenderOverrideSlot) slot,mouseX,mouseY,alpha);
                } else {
                    this.renderSlot(stack, slot);
                }
            }


            if (this.hoveredSlot == null && this.isHovering(slot, mouseX, mouseY) && slot.isActive()) {
//            if (slot.isActive()) {
                this.hoveredSlot = slot;
                if(facFlag){
                    ((RenderOverrideSlot) slot).renderWhenOn(stack,slot.x,slot.y,alpha,slot.getItem(),getSlotColor(i1),mouseX, mouseY);
                }else if (this.hoveredSlot.isHighlightable()){
                    renderSlotHighlight(stack, slot.x, slot.y, 0, getSlotColor(i1));
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
            this.renderFloatingItem(stack, this.snapbackItem, (int) (this.snapbackStartX * (1-f) + this.snapbackEnd.x* f), (int) (this.snapbackStartY * (1-f) + this.snapbackEnd.y* f), (String)null);
        }

        stack.pose().popPose();
    }

//    @Override
//    public int getSlotColor(int p_getSlotColor_1_) {
//        return 0;
//    }
}
