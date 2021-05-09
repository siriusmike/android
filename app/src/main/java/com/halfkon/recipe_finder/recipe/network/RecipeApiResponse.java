package com.halfkon.recipe_finder.recipe.network;

import com.halfkon.recipe_finder.recipe.model.Recipe;

import java.util.List;

public class RecipeApiResponse {
    private final List<Recipe> mRecipes;
    private final Throwable mError;

    RecipeApiResponse(List<Recipe> recipes) {
        mRecipes = recipes;
        mError = null;
    }

    RecipeApiResponse(Throwable error) {
        mError = error;
        mRecipes = null;
    }

    public List<Recipe> getRecipes() {
        return mRecipes;
    }

    public Throwable getError() {
        return mError;
    }
}