package com.ardc.arkdust.Items.sugar;

import com.ardc.arkdust.registry.ItemRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class SugarLump extends Item {
    private static final FoodProperties food = (new FoodProperties.Builder())
            .saturationMod(34)
            .nutrition(42)
            .effect(()->new MobEffectInstance(MobEffects.HEAL,60,1),0.5F)
            .effect(()->new MobEffectInstance(MobEffects.POISON,120,3), 0.7F)
            .fast()
            .alwaysEat()
            .build();
    public SugarLump() {
        super(new Properties().food(food).rarity(ItemRegistry.Rar.TREASURE));
    }
}
