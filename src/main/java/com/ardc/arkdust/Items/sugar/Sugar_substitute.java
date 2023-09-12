package com.ardc.arkdust.Items.sugar;

import com.ardc.arkdust.registry.ModGroupRegistry;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

public class Sugar_substitute extends Item {
    private static final Food food = (new Food.Builder())
            .nutrition(1)
            .build();
    public Sugar_substitute() {
        super(new Properties().food(food).tab(ModGroupRegistry.TERRA_WORLD_COMMON_MATERIAL));//记住此处要添加food(food)
    }
}
