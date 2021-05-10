package com.halfkon.recipe_finder.ingredient.network;

import androidx.lifecycle.LiveData;

public interface IngredientApiRepo {
    LiveData<com.halfkon.recipe_finder.ingredient.network.IngredientApiResponse> autocompleteIngredients(String query);

}
