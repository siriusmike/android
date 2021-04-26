package com.halfkon.recipe_finder.recipe.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.halfkon.recipe_finder.recipe.network.RecipeApiRepo;
import com.halfkon.recipe_finder.recipe.network.RecipeApiRepoImpl;
import com.halfkon.recipe_finder.recipe.network.RecipeApiResponse;

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

    public LiveData<RecipeApiResponse> getRecipes(@NonNull  String[] ingredients) {
        mRecipeApiResponse.addSource(
                mRecipeApiRepo.getRecipes(ingredients),
                mRecipeApiResponse::setValue
        );
        return mRecipeApiResponse;
    }
}