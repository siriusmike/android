package com.halfkon.recipe.ingredient.network;

import com.halfkon.recipe.ingredient.model.Ingredient;

import java.util.List;

public class IngredientApiResponse {
    private List<Ingredient> mIngredients;
    private Throwable mError;

    IngredientApiResponse(List<Ingredient> ingredients) {
        mIngredients = ingredients;
        mError = null;
    }

    IngredientApiResponse(Throwable error) {
        mError = error;
        mIngredients = null;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public Throwable getError() {
        return mError;
    }

}
