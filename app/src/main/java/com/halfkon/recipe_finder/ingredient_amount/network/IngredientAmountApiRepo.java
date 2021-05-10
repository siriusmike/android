package com.halfkon.recipe_finder.ingredient_amount.network;

import androidx.lifecycle.LiveData;


public interface IngredientAmountApiRepo {
    LiveData<IngredientAmountApiResponse> getIngredientsAmount(Integer recipeId);

}
