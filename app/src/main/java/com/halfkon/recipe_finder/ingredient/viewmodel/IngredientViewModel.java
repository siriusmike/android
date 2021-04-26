package com.halfkon.recipe_finder.ingredient.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.halfkon.recipe_finder.ingredient.network.IngredientApiRepo;
import com.halfkon.recipe_finder.ingredient.network.IngredientApiRepoImpl;
import com.halfkon.recipe_finder.ingredient.network.IngredientApiResponse;

public class IngredientViewModel extends ViewModel {
    private final MediatorLiveData<IngredientApiResponse> mIngredientApiResponse;
    private final IngredientApiRepo mIngredientApiRepo;

    public IngredientViewModel() {
        mIngredientApiResponse = new MediatorLiveData<>();
        mIngredientApiRepo = new IngredientApiRepoImpl();
    }

    @NonNull
    public LiveData<IngredientApiResponse> getApiResponse() {
        return mIngredientApiResponse;
    }

    public LiveData<IngredientApiResponse> getIngredients(@NonNull String query) {
        mIngredientApiResponse.addSource(
                mIngredientApiRepo.getIngredients(query),
                mIngredientApiResponse::setValue
        );
        return mIngredientApiResponse;
    }
}
