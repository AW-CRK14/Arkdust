package com.ardc.arkdust.preobject.item;

import com.ardc.arkdust.preobject.pre.PreItem;
import com.ardc.arkdust.registry.CapabilityRegistry;
import com.ardc.arkdust.registry.ModGroupRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class OIRVaccineItem extends PreItem {
    public final int vaccineLevel;
    public final float therapeuticFactors;
    public OIRVaccineItem(boolean exp, int vaccineLevel, float therapeuticFactors) {
        super(new Properties().fireResistant().rarity(Rarity.UNCOMMON).stacksTo(8).tab(ModGroupRegistry.worldMaterial), exp);
        this.vaccineLevel = Math.max(vaccineLevel,1);
        this.therapeuticFactors = Math.min(Math.max(therapeuticFactors,0.1F),5);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(new TranslationTextComponent("pma.oi.OIRVaccineLevel",vaccineLevel));
        list.add(new TranslationTextComponent("pma.oi.OIRVaccineFactors",therapeuticFactors).withStyle(therapeuticFactors >= 1 ? TextFormatting.GREEN : TextFormatting.RED));
    }

    public int getUseDuration(ItemStack itemStack) {
        return this.vaccineLevel * 15 + 15;
    }

    public ItemStack finishUsingItem(ItemStack itemStack, World world, LivingEntity livingEntity) {
        if(livingEntity instanceof PlayerEntity) {
            livingEntity.getCapability(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY).ifPresent((i)->{
                int rLevel = i.ORI$getRLevel();
                if (this.vaccineLevel == rLevel + 1) {
                    i.ORI$addRLevel();
                    i.ORI$resetPoint();
                    if(world.isClientSide)
                        ((PlayerEntity) livingEntity).displayClientMessage(new TranslationTextComponent("pma.oi.OIPointReset").withStyle(TextFormatting.GREEN), false);
                    itemStack.shrink(1);
                } else if (this.vaccineLevel <= rLevel) {
                    int minus = (int) (-vaccineLevel * vaccineLevel * 24 * therapeuticFactors);
                    i.ORI$addPoint(minus);
                    System.out.println(minus);
                    itemStack.shrink(1);
                } else {
                    if(world.isClientSide)
                        ((PlayerEntity) livingEntity).displayClientMessage(new TranslationTextComponent("pma.oi.vaccineLevelTooHigh").withStyle(TextFormatting.RED), false);
                }
            });
        }
        return itemStack;
    }

    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.CROSSBOW;
    }

    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity playerEntity, Hand p_77659_3_) {
        playerEntity.startUsingItem(p_77659_3_);
        return ActionResult.success(playerEntity.getMainHandItem());
    }
}
