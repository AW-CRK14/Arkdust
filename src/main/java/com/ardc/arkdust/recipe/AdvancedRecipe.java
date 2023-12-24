package com.ardc.arkdust.recipe;

import com.ardc.arkdust.block_entity.OERIMachineBE;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public abstract class AdvancedRecipe<T extends Container> implements Recipe<T> {
    public AdvancedRecipe(ResourceLocation recipeId,int time,int energy_cost){

    }
}
