package com.ardc.arkdust.gui.screen.menu;

import com.ardc.arkdust.preobject.pre.PreItem;
import com.ardc.arkdust.registry.ModGroupRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class MenuTestItem extends PreItem {

    public MenuTestItem() {
        super(new Properties().tab(ModGroupRegistry.WORLD_MATERIAL).fireResistant());
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        if(world.isClientSide){
            DistExecutor.safeCallWhenOn(Dist.CLIENT,()-> MenuScreen.OpenMenuGui::new);
        }
        return super.use(world,playerEntity,hand);
    }
}


