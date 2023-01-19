package com.ardc.arkdust.registry;

import com.ardc.arkdust.capability.health_system.HealthSystemDataNetwork;
import com.ardc.arkdust.capability.rdi_auth.RDIAccountAuthDataNetwork;
import com.ardc.arkdust.capability.rdi_depot.RDIDepotDataNetwork;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModBusSetupRegistry {
    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent event){
        event.enqueueWork(HealthSystemDataNetwork::registerMessage);
        event.enqueueWork(RDIAccountAuthDataNetwork::registerMessage);
        event.enqueueWork(RDIDepotDataNetwork::registerMessage);
    }
}
