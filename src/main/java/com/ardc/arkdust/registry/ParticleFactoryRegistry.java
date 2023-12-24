package com.ardc.arkdust.registry;

import com.ardc.arkdust.particle.environment.FlyingGravelParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleFactoryRegistry {

    @SubscribeEvent
    public static void onParticleFactoryRegistry(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(ParticleRegistry.FLYING_GRAVEL.get(), FlyingGravelParticle.Factory::new);
    }
}
