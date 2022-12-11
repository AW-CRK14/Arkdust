package com.ardc.arkdust.registry;

import com.ardc.arkdust.CodeMigration.RunHelper.CapabilityHelper;
import com.ardc.arkdust.Utils;
import com.ardc.arkdust.playmethod.story.StoryCapabilityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class CapabilityAttach {
    @SubscribeEvent
    public static void onCapabilityAttach(AttachCapabilitiesEvent<Entity> event){
        Entity entity = event.getObject();
        if (entity instanceof PlayerEntity){
            event.addCapability(new ResourceLocation(Utils.MOD_ID,"story"),new StoryCapabilityProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        CapabilityHelper.playerCloneDefaultProgress(CapabilityRegistry.STORY_CAPABILITY,event);
    }
}
