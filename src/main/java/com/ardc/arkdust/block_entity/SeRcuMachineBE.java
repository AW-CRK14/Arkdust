package com.ardc.arkdust.block_entity;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.gui.ArdFactorBlockScreen;
import com.ardc.arkdust.gui.widget.FactorySlot;
import com.ardc.arkdust.registry.ContainerRegistry;
import com.ardc.arkdust.registry.TileEntityTypeRegistry;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;

public class SeRcuMachineBE extends TileEntity implements ITickableTileEntity,INamedContainerProvider{


    public final int playerSlotIndexFirst = 7;
    private final IInventory inventory = new Inventory(7);
    private final Info info = new Info();
    public SeRcuMachineBE() {
        super(TileEntityTypeRegistry.SE_RCU_MACHINE_BE.get());
    }

    @Override
    public void tick() {

    }

    public Info getInfo() {
        return info;
    }

    public IInventory getInventory() {
        return inventory;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("gui." + Utils.MOD_ID + ".cworld.se_rcu_machine");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        return new SelfContainer(id,playerInventory,this.info,this.inventory);
    }

    public static class Info implements IIntArray {
        int energyIn = 0;
        int time = 0;

        @Override
        public int get(int index) {
            return index==0 ? energyIn : time;
        }

        @Override
        public void set(int index, int value) {
            if(index==0) energyIn = value;
            else time = value;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public static class SelfContainer extends Container{
        private final Info info;

        public static SelfContainer createContainer(int synId, PlayerInventory playerInventory, PacketBuffer data) {
            SeRcuMachineBE be = (SeRcuMachineBE)Minecraft.getInstance().level.getBlockEntity(data.readBlockPos());
            return new SelfContainer(synId,playerInventory,be.getInfo(), be.getInventory());
        }
        public SelfContainer(int synId, PlayerInventory playerInventory, Info info,IInventory inventory) {
            super(ContainerRegistry.se_rcu_mac.get(),synId);
            this.addDataSlots(info);
            checkContainerSize(inventory,7);
            checkContainerDataCount(info,2);
            this.info = info;

            this.addSlot(new FactorySlot(inventory,0,11,26));
            this.addSlot(new FactorySlot(inventory,1,46,26));
            this.addSlot(new FactorySlot(inventory,2,81,26));
            this.addSlot(new FactorySlot(inventory,3,116,26));
            this.addSlot(new FactorySlot(inventory,4,152,26));
            this.addSlot(new FactorySlot(inventory,5,211,26));
            this.addSlot(new FactorySlot(inventory,6,271,26));

            this.addDataSlots(info);
            addPlayerInventory(playerInventory);
        }

        private void addPlayerInventory(PlayerInventory inventory){
            addSlotBox(inventory,70,100,9);
            addSlotBox(inventory,70,118,18);
            addSlotBox(inventory,70,136,27);
            addSlotBox(inventory,70,160,0);
        }

        private void addSlotBox(PlayerInventory inventory,int xForm,int yFrom,int indexFrom){
            for (int i = 0; i < 9 ; i++){
                addSlot(new Slot(inventory,indexFrom+i,xForm,yFrom));
                xForm += 18;
            }
        }

        public Info getInfo() {
            return info;
        }

        @Override
        public boolean stillValid(PlayerEntity p_75145_1_) {
            return true;
        }
    }

    public static class Screen extends ArdFactorBlockScreen<SelfContainer>{
        private static final int texX = 3730;
        private static final int texY = 1020;
        private static final float scale = 0.08F;
        private static final ResourceLocation ui = new ResourceLocation(Utils.MOD_ID,"textures/gui/block/cw/se_scu_machine.png");

        public Screen(SelfContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
            super(p_i51105_1_, p_i51105_2_, p_i51105_3_);

        }



        @Override
        protected ResourceLocation getUI() {
            return ui;
        }

        @Override
        protected void initialization() {
            this.imageHeight = (int) (texY * scale);
            this.imageWidth = (int) (texX * scale);
        }

        private int mouseX;
        private int mouseY;

        @Override
        public void render(MatrixStack stack, int mouseX, int mouseY, float tick) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            super.render(stack, mouseX, mouseY, tick);
        }

        @Override
        public boolean keyPressed(int key, int scan, int modifiers) {
            if (key == GLFW.GLFW_KEY_C){
                System.out.println("mouse pos:" + mouseX + "," + mouseY);
            }
            return super.keyPressed(key, scan, modifiers);
        }
    }
}
