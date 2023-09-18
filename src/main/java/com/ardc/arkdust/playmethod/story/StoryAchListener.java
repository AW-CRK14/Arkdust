package com.ardc.arkdust.playmethod.story;

import com.ardc.arkdust.capability.story.IStorySaveCapability;
import com.ardc.arkdust.registry.CapabilityRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class StoryAchListener<T extends Event> {
    public final Class<T> clazz;
    public final Call<T> call;

    public StoryAchListener(Class<T> clazz, Call<T> call) {
        this.clazz = clazz;
        this.call = call;
    }

    public static class Pre{
        public static StoryAchListener<PlayerEvent.ItemPickupEvent> itemPickup(ResourceLocation regName){
            return new StoryAchListener<>(PlayerEvent.ItemPickupEvent.class,(event -> event.getStack().getItem().getRegistryName().equals(regName)));
        }

        public static StoryAchListener<PlayerEvent.ItemPickupEvent> itemPickup(Call<PlayerEvent.ItemPickupEvent> call){
            return new StoryAchListener<>(PlayerEvent.ItemPickupEvent.class,call);
        }
    }

    public interface Call<V extends Event>{

        default boolean preCall(Event event){
            return call((V) event);
        }
        boolean call(V event);
    }

    public static class AdvStoryAchieveListener<T extends Event> {

        public final int stage;
        public final Story story;
        public final StoryAchListener<T> listener;
        public AdvStoryAchieveListener(int stage, Story story, StoryAchListener<T> listener) {
            this.stage = stage;
            this.story = story;
            this.listener = listener;
        }

    }

    @Mod.EventBusSubscriber
    private static class Listener{
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void onLoggedIn(PlayerEvent.PlayerLoggedInEvent event){
            event.getPlayer().getCapability(CapabilityRegistry.STORY_CAPABILITY).ifPresent(IStorySaveCapability::buildListener);
        }

        @SubscribeEvent
        public static void onPickUpItem(PlayerEvent.ItemPickupEvent event){
            event.getPlayer().getCapability(CapabilityRegistry.STORY_CAPABILITY).ifPresent((i)->i.pushEvent(event,event.getPlayer()));
        }
    }
}
