package com.ardc.arkdust.preobject.item;

import com.ardc.arkdust.NewPlayingMethod.OriInfection.OIMain;
import com.ardc.arkdust.preobject.pre.PreItem;
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

public class BaseBlueprintItem extends PreItem {
    public final int level;
    public final int weight;
    public BaseBlueprintItem(boolean exp, int level,int weight) {
        super(new Properties().fireResistant().rarity(Rarity.RARE).tab(ModGroupRegistry.terraIndustrialMaterial), exp);
        this.level = Math.max(level,1);
        this.weight = weight;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        if(this.weight > 0) {
            list.add(new TranslationTextComponent("pma.bp.baseBPLevel", level).withStyle(TextFormatting.BLUE));
        }else{
            list.add(new TranslationTextComponent("pma.bp.BPPiece").withStyle(TextFormatting.BLUE));
        }
    }
}
