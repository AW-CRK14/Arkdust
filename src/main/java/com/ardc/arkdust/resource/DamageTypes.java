package com.ardc.arkdust.resource;

import com.ardc.arkdust.Utils;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class DamageTypes {
    public static final ResourceKey<DamageType> ORIROCK_DEATH = create("orirock_death");
    public static final ResourceKey<DamageType> ORIROCK_INFECTION = create("orirock_infection");

    public static ResourceKey<DamageType> create(String name){
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Utils.MOD_ID,name));
    }

    public static Holder<DamageType> createDamageSource(RegistryAccess access, ResourceKey<DamageType> typesResourceKey){
        return access.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(typesResourceKey);
    }

    public static Holder<DamageType> createDamageSource(Entity player, ResourceKey<DamageType> typesResourceKey){
        return player.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(typesResourceKey);
    }

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(ORIROCK_DEATH, new DamageType("orirock_death", 0.1F, DamageEffects.DROWNING));
        context.register(ORIROCK_INFECTION, new DamageType("orirock_infection", 0.3F, DamageEffects.DROWNING));
    }
}
