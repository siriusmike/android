package com.halfkon.recipe_finder.ingredient.network;

import com.halfkon.recipe_finder.ingredient.model.Ingredient;
import com.halfkon.recipe_finder.instructions.model.Instructions;
import com.squareup.moshi.Json;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IngredientApi {
    @GET("/food/ingredients/autocomplete")
    Call<List<Ingredient>> autocompleteIngredients(@Query("query") String query);
}
