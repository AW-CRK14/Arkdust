package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.capability.AbsCapabilityProvider;
import com.ardc.arkdust.capability.health_system.HealthSystemCapability;
import com.ardc.arkdust.capability.rdi_auth.RDIAccountAuthCapability;
import com.ardc.arkdust.capability.rdi_depot.RDIDepotCapability;
import com.ardc.arkdust.capability.story.StorySaveCapability;
import com.ardc.arkdust.helper.CapabilityHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CapabilityRegistry {
//    @CapabilityInject(IStorySaveCapability.class)
    public static Capability<StorySaveCapability> STORY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
//    @CapabilityInject(IHealthSystemCapability.class)
    public static Capability<HealthSystemCapability> HEALTH_SYSTEM_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
//    @CapabilityInject(IRDIAccountAuthCapability.class)
    public static Capability<RDIAccountAuthCapability> RDI_ACCOUNT_AUTH_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
//    @CapabilityInject(IRDIDepotCapability.class)
    public static Capability<RDIDepotCapability> RDI_DEPOT_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});


    @SubscribeEvent
    public static void onCapabilityAttach(AttachCapabilitiesEvent<Entity> event){
        Entity entity = event.getObject();
        if (entity instanceof Player){
            event.addCapability(new ResourceLocation(Utils.MOD_ID,"story"), new AbsCapabilityProvider<>(CapabilityRegistry.STORY_CAPABILITY, StorySaveCapability::new));
            event.addCapability(new ResourceLocation(Utils.MOD_ID,"health_system"),new AbsCapabilityProvider<>(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY, HealthSystemCapability::new));
            event.addCapability(new ResourceLocation(Utils.MOD_ID,"rdi_account_auth"),new AbsCapabilityProvider<>(CapabilityRegistry.RDI_ACCOUNT_AUTH_CAPABILITY, RDIAccountAuthCapability::new));
            event.addCapability(new ResourceLocation(Utils.MOD_ID,"rdi_depot"),new AbsCapabilityProvider<>(CapabilityRegistry.RDI_DEPOT_CAPABILITY, RDIDepotCapability::new));
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        CapabilityHelper.playerCloneDefaultProgress(CapabilityRegistry.STORY_CAPABILITY,event,true);
        CapabilityHelper.playerCloneDefaultProgress(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY,event,true);
        CapabilityHelper.playerCloneDefaultProgress(CapabilityRegistry.RDI_ACCOUNT_AUTH_CAPABILITY,event,true);
        CapabilityHelper.playerCloneDefaultProgress(CapabilityRegistry.RDI_DEPOT_CAPABILITY,event,true);
    }

    @SubscribeEvent//玩家进入时的数据同步
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event){
        if(!event.getEntity().level().isClientSide){
            event.getEntity().getCapability(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY).ifPresent((i)->i.sendToClient((ServerPlayer) event.getEntity()));
            event.getEntity().getCapability(CapabilityRegistry.RDI_ACCOUNT_AUTH_CAPABILITY).ifPresent((i)->i.sendToClient((ServerPlayer) event.getEntity()));
            event.getEntity().getCapability(CapabilityRegistry.RDI_DEPOT_CAPABILITY).ifPresent((i)->i.sendToClient((ServerPlayer) event.getEntity()));
        }
    }

//    @SubscribeEvent
//    public static void StoryCapabilityRegistry(FMLCommonSetupEvent event){
//        event.enqueueWork(()-> CapabilityManager.INSTANCE.register(
//                IStorySaveCapability.class,
//                new Capability.IStorage<IStorySaveCapability>() {
//                    public INBT writeNBT(Capability<IStorySaveCapability> capability, IStorySaveCapability instance, Direction side) {
//                        return null;
//                    }
//                    public void readNBT(Capability<IStorySaveCapability> capability, IStorySaveCapability instance, Direction side, INBT nbt) {
//                    }
//                },
//                ()->null
//        ));
//
//        event.enqueueWork(()-> CapabilityManager.INSTANCE.register(
//                IHealthSystemCapability.class,
//                new Capability.IStorage<IHealthSystemCapability>() {
//                    public INBT writeNBT(Capability<IHealthSystemCapability> capability, IHealthSystemCapability instance, Direction side) {
//                        return null;
//                    }
//                    public void readNBT(Capability<IHealthSystemCapability> capability, IHealthSystemCapability instance, Direction side, INBT nbt) {
//                    }
//                },
//                ()->null
//        ));
//
//        event.enqueueWork(registerDefaultCap(IRDIAccountAuthCapability.class));
//        event.enqueueWork(registerDefaultCap(IRDIDepotCapability.class));
//    }
//
//    public static <T> Runnable registerDefaultCap(Class<T> tClass){
//        return ()-> CapabilityManager.INSTANCE.register(
//                tClass,
//                new Capability.IStorage<T>() {
//                    public INBT writeNBT(Capability<T> capability, T instance, Direction side) {
//                        return null;
//                    }
//                    public void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt) {
//                    }
//                },
//                ()->null
//        );
//    }
}
