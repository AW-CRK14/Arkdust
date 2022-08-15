package com.ardc.arkdust.CodeMigration.pre;

import com.ardc.arkdust.NewPlayingMethod.OriInfection.IOIItem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class PreItem extends Item {
    public final boolean explain;
    public PreItem(Properties properties){
        super(properties);
        this.explain = false;
    }

    public PreItem(Properties properties,boolean exp){
        super(properties);
        this.explain = exp;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
//        if(this instanceof IOIItem){
//            list.add(((IOIItem) itemstack.getItem()).infoAddOfOI());
//        }
        if(explain) {
            if (!Screen.hasShiftDown()) {
                list.add(new TranslationTextComponent("explain.exp." + this.getDescriptionId()));
            } else {
                list.add(new TranslationTextComponent("basmes.way_to_get").withStyle(TextFormatting.GOLD));
                list.add(new TranslationTextComponent("explain.get." + this.getDescriptionId()));
            }
        }
    }
}
