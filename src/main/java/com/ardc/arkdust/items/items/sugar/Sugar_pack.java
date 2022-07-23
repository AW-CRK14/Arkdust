package com.ardc.arkdust.items.items.sugar;

import com.ardc.arkdust.ModGroupRegistry;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class Sugar_pack extends Item {
    private static final Food food = (new Food.Builder())
            .saturationMod(9)
            .nutrition(11)
            .effect(()->new EffectInstance(Effects.POISON,40,3), 0.3F)
            .build();
    public Sugar_pack() {
        super(new Properties().food(food).tab(ModGroupRegistry.terraCommonMaterial));
    }
}
