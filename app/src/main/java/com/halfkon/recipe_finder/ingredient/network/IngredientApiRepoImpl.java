package com.halfkon.recipe_finder.ingredient.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.halfkon.recipe_finder.ingredient.model.Ingredient;
import com.halfkon.recipe_finder.ingredient_amount.network.IngredientAmountApiResponse;

import java.util.List;
import java.util.Objects;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class IngredientApiRepoImpl implements com.halfkon.recipe_finder.ingredient.network.IngredientApiRepo {
    private static final String HOST = "okto.pw";
    private final IngredientApi mIngredientApi;

    public IngredientApiRepoImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(new HttpUrl.Builder()
                        .scheme("https")
                        .host(HOST)
                        .build())
                .build();

        mIngredientApi = retrofit.create(IngredientApi.class);
    }

    @Override
    public LiveData<IngredientApiResponse> autocompleteIngredients(String query) {
        final MutableLiveData<com.halfkon.recipe_finder.ingredient.network.IngredientApiResponse> liveData = new MutableLiveData<>();
        Call<List<Ingredient>> call = mIngredientApi.autocompleteIngredients(query);
        call.enqueue(new Callback<List<Ingredient>>() {
            @Override
            public void onResponse(@NonNull Call<List<Ingredient>> call,
                                   @NonNull Response<List<Ingredient>> response) {
                if (response.body() == null) {
                    liveData.setValue(new IngredientApiResponse(new Exception("Empty response body")));
                } else {
                    liveData.setValue(
                            new IngredientApiResponse(response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Ingredient>> call,
                                  @NonNull Throwable t) {
                liveData.setValue(new IngredientApiResponse(t));
            }
        });
        return liveData;
    }
}

