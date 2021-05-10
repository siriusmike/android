package com.halfkon.recipe_finder.recipe.network;

import androidx.lifecycle.LiveData;

public interface RecipeApiRepo {
    LiveData<RecipeApiResponse> getRecipes(String[] ingredients);
    LiveData<RecipeApiResponse> getRandomRecipes();
}
