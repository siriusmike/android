package com.halfkon.recipe_finder.recipe.network;

import com.halfkon.recipe_finder.recipe.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApi {
    @GET("/recipes/findByIngredients")
    Call<List<Recipe>> getRecipes(@Query("query") String query);
}
