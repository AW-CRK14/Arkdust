package com.ardc.arkdust.Items.items.sugar;

import com.ardc.arkdust.registry.ModGroupRegistry;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class Sugar_lump extends Item {
    private static final Food food = (new Food.Builder())
            .saturationMod(34)
            .nutrition(42)
            .effect(()->new EffectInstance(Effects.HEAL,60,1),0.5F)
            .effect(()->new EffectInstance(Effects.POISON,120,3), 0.7F)
            .fast()
            .alwaysEat()
            .build();
    public Sugar_lump() {
        super(new Properties().food(food).tab(ModGroupRegistry.TERRA_WORLD_COMMON_MATERIAL));
    }
}
