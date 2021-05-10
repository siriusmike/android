package com.halfkon.recipe_finder.recipe.network;

import androidx.lifecycle.LiveData;

import java.util.List;


public interface RecipeApiRepo {
    LiveData<RecipeApiResponse> getRecipesByIngredients(List<String> ingredients);
    LiveData<RecipeApiResponse> searchRecipes(String query);
    LiveData<RecipeApiResponse> getRandomRecipes(Integer count);
    LiveData<RecipeApiResponse> getRecipesBulk(List<String> ids);
}
