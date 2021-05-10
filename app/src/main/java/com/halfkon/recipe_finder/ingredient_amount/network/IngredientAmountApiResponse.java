package com.halfkon.recipe_finder.ingredient_amount.network;


import com.halfkon.recipe_finder.ingredient_amount.model.IngredientAmount;

import java.util.List;

public class IngredientAmountApiResponse {
    private final List<IngredientAmount> mIngredients;
    private final Throwable mError;

    IngredientAmountApiResponse(List<IngredientAmount> ingredients) {
        mIngredients = ingredients;
        mError = null;
    }

    IngredientAmountApiResponse(Throwable error) {
        mError = error;
        mIngredients = null;
    }

    public List<IngredientAmount> getIngredients() {
        return mIngredients;
    }

    public Throwable getError() {
        return mError;
    }

}
