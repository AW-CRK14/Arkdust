package com.ardc.arkdust.Items;

import com.ardc.arkdust.preobject.PreItem;
import com.ardc.arkdust.registry.ItemRegistry;
import com.ardc.arkdust.registry.ModGroupRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class EnderEnergyRadar extends PreItem {
    public final boolean work;

    public EnderEnergyRadar(boolean canWork) {
        super(new Properties().tab(ModGroupRegistry.WORLD_MATERIAL).fireResistant().durability(16));
        this.work = canWork;
    }

    public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        if(!work){
            list.add(new TranslationTextComponent("explain.special.item.arkdust.ender_energy_radar.broken").withStyle(TextFormatting.DARK_RED));
        }else if(itemstack.getDamageValue() == 0){
            list.add(new TranslationTextComponent("explain.special.item.arkdust.ender_energy_radar.new").withStyle(TextFormatting.DARK_GREEN));
        }else{
            list.add(new TranslationTextComponent("explain.special.item.arkdust.ender_energy_radar.used"));
        }
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        playerEntity.startUsingItem(hand);
        ItemStack stack = playerEntity.getItemInHand(hand);
        if(stack.getItem().equals(this)){
            if(!work){
                if(!world.isClientSide()) playerEntity.displayClientMessage(new TranslationTextComponent("explain.special.item.arkdust.ender_energy_radar.broken").withStyle(TextFormatting.RED),true);
                return ActionResult.pass(stack);
            }
            if(!world.isClientSide()) playerEntity.displayClientMessage(new TranslationTextComponent("explain.special.item.arkdust.ender_energy_radar.run").withStyle(TextFormatting.DARK_AQUA),true);
            if(stack.getDamageValue() >= 15){
                stack.setCount(0);
                ItemStack newItem = new ItemStack(ItemRegistry.broken_ender_energy_radar.get());
                world.addFreshEntity(new ItemEntity(world,playerEntity.getX(),playerEntity.getY(),playerEntity.getZ(),newItem));
            }else{
                stack.setDamageValue(stack.getDamageValue() + 1);
            }
            return ActionResult.success(stack);
        }
        return ActionResult.pass(stack);
    }
}


