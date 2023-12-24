package com.ardc.arkdust.recipe.oeri;

import com.ardc.arkdust.block_entity.OERIMachineBE;
import com.ardc.arkdust.enums.SlotType;
import com.ardc.arkdust.helper.ItemStackHelper;
import com.ardc.arkdust.helper.ListAndMapHelper;
import com.ardc.arkdust.recipe.RecipeSerializerRegistry;
import com.ardc.arkdust.registry.RecipeTypeRegistry;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.List;

public class OERIRecipe implements Recipe<OERIMachineBE> {

    public final boolean isSimple;
    public final ResourceLocation recipeId;
    public final int cost_per_tick,time;
    public final NonNullList<Ingredient> input;
    public final ItemStack output;


    public OERIRecipe(ResourceLocation recipeId, int cost_per_tick, int time, NonNullList<Ingredient> input , ItemStack output){
        this.recipeId = recipeId;
        this.cost_per_tick = cost_per_tick;
        this.time = time;
        this.input = input;
        this.output = output;
        this.isSimple = input.stream().allMatch(Ingredient::isSimple);
    }


    @Override
    public boolean matches(OERIMachineBE be, Level level) {
        int[] match = RecipeMatcher.findMatches(be.getStacksForType(SlotType.INPUT),input);
        if(match == null) return false;
        List<ItemStack> items = ListAndMapHelper.resort(be.getStacksForType(SlotType.INPUT),match);
        return ItemStackHelper.recipeProduceCount(items,input) != 0;
    }

    @Override
    public ItemStack assemble(OERIMachineBE p_44001_, RegistryAccess p_267165_) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return x >= 7;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public net.minecraft.world.item.crafting.RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.OERI.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypeRegistry.OERI.get();
    }


    public static class Serializer implements net.minecraft.world.item.crafting.RecipeSerializer<OERIRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("arkdust", "oeri");
        public OERIRecipe fromJson(ResourceLocation id, JsonObject json) {
            NonNullList<Ingredient> input = ItemStackHelper.itemsFromJson(GsonHelper.getAsJsonArray(json, "inputs"));
            if (input.isEmpty() || input.size() > 7) {
                throw new JsonParseException("Illegal ingredients for oeri recipe");
            } else {
                ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json,"output"), true, true);
                int time = GsonHelper.getAsInt(json,"time");
                int energy_cost = GsonHelper.getAsInt(json,"energy_cost");
                return new OERIRecipe(id,energy_cost,time,input,output);
            }
        }

        public OERIRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf byteBuf) {
            int i = byteBuf.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);
            nonnulllist.replaceAll(ignored -> Ingredient.fromNetwork(byteBuf));
            ItemStack itemstack = byteBuf.readItem();
            return new OERIRecipe(id, byteBuf.readInt(), byteBuf.readInt(), nonnulllist, itemstack);
        }

        public void toNetwork(FriendlyByteBuf byteBuf, OERIRecipe recipe) {
            for(Ingredient ingredient : recipe.input) {
                ingredient.toNetwork(byteBuf);
            }
            byteBuf.writeItem(recipe.output);
            byteBuf.writeInt(recipe.time);
            byteBuf.writeInt(recipe.cost_per_tick);
        }
    }
}
