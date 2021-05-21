package com.halfkon.recipe_finder.recipe.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.halfkon.recipe_finder.recipe.network.RecipeApiRepo;
import com.halfkon.recipe_finder.recipe.network.RecipeApiRepoImpl;
import com.halfkon.recipe_finder.recipe.network.RecipeApiResponse;

import java.util.List;

public class RecipeViewModel extends ViewModel {
    private final MediatorLiveData<RecipeApiResponse> mRecipeApiResponse;
    private final RecipeApiRepo mRecipeApiRepo;

    public RecipeViewModel() {
        mRecipeApiResponse = new MediatorLiveData<>();
        mRecipeApiRepo = new RecipeApiRepoImpl();
    }

    @NonNull
    public LiveData<RecipeApiResponse> getApiResponse() {
        return mRecipeApiResponse;
    }

    public LiveData<RecipeApiResponse> getRecipes(@NonNull List<String> ingredients) {
        mRecipeApiResponse.addSource(
                mRecipeApiRepo.getRecipesByIngredients(ingredients),
                mRecipeApiResponse::setValue
        );
        return mRecipeApiResponse;
    }

    public LiveData<RecipeApiResponse> getRandomRecipes(Integer count) {
        mRecipeApiResponse.addSource(
                mRecipeApiRepo.getRandomRecipes(count),
                mRecipeApiResponse::setValue
        );
        return mRecipeApiResponse;
    }

    public LiveData<RecipeApiResponse> searchRecipes(String query) {
        mRecipeApiResponse.addSource(
                mRecipeApiRepo.searchRecipes(query),
                mRecipeApiResponse::setValue
        );
        return mRecipeApiResponse;
    }

    public LiveData<RecipeApiResponse> getRecipesBulk(@NonNull List<String> ids) {
        mRecipeApiResponse.addSource(
                mRecipeApiRepo.getRecipesBulk(ids),
                mRecipeApiResponse::setValue
        );
        return mRecipeApiResponse;
    }
}