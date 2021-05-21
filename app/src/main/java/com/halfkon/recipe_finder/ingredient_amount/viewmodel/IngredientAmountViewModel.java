package com.halfkon.recipe_finder.ingredient_amount.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.halfkon.recipe_finder.ingredient_amount.network.IngredientAmountApiRepo;
import com.halfkon.recipe_finder.ingredient_amount.network.IngredientAmountApiRepoImpl;
import com.halfkon.recipe_finder.ingredient_amount.network.IngredientAmountApiResponse;

public class IngredientAmountViewModel extends ViewModel {
    private final MediatorLiveData<IngredientAmountApiResponse> mIngredientAmountApiResponse;
    private final IngredientAmountApiRepo mIngredientAmountApiRepo;

    public IngredientAmountViewModel() {
        mIngredientAmountApiResponse = new MediatorLiveData<>();
        mIngredientAmountApiRepo = new IngredientAmountApiRepoImpl();
    }

    @NonNull
    public LiveData<IngredientAmountApiResponse> getApiResponse() {
        return mIngredientAmountApiResponse;
    }

    public LiveData<IngredientAmountApiResponse> getIngredientsAmount(@NonNull Integer recipeId) {
        mIngredientAmountApiResponse.addSource(
                mIngredientAmountApiRepo.getIngredientsAmount(recipeId),
                mIngredientAmountApiResponse::setValue
        );
        return mIngredientAmountApiResponse;
    }
}
