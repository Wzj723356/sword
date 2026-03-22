package net.mcreator.sword.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class AlchemyRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final int cookingTime;
    private final int requiredRealm;

    public AlchemyRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, ItemStack result, int cookingTime, int requiredRealm) {
        this.id = id;
        this.ingredients = ingredients;
        this.result = result;
        this.cookingTime = cookingTime;
        this.requiredRealm = requiredRealm;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if (level.isClientSide()) return false;
        
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            boolean found = false;
            for (int j = 0; j < Math.min(container.getContainerSize(), 5); j++) {
                if (ingredient.test(container.getItem(j))) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return result.copy();
    }

    public ItemStack getResultItem() {
        return result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public int getRequiredRealm() {
        return requiredRealm;
    }

    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    public static class Type implements RecipeType<AlchemyRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "alchemy";
    }

    public static class Serializer implements RecipeSerializer<AlchemyRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation("sword", "alchemy");

        @Override
        public AlchemyRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            JsonArray ingredientsArray = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> ingredients = NonNullList.create();
            
            for (int i = 0; i < ingredientsArray.size(); i++) {
                Ingredient ingredient = Ingredient.fromJson(ingredientsArray.get(i));
                if (!ingredient.isEmpty()) {
                    ingredients.add(ingredient);
                }
            }
            
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for alchemy recipe");
            }
            
            JsonObject resultJson = GsonHelper.getAsJsonObject(json, "result");
            String itemId = GsonHelper.getAsString(resultJson, "item");
            int count = GsonHelper.getAsInt(resultJson, "count", 1);
            ItemStack result = new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(new ResourceLocation(itemId)), count);
            
            int cookingTime = GsonHelper.getAsInt(json, "cookingtime", 200);
            int requiredRealm = GsonHelper.getAsInt(json, "required_realm", 0);
            
            return new AlchemyRecipe(recipeId, ingredients, result, cookingTime, requiredRealm);
        }

        @Nullable
        @Override
        public AlchemyRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int ingredientSize = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientSize, Ingredient.EMPTY);
            
            for (int i = 0; i < ingredientSize; i++) {
                ingredients.set(i, Ingredient.fromNetwork(buffer));
            }
            
            ItemStack result = buffer.readItem();
            int cookingTime = buffer.readVarInt();
            int requiredRealm = buffer.readVarInt();
            
            return new AlchemyRecipe(recipeId, ingredients, result, cookingTime, requiredRealm);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AlchemyRecipe recipe) {
            buffer.writeVarInt(recipe.ingredients.size());
            
            for (Ingredient ingredient : recipe.ingredients) {
                ingredient.toNetwork(buffer);
            }
            
            buffer.writeItem(recipe.result);
            buffer.writeVarInt(recipe.cookingTime);
            buffer.writeVarInt(recipe.requiredRealm);
        }
    }
}
