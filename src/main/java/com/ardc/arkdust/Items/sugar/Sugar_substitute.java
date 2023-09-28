package com.ardc.arkdust.Items.sugar;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class Sugar_substitute extends Item {
    private static final FoodProperties food = (new FoodProperties.Builder())
            .nutrition(1)
            .build();
    public Sugar_substitute() {
        super(new Properties().food(food));//记住此处要添加food(food)
    }
}
