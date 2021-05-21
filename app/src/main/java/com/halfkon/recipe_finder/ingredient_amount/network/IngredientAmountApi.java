package com.halfkon.recipe_finder.ingredient_amount.network;

import com.halfkon.recipe_finder.ingredient_amount.model.IngredientAmount;
import com.squareup.moshi.Json;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IngredientAmountApi {
    class Ingredients {
        @Json(name = "ingredients")
        public List<IngredientAmount> data;
    }

    @GET("/recipes/{recipe_id}/ingredientWidget.json")
    Call<Ingredients> getIngredientsAmount(@Path(value = "recipe_id", encoded = true) Integer recipeId);
}
