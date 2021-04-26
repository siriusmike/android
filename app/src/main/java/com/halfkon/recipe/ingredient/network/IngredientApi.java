package com.halfkon.recipe.ingredient.network;

import com.halfkon.recipe.ingredient.model.Ingredient;
import com.squareup.moshi.Json;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IngredientApi {
    class Ingredients {
        @Json(name = "results")
        public List<Ingredient> results;

        @Json(name = "number")
        public Integer number;
    }

    @GET("/food/ingredients/search")
    Call<Ingredients> getIngredients(@Query("query") String query);
}
