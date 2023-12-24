package com.ardc.arkdust.Items;

import com.ardc.arkdust.preobject.PreItem;
import com.ardc.arkdust.registry.item.Item$CommonWorld;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class EnderEnergyRadar extends PreItem {
    public final boolean work;

    public EnderEnergyRadar(boolean canWork) {
        super(new Properties().fireResistant().durability(16));
        this.work = canWork;
    }

    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        if(!work){
            list.add(Component.translatable("explain.special.item.arkdust.ender_energy_radar.broken").withStyle(ChatFormatting.DARK_RED));
        }else if(itemstack.getDamageValue() == 0){
            list.add(Component.translatable("explain.special.item.arkdust.ender_energy_radar.new").withStyle(ChatFormatting.DARK_GREEN));
        }else{
            list.add(Component.translatable("explain.special.item.arkdust.ender_energy_radar.used"));
        }
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player playerEntity, InteractionHand hand) {
        playerEntity.startUsingItem(hand);
        ItemStack stack = playerEntity.getItemInHand(hand);
        if(stack.getItem().equals(this)){
            if(!work){
                if(!world.isClientSide()) playerEntity.displayClientMessage(Component.translatable("explain.special.item.arkdust.ender_energy_radar.broken").withStyle(ChatFormatting.RED),true);
                return InteractionResultHolder.pass(stack);
            }
            if(!world.isClientSide()) playerEntity.displayClientMessage(Component.translatable("explain.special.item.arkdust.ender_energy_radar.run").withStyle(ChatFormatting.DARK_AQUA),true);
            if(stack.getDamageValue() >= 15){
                stack.setCount(0);
                ItemStack newItem = new ItemStack(Item$CommonWorld.broken_ender_energy_radar.get());
                world.addFreshEntity(new ItemEntity(world,playerEntity.getX(),playerEntity.getY(),playerEntity.getZ(),newItem));
            }else{
                stack.setDamageValue(stack.getDamageValue() + 1);
            }
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.pass(stack);
    }
}


