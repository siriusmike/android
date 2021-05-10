package com.halfkon.recipe_finder.ingredient_amount.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.halfkon.recipe_finder.ingredient_amount.model.IngredientAmount;

import java.util.List;
import java.util.Objects;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class IngredientAmountApiRepoImpl implements IngredientAmountApiRepo {
    private static final String HOST = "okto.pw";
    private final IngredientAmountApi mIngredientAmountApi;

    public IngredientAmountApiRepoImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(new HttpUrl.Builder()
                        .scheme("https")
                        .host(HOST)
                        .build())
                .build();

        mIngredientAmountApi = retrofit.create(IngredientAmountApi.class);
    }

    @Override
    public LiveData<IngredientAmountApiResponse> getIngredientsAmount(Integer recipeId) {
        final MutableLiveData<IngredientAmountApiResponse> liveData = new MutableLiveData<>();
        Call<IngredientAmountApi.Ingredients> call = mIngredientAmountApi.getIngredientsAmount(recipeId);
        call.enqueue(new Callback<IngredientAmountApi.Ingredients>() {
            @Override
            public void onResponse(@NonNull Call<IngredientAmountApi.Ingredients> call,
                                   @NonNull Response<IngredientAmountApi.Ingredients> response) {
                liveData.setValue(
                        new IngredientAmountApiResponse(Objects.requireNonNull(response.body().data)));
            }

            @Override
            public void onFailure(@NonNull Call<IngredientAmountApi.Ingredients> call,
                                  @NonNull Throwable t) {
                liveData.setValue(new IngredientAmountApiResponse(t));
            }
        });
        return liveData;
    }
}

