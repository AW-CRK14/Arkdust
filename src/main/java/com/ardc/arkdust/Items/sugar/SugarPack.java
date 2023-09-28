package com.ardc.arkdust.Items.sugar;

import com.ardc.arkdust.registry.ItemRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class SugarPack extends Item {
    private static final FoodProperties food = new FoodProperties.Builder()
            .saturationMod(9)
            .nutrition(11)
            .effect(()->new MobEffectInstance(MobEffects.POISON,40,3), 0.3F)
            .build();
    public SugarPack() {
        super(new Properties().food(food).rarity(ItemRegistry.Rar.VALUABLE));
    }
}
