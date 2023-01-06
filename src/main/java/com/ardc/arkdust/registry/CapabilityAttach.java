package com.ardc.arkdust.registry;

import com.ardc.arkdust.CodeMigration.RunHelper.CapabilityHelper;
import com.ardc.arkdust.Utils;
import com.ardc.arkdust.playmethod.health_system.HealthSystemCapabilityProvider;
import com.ardc.arkdust.playmethod.rdi_auth.RDIAccountAuthCapabilityProvider;
import com.ardc.arkdust.playmethod.story.StoryCapabilityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CapabilityAttach {
    @SubscribeEvent
    public static void onCapabilityAttach(AttachCapabilitiesEvent<Entity> event){
        Entity entity = event.getObject();
        if (entity instanceof PlayerEntity){
            event.addCapability(new ResourceLocation(Utils.MOD_ID,"story"),new StoryCapabilityProvider());
            event.addCapability(new ResourceLocation(Utils.MOD_ID,"health_system"),new HealthSystemCapabilityProvider());
            event.addCapability(new ResourceLocation(Utils.MOD_ID,"rdi_account_auth"),new RDIAccountAuthCapabilityProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        CapabilityHelper.playerCloneDefaultProgress(CapabilityRegistry.STORY_CAPABILITY,event);
        CapabilityHelper.playerCloneDefaultProgress(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY,event);
        CapabilityHelper.playerCloneDefaultProgress(CapabilityRegistry.RDI_ACCOUNT_AUTH_CAPABILITY,event);
    }

    @SubscribeEvent//玩家进入时的数据同步
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event){
        if(!event.getPlayer().level.isClientSide){
            event.getPlayer().getCapability(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY).ifPresent((i)->i.sendPackToClient((ServerPlayerEntity) event.getPlayer()));
            event.getPlayer().getCapability(CapabilityRegistry.RDI_ACCOUNT_AUTH_CAPABILITY).ifPresent((i)->i.sendPackToClient((ServerPlayerEntity) event.getPlayer()));
        }
    }
}
