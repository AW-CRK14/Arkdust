package com.ardc.arkdust.Items.items.sugar;

import com.ardc.arkdust.ModGroupRegistry;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

public class Industrial_sugar extends Item {
    private static final Food food = (new Food.Builder())
            .saturationMod(2)
            .nutrition(3)
            .build();
    public Industrial_sugar() {
        super(new Properties().food(food).tab(ModGroupRegistry.terraCommonMaterial));
    }
}
