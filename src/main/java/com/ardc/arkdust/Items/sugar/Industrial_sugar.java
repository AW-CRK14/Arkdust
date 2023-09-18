package com.ardc.arkdust.Items.sugar;

import com.ardc.arkdust.registry.ItemRegistry;
import com.ardc.arkdust.registry.ModGroupRegistry;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

public class Industrial_sugar extends Item{
    private static final Food food = (new Food.Builder())
            .saturationMod(2)
            .nutrition(3)
            .build();

    public Industrial_sugar() {
        super(new Properties().food(food).tab(ModGroupRegistry.TERRA_WORLD_COMMON_MATERIAL).rarity(ItemRegistry.Rar.SPECIAL));
    }
}
