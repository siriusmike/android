package com.halfkon.recipe.ingredient.network;

import androidx.lifecycle.LiveData;

public interface IngredientApiRepo {
    LiveData<IngredientApiResponse> getIngredients(String query);
}
