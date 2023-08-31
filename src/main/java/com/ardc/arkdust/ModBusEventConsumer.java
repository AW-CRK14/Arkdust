package com.ardc.arkdust;

import com.ardc.arkdust.registry.BlueprintTargetRegistry;
import com.ardc.arkdust.registry.StoryRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEventConsumer {
    @SubscribeEvent
    public static void OnLoadComplete(FMLLoadCompleteEvent event){
        StoryRegistry.s.pushToRegister();//¾çÇé°ü×¢²á
        BlueprintTargetRegistry.s.pushToRegister();
    }
}
