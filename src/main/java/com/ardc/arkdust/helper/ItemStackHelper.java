package com.ardc.arkdust.helper;

import com.ardc.arkdust.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ItemStackHelper {
    public static final Logger LOGGER = LogManager.getLogger(Utils.getLogName("Helper:ItemStack"));

//    public static boolean checkInRecipe(List<ItemStack> stack, List<ItemStack> recipe){
//        if(!(stack.size() == recipe.size())) return false;
////        stack = ListAndMapHelper.copyList(stack);
////        recipe = ListAndMapHelper.copyList(recipe);
//        List<String> stackNames = fromStackList(stack);
//        List<String> recipeNames = fromStackList(recipe);
//        Collections.sort(stackNames);
//        Collections.sort(recipeNames);
//        for(int i = 0 ; i < stack.size() ; i++){
//            if(!stackNames.get(i).equals(recipeNames.get(i))) return false;
//
//        }
//    }

    public static List<String> fromStackList(List<ItemStack> stack){
        return stack.stream().map(itemStack -> BuiltInRegistries.ITEM.getKey(itemStack.getItem()).toString()).collect(Collectors.toList());
    }

    public static ItemStack checkInIngredient(Item it,Ingredient in){
        for (ItemStack stack : in.getItems()){
            if(stack.is(it)) return stack;
        }
        return ItemStack.EMPTY;
    }

    public static int recipeProduceCount(List<ItemStack> resortedInput, NonNullList<Ingredient> recipe){
        if(resortedInput.size() != recipe.size()) return -1;
        int maxCount = Integer.MAX_VALUE;
        for(int i = 0 ; i < recipe.size() ; i++){
            Ingredient ingredient = recipe.get(i);
            int rc = ingredient.getItems().length == 1 ? ingredient.getItems()[1].getCount() : checkInIngredient(resortedInput.get(i).getItem(),ingredient).getCount();
            maxCount = Math.min(maxCount,resortedInput.get(i).getCount()/rc);
            if(maxCount <= 0) return 0;
        }
        return maxCount;
    }

    public static NonNullList<Ingredient> itemsFromJson(JsonArray jsonArray) {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();

        for(JsonElement element : jsonArray) {
            nonnulllist.add(Ingredient.fromJson(element, false));
        }

        return nonnulllist;
    }
}
