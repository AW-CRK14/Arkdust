package com.ardc.arkdust.recipe;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.recipe.oeri.OERIRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSerializerRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Utils.MOD_ID);
    public static final RegistryObject<RecipeSerializer<OERIRecipe>> OERI = RECIPE.register("oeri", OERIRecipe.Serializer::new);
}
