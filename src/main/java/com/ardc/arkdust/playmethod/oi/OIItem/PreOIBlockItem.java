package com.ardc.arkdust.playmethod.oi.OIItem;

import com.ardc.arkdust.playmethod.oi.ori_infection.IOIItem;
import com.ardc.arkdust.preobject.PreBlockItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class PreOIBlockItem extends PreBlockItem implements IOIItem {
    private final int OILevel;
    private final int damage;
    private final int gDamage;
    private final int playerOIPointAdd;
    private final int guaranteePlayerOIPointAdd;
    public PreOIBlockItem(Block block, Properties properties, boolean exp, int OILevel, int damage, int gDamage, int playerOIPointAdd, int guaranteePlayerOIPointAdd) {
        super(block,properties,exp);
        this.OILevel = OILevel;
        this.damage = damage;
        this.gDamage = gDamage;
        this.playerOIPointAdd = playerOIPointAdd;
        this.guaranteePlayerOIPointAdd = guaranteePlayerOIPointAdd;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        list.add(infoAddOfOI());
        super.appendHoverText(itemstack, world, list, flag);
    }

    @Override
    public int getOILevel() {
        return OILevel;
    }

    @Override
    public int doDamage() {
        return damage;
    }

    @Override
    public int guaranteeDamage() {
        return gDamage;
    }

    @Override
    public int playerOIPointAdd() {
        return playerOIPointAdd;
    }

    @Override
    public int guaranteePlayerOIPointAdd() {
        return guaranteePlayerOIPointAdd;
    }
}
