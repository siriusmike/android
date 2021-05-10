package com.halfkon.recipe_finder.recipe.network;

import com.halfkon.recipe_finder.ingredient.model.Ingredient;
import com.halfkon.recipe_finder.recipe.model.Recipe;
import com.squareup.moshi.Json;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApi {
    class Recipes {
        @Json(name = "recipes")
        public List<Recipe> data;
    }

    @GET("/recipes/findByIngredients")
    Call<Recipes> getRecipes(@Query("query") String query);

    @GET("/recipes/random")
    Call<Recipes> getRandomRecipes(@Query("number") int number);

}
