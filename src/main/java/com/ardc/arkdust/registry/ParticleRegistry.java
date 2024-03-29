package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.particle.environment.FlyingGravelParticle;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Utils.MOD_ID);
    public static final RegistryObject<ParticleType<FlyingGravelParticle.Type>> FLYING_GRAVEL = PARTICLE.register("flying_gravel", FlyingGravelParticle.Type::new);
}
