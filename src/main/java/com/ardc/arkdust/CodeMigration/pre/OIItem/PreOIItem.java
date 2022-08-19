package com.ardc.arkdust.CodeMigration.pre.OIItem;

import com.ardc.arkdust.CodeMigration.pre.PreItem;
import com.ardc.arkdust.NewPlayingMethod.OriInfection.IOIItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class PreOIItem extends PreItem implements IOIItem {
    private final int OILevel;
    private final int damage;
    private final int gDamage;
    private final int playerOIPointAdd;
    private final int guaranteePlayerOIPointAdd;
    public PreOIItem(Properties properties, boolean exp,int OILevel,int damage,int gDamage,int playerOIPointAdd,int guaranteePlayerOIPointAdd) {
        super(properties, exp);
        this.OILevel = OILevel;
        this.damage = damage;
        this.gDamage = gDamage;
        this.playerOIPointAdd = playerOIPointAdd;
        this.guaranteePlayerOIPointAdd = guaranteePlayerOIPointAdd;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
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
