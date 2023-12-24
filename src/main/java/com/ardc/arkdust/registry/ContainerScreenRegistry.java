package com.ardc.arkdust.registry;

import com.ardc.arkdust.block_entity.OERIMachineBE;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ContainerScreenRegistry {
    @SubscribeEvent
    public static void bindScreen(FMLClientSetupEvent event){
        event.enqueueWork(()->{
            MenuScreens.register(ContainerRegistry.se_rcu_mac.get(), OERIMachineBE.Screen::new);
        });
    }
}
