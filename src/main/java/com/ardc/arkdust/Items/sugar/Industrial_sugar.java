package com.ardc.arkdust.Items.sugar;

import com.ardc.arkdust.registry.ItemRegistry;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class Industrial_sugar extends Item {
    private static final FoodProperties food = (new FoodProperties.Builder())
            .saturationMod(2)
            .nutrition(3)
            .build();

    public Industrial_sugar() {
        super(new Properties().food(food).rarity(ItemRegistry.Rar.SPECIAL));
    }
}
