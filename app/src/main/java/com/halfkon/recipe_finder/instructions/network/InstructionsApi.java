package com.halfkon.recipe_finder.instructions.network;

import com.halfkon.recipe_finder.instructions.model.Instructions;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InstructionsApi {
    @GET("/recipes/{recipe_id}/analyzedInstructions?stepBreakdown=False")
    Call<List<Instructions>> getInstructions(@Path(value = "recipe_id", encoded = true) Integer recipeId);
}
