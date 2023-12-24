package com.ardc.arkdust.gui.screen.menu;

import com.ardc.arkdust.preobject.PreItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class MenuTestItem extends PreItem {

    public MenuTestItem() {
        super(new Properties().fireResistant());
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player playerEntity, InteractionHand hand) {
        if(world.isClientSide){
            DistExecutor.safeCallWhenOn(Dist.CLIENT,()-> MenuScreen.OpenMenuGui::new);
        }
        return super.use(world,playerEntity,hand);
    }
}


