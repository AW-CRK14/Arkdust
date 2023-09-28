package com.ardc.arkdust.block_entity;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.gui.ArdFactorBlockScreen;
import com.ardc.arkdust.gui.widget.FactorySlot;
import com.ardc.arkdust.registry.ContainerRegistry;
import com.ardc.arkdust.registry.ItemRegistry;
import com.ardc.arkdust.registry.TileEntityTypeRegistry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class SeRcuMachineBE extends BlockEntity implements MenuProvider{


    public final int playerSlotIndexFirst = 7;
    public final Container slots = new Container(7);
    private final Info info = new Info();
    public SeRcuMachineBE(BlockPos pos, BlockState state) {
        super(TileEntityTypeRegistry.SE_RCU_MACHINE_BE.get(),pos,state);
        setChanged();
    }

    public Info getInfo() {
        return info;
    }



    //MenuProvider
    @Override
    public Component getDisplayName() {
        return Component.translatable("gui." + Utils.MOD_ID + ".cworld.se_rcu_machine");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int winId, Inventory playerInventory, Player player) {
        return new Menu(winId,player,worldPosition);
    }



    //Taker
    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(slots, Direction.DOWN, Direction.NORTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, Direction facing) {
        if (!this.remove && facing != null && capability == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER) {
            if (facing == Direction.DOWN)
                return handlers[0].cast();
            else
                return handlers[1].cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        for (LazyOptional<? extends IItemHandler> handler : handlers)
            handler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.handlers = SidedInvWrapper.create(slots, Direction.DOWN, Direction.NORTH);
    }



    public static class Info implements ContainerData {
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

    public static class Container extends SimpleContainer implements WorldlyContainer{

        public Container(int size){
            super(size);
        }

        @Override
        public int[] getSlotsForFace(Direction direction) {
            return direction.equals(Direction.DOWN) ? new int[]{6} :new int[]{0,1,2,3,4,5};
        }

        @Override
        public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, Direction direction) {
            return !Objects.equals(direction, Direction.DOWN) && (itemStack.getItem().equals(ItemRegistry.sealed_energy_substrate) == (index==5));
        }

        @Override
        public boolean canTakeItemThroughFace(int index, ItemStack itemStack, Direction direction) {
            return Objects.equals(direction, Direction.DOWN) && index == 6;
        }

        @Override
        public ItemStack getItem(int p_19157_) {
            return super.getItem(p_19157_);
        }
    }

    public static class Menu extends AbstractContainerMenu{
        public final Info info;

        public Menu(int synId, Inventory playerInventory, FriendlyByteBuf data) {
            this(synId,playerInventory.player,data.readBlockPos());
        }

        public Menu(int synId, Player player, BlockPos pos) {
            super(ContainerRegistry.se_rcu_mac.get(),synId);
            if(player.level().getBlockEntity(pos) instanceof SeRcuMachineBE be) {
                this.addDataSlots(be.info);
                this.info = be.getInfo();

                this.addSlot(new FactorySlot(be.slots, 0, 11, 26));
                this.addSlot(new FactorySlot(be.slots, 1, 46, 26));
                this.addSlot(new FactorySlot(be.slots, 2, 81, 26));
                this.addSlot(new FactorySlot(be.slots, 3, 116, 26));
                this.addSlot(new FactorySlot(be.slots, 4, 152, 26));
                this.addSlot(new FactorySlot(be.slots, 5, 211, 26));
                this.addSlot(new FactorySlot(be.slots, 6, 271, 26));

                this.addDataSlots(info);
            }else {
                info = new Info();
            }
            addPlayerInventory(player.getInventory());
        }

        private void addPlayerInventory(Inventory inventory){
            addSlotBox(inventory,70,100,9);
            addSlotBox(inventory,70,118,18);
            addSlotBox(inventory,70,136,27);
            addSlotBox(inventory,70,160,0);
        }

        private void addSlotBox(Inventory inventory,int xForm,int yFrom,int indexFrom){
            for (int i = 0; i < 9 ; i++){
                addSlot(new Slot(inventory,indexFrom+i,xForm,yFrom));
                xForm += 18;
            }
        }

        public Info getInfo() {
            return info;
        }

        @Override
        public boolean stillValid(Player p_75145_1_) {
            return true;
        }

        @Override
        public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
            return new ItemStack(Items.AIR);
        }
    }

    public static class Screen extends ArdFactorBlockScreen<Menu>{
        private static final int texX = 3730;
        private static final int texY = 1020;
        private static final float scale = 0.08F;
        private static final ResourceLocation ui = new ResourceLocation(Utils.MOD_ID,"textures/gui/block/cw/se_scu_machine.png");

        public Screen(Menu p_i51105_1_, Inventory p_i51105_2_, Component p_i51105_3_) {
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
        public void render(GuiGraphics stack, int mouseX, int mouseY, float tick) {
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
