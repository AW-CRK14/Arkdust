package com.ardc.arkdust.registry;

import com.ardc.arkdust.CodeMigration.RunHelper.CapabilityHelper;
import com.ardc.arkdust.Utils;
import com.ardc.arkdust.playmethod.story.IStorySaveCapability;
import com.ardc.arkdust.playmethod.story.StoryCapabilityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityRegistry {
    @CapabilityInject(IStorySaveCapability.class)
    public static Capability<IStorySaveCapability> STORY_CAPABILITY;

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
    }
}
