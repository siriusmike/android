package com.halfkon.recipe_finder.recipe.network;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.halfkon.recipe_finder.recipe.model.Recipe;

import java.util.List;
import java.util.Objects;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RecipeApiRepoImpl implements RecipeApiRepo {
    private static final String HOST = "api.spoonacular.com";
    private final RecipeApi mRecipeApi;

    public RecipeApiRepoImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(new HttpUrl.Builder()
                        .scheme("https")
                        .host(HOST)
                        .build())
                .build();

        mRecipeApi = retrofit.create(RecipeApi.class);
    }

    @Override
    public LiveData<RecipeApiResponse> getRecipes(String[] ingredients){
        final MutableLiveData<RecipeApiResponse> liveData = new MutableLiveData<>();

        String query = TextUtils.join(", ", ingredients);
        Call<List<Recipe>> call = mRecipeApi.getRecipes(query);

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call,
                                   @NonNull Response<List<Recipe>> response) {
                liveData.setValue(new RecipeApiResponse(Objects.requireNonNull(response.body())));
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                liveData.setValue(new RecipeApiResponse(t));
            }
        });
        return liveData;
    }
}

