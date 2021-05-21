package com.halfkon.recipe_finder.instructions.network;

import androidx.lifecycle.LiveData;

public interface InstructionsApiRepo {
    LiveData<InstructionsApiResponse> getInstructions(Integer recipeId);
}
