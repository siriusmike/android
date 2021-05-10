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

    class Results {
        @Json(name = "results")
        public List<Recipe> data;
    }

    @GET("/recipes/findByIngredients")
    Call<Recipes> getRecipesByIngredients(@Query("query") String query);

    @GET("/recipes/complexSearch?instructionsRequired=True")
    Call<Results> SearchRecipes(@Query("query") String query);

    @GET("/recipes/random")
    Call<Recipes> getRandomRecipes(@Query("number") int number);

    @GET("/recipes/informationBulk")
    Call<List<Recipe>> getRecipesBulk(@Query("ids") String query);
}
