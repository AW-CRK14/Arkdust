package com.ardc.arkdust.Items.pre;

import com.ardc.arkdust.preobject.PreItem;
import com.ardc.arkdust.registry.CapabilityRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class OIRVaccineItem extends PreItem {
    public final int decPoint;
    public OIRVaccineItem(boolean exp, int decrease) {
        super(new Properties().fireResistant().rarity(Rarity.UNCOMMON).stacksTo(8), exp);
        this.decPoint = decrease;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("pma.oi.OIRVaccineLevel",decPoint));
    }

    public int getUseDuration(ItemStack itemStack) {
        return 15;
    }

    public ItemStack finishUsingItem(ItemStack itemStack, Level world, LivingEntity livingEntity) {
        if(livingEntity instanceof Player) {
            livingEntity.getCapability(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY).ifPresent((i)->{
                i.ORI$addPoint(-decPoint);
            });
        }
        return itemStack;
    }

    public UseAnim getUseAnimation(ItemStack p_77661_1_) {
        return UseAnim.CROSSBOW;
    }

    public InteractionResultHolder<ItemStack> use(Level p_77659_1_, Player playerEntity, InteractionHand p_77659_3_) {
        playerEntity.startUsingItem(p_77659_3_);
        return InteractionResultHolder.success(playerEntity.getMainHandItem());
    }
}
