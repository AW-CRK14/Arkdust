package com.ardc.arkdust.block_entity;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.advanced_obj.AdvancedContainerBlockEntity;
import com.ardc.arkdust.advanced_obj.AdvancedContainerMenu;
import com.ardc.arkdust.enums.SlotType;
import com.ardc.arkdust.gui.ArdFactorBlockScreen;
import com.ardc.arkdust.gui.widget.RenderOverrideSlot;
import com.ardc.arkdust.registry.ContainerRegistry;
import com.ardc.arkdust.registry.TileEntityTypeRegistry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class OERIMachineBE extends AdvancedContainerBlockEntity {
    public static final Logger LOGGER = LogManager.getLogger();
    private final Info info = new Info();
    private static final Map<SlotType,Integer> MAP = new HashMap<>();
    static {
        MAP.put(SlotType.INPUT,5);
        MAP.put(SlotType.OUTPUT,1);
        MAP.put(SlotType.ENERGY,1);
    }
    public OERIMachineBE(BlockPos pos, BlockState state) {
        super(TileEntityTypeRegistry.SE_RCU_MACHINE_BE.get(),pos,state, MAP);
    }

    public Info getInfo() {
        return info;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("gui." + Utils.MOD_ID + ".cworld.oeri_mac");
    }


    @Override
    public AbstractContainerMenu menu(int winId, Inventory playerInventory, Player player) {
        return new OERIMachineBE.Menu(winId,player,worldPosition);
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

    public static class Menu extends AdvancedContainerMenu {
        public final Info info;

        public Menu(int synId, Inventory playerInventory, FriendlyByteBuf data) {
            this(synId,playerInventory.player,data.readBlockPos());
        }

        public Menu(int synId, Player player, BlockPos pos) {
            super(ContainerRegistry.se_rcu_mac.get(),synId,player, pos);
            if(this.tileEntity instanceof OERIMachineBE be) {
                this.addDataSlots(be.info);
                this.info = be.getInfo();

                this.addSlot(new RenderOverrideSlot.EmpIndSlot(be, 0, 168,44));
                this.addSlot(new RenderOverrideSlot.EmpIndSlot(be, 1, 185,44));
                this.addSlot(new RenderOverrideSlot.EmpIndSlot(be, 2, 202,44));
                this.addSlot(new RenderOverrideSlot.EmpIndSlot(be, 3, 168,61));
                this.addSlot(new RenderOverrideSlot.EmpIndSlot(be, 4, 185,61));
                this.addSlot(new RenderOverrideSlot.EmpIndSlot(be, 5, 337,36));
                this.addSlot(new RenderOverrideSlot.EmpIndSlot(be, 6, 335,95));

                this.addDataSlots(info);
            }else {
                info = new Info();
            }
            addPlayerInventory(player.getInventory());
        }

        private void addPlayerInventory(Inventory inventory){
            addSlotBox(inventory,132,170,9);
            addSlotBox(inventory,132,189,18);
            addSlotBox(inventory,132,208,27);
            addSlotBox(inventory,132,233,0);
        }

        private void addSlotBox(Inventory inventory,int xForm,int yFrom,int indexFrom){
            for (int i = 0; i < 9 ; i++){
                addSlot(new RenderOverrideSlot.ScaledSlot(inventory,indexFrom+i,xForm,yFrom,0.875F,0.8F));
                xForm += 19;
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
//        private static final ResourceLocation ui = new ResourceLocation(Utils.MOD_ID,"textures/gui/block/cw/se_scu_machine.png");
        private static final ResourceLocation ui = new ResourceLocation(Utils.MOD_ID,"textures/gui/aefi/orienergy_release_inducer.png");

        public Screen(Menu p_i51105_1_, Inventory p_i51105_2_, Component p_i51105_3_) {
            super(p_i51105_1_, p_i51105_2_, p_i51105_3_,new MacInfo(new ResourceLocation(Utils.MOD_ID,"orienergy_release_inducer"),0,false, false));

        }



        @Override
        protected ResourceLocation getUI() {
            return ui;
        }

        private int mouseX;
        private int mouseY;

//        @Override
//        public float UIRenderScale() {
//            return 0.99F;
//        }

//        @Override
//        public int UIRenderOffset(boolean isY) {
//            return isY ? 2 : 6;
//        }

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
