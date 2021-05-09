package com.halfkon.recipe_finder.ingredient.network;

import androidx.lifecycle.LiveData;

public interface IngredientApiRepo {
    LiveData<IngredientApiResponse> getIngredients(String query);
}
