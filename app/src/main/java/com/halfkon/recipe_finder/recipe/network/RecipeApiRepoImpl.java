package com.halfkon.recipe_finder.recipe.network;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.halfkon.recipe_finder.recipe.model.Recipe;

import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RecipeApiRepoImpl implements RecipeApiRepo {
    private static final String HOST = "okto.pw";
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
    public LiveData<RecipeApiResponse> getRecipesByIngredients(List<String> ingredients){
        final MutableLiveData<RecipeApiResponse> liveData = new MutableLiveData<>();

        String query = TextUtils.join(", ", ingredients);
        Call<RecipeApi.Recipes> call = mRecipeApi.getRecipesByIngredients(query);

        call.enqueue(new Callback<RecipeApi.Recipes>() {
            @Override
            public void onResponse(@NonNull Call<RecipeApi.Recipes> call,
                                   @NonNull Response<RecipeApi.Recipes> response) {
                if (response.body() == null) {
                    liveData.setValue(new RecipeApiResponse(new Exception("Empty resp body")));
                } else {
                    liveData.setValue(
                            new RecipeApiResponse(response.body().data));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeApi.Recipes> call, @NonNull Throwable t) {
                liveData.setValue(new RecipeApiResponse(t));
            }
        });
        return liveData;
    }

    @Override
    public LiveData<RecipeApiResponse> searchRecipes(String query) {
        final MutableLiveData<RecipeApiResponse> liveData = new MutableLiveData<>();

        Call<RecipeApi.Results> call = mRecipeApi.SearchRecipes(query);

        call.enqueue(new Callback<RecipeApi.Results>() {
            @Override
            public void onResponse(@NonNull Call<RecipeApi.Results> call,
                                   @NonNull Response<RecipeApi.Results> response) {
                if (response.body() == null) {
                    liveData.setValue(new RecipeApiResponse(new Exception("Empty resp body")));
                } else {
                    liveData.setValue(
                            new RecipeApiResponse(response.body().data));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeApi.Results> call, @NonNull Throwable t) {
                liveData.setValue(new RecipeApiResponse(t));
            }
        });
        return liveData;
    }

    @Override
    public LiveData<RecipeApiResponse> getRecipesBulk(List<String> ids) {
        final MutableLiveData<RecipeApiResponse> liveData = new MutableLiveData<>();

        String query = TextUtils.join(",", ids);
        Call<List<Recipe>> call = mRecipeApi.getRecipesBulk(query);

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call,
                                   @NonNull Response<List<Recipe>> response) {
                if (response.body() == null) {
                    liveData.setValue(new RecipeApiResponse(new Exception("Empty resp body")));
                } else {
                    liveData.setValue(
                            new RecipeApiResponse(response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                liveData.setValue(new RecipeApiResponse(t));
            }
        });
        return liveData;
    }


    @Override
    public LiveData<RecipeApiResponse> getRandomRecipes(Integer count){
        final MutableLiveData<RecipeApiResponse> liveData = new MutableLiveData<>();

        Call<RecipeApi.Recipes> call = mRecipeApi.getRandomRecipes(count);

        call.enqueue(new Callback<RecipeApi.Recipes>() {
            @Override
            public void onResponse(@NonNull Call<RecipeApi.Recipes> call,
                                   @NonNull Response<RecipeApi.Recipes> response) {
                if (response.body() == null) {
                    liveData.setValue(new RecipeApiResponse(new Exception("Empty resp body")));
                } else {
                    liveData.setValue(
                            new RecipeApiResponse(response.body().data));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeApi.Recipes> call, @NonNull Throwable t) {
                liveData.setValue(new RecipeApiResponse(t));
            }
        });
        return liveData;
    }
}

