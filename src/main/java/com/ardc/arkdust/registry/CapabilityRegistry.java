package com.ardc.arkdust.registry;

import com.ardc.arkdust.capability.health_system.IHealthSystemCapability;
import com.ardc.arkdust.capability.rdi_auth.IRDIAccountAuthCapability;
import com.ardc.arkdust.capability.rdi_depot.IRDIDepotCapability;
import com.ardc.arkdust.capability.story.IStorySaveCapability;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityRegistry {
    @CapabilityInject(IStorySaveCapability.class)
    public static Capability<IStorySaveCapability> STORY_CAPABILITY;
    @CapabilityInject(IHealthSystemCapability.class)
    public static Capability<IHealthSystemCapability> HEALTH_SYSTEM_CAPABILITY;
    @CapabilityInject(IRDIAccountAuthCapability.class)
    public static Capability<IRDIAccountAuthCapability> RDI_ACCOUNT_AUTH_CAPABILITY;
    @CapabilityInject(IRDIDepotCapability.class)
    public static Capability<IRDIDepotCapability> RDI_DEPOT_CAPABILITY;

    @SubscribeEvent
    public static void StoryCapabilityRegistry(FMLCommonSetupEvent event){
        event.enqueueWork(()-> CapabilityManager.INSTANCE.register(
                IStorySaveCapability.class,
                new Capability.IStorage<IStorySaveCapability>() {
                    public INBT writeNBT(Capability<IStorySaveCapability> capability, IStorySaveCapability instance, Direction side) {
                        return null;
                    }
                    public void readNBT(Capability<IStorySaveCapability> capability, IStorySaveCapability instance, Direction side, INBT nbt) {
                    }
                },
                ()->null
        ));

        event.enqueueWork(()-> CapabilityManager.INSTANCE.register(
                IHealthSystemCapability.class,
                new Capability.IStorage<IHealthSystemCapability>() {
                    public INBT writeNBT(Capability<IHealthSystemCapability> capability, IHealthSystemCapability instance, Direction side) {
                        return null;
                    }
                    public void readNBT(Capability<IHealthSystemCapability> capability, IHealthSystemCapability instance, Direction side, INBT nbt) {
                    }
                },
                ()->null
        ));

        event.enqueueWork(registerDefaultCap(IRDIAccountAuthCapability.class));
        event.enqueueWork(registerDefaultCap(IRDIDepotCapability.class));
    }

    public static <T> Runnable registerDefaultCap(Class<T> tClass){
        return ()-> CapabilityManager.INSTANCE.register(
                tClass,
                new Capability.IStorage<T>() {
                    public INBT writeNBT(Capability<T> capability, T instance, Direction side) {
                        return null;
                    }
                    public void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt) {
                    }
                },
                ()->null
        );
    }
}
