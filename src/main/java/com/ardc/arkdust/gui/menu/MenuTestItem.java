package com.ardc.arkdust.gui.menu;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.preobject.pre.PreItem;
import com.ardc.arkdust.registry.ModGroupRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class MenuTestItem extends PreItem {

    public MenuTestItem() {
        super(new Properties().tab(ModGroupRegistry.worldMaterial).fireResistant());
    }

    public int getUseDuration(ItemStack itemStack) {
        return 40;
    }

    public ItemStack finishUsingItem(ItemStack itemStack, World world, LivingEntity livingEntity) {
        if(world.isClientSide){
            DistExecutor.safeCallWhenOn(Dist.CLIENT,()->OpenGui::new);
        }
        return itemStack;
    }

    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.CROSSBOW;
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        playerEntity.startUsingItem(hand);
        return ActionResult.success(playerEntity.getItemInHand(hand));
    }

    public static class OpenGui{
        public OpenGui(){
            Minecraft mc = Minecraft.getInstance();
            mc.pushGuiLayer(new MenuScreen(new TranslationTextComponent(Utils.MOD_ID + ".infomac.menu")));
        }
    }
}


