package com.ardc.arkdust.Items.items.sugar;

import com.ardc.arkdust.registry.ModGroupRegistry;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class Sugar_lump extends Item {
    private static final Food food = (new Food.Builder())
            .saturationMod(34)//回复34点饱和度
            .nutrition(42)//回复42点饥饿值
            .effect(()->new EffectInstance(Effects.HEAL,60,1),0.5F)
            .effect(()->new EffectInstance(Effects.POISON,120,3), 0.7F)
            .fast()//加快进食速度，自定义时间方法不明
            .alwaysEat()//在饱腹时也可以食用
            .build();
    public Sugar_lump() {
        super(new Properties().food(food).tab(ModGroupRegistry.terraCommonMaterial));
    }
}
