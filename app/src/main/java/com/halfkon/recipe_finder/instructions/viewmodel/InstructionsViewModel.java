package com.halfkon.recipe_finder.instructions.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.halfkon.recipe_finder.instructions.network.InstructionsApiRepo;
import com.halfkon.recipe_finder.instructions.network.InstructionsApiRepoImpl;
import com.halfkon.recipe_finder.instructions.network.InstructionsApiResponse;

public class InstructionsViewModel extends ViewModel {
    private final MediatorLiveData<InstructionsApiResponse> mInstructionsApiResponse;
    private final InstructionsApiRepo mInstructionsApiRepo;

    public InstructionsViewModel() {
        mInstructionsApiResponse = new MediatorLiveData<>();
        mInstructionsApiRepo = new InstructionsApiRepoImpl();
    }

    @NonNull
    public LiveData<InstructionsApiResponse> getApiResponse() {
        return mInstructionsApiResponse;
    }

    public LiveData<InstructionsApiResponse> getInstructions(@NonNull Integer recipeId) {
        mInstructionsApiResponse.addSource(
                mInstructionsApiRepo.getInstructions(recipeId),
                mInstructionsApiResponse::setValue
        );
        return mInstructionsApiResponse;
    }
}
