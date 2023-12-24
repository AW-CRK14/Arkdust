package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.recipe.oeri.OERIRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeTypeRegistry {
    public static final DeferredRegister<RecipeType<?>> RECIPE = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Utils.MOD_ID);

    public static final RegistryObject<RecipeType<OERIRecipe>> OERI = registry("oeri");

    private static <T extends Recipe<?>> RegistryObject<RecipeType<T>> registry(String name){
        return RECIPE.register(name,()->RecipeType.simple(new ResourceLocation(Utils.MOD_ID,name)));
    }
}
