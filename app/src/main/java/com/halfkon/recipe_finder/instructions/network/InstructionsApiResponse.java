package com.halfkon.recipe_finder.instructions.network;

import com.halfkon.recipe_finder.instructions.model.Instructions;


public class InstructionsApiResponse {
    private final Instructions mInstructions;
    private final Throwable mError;

    InstructionsApiResponse(Instructions instructions) {
        mInstructions = instructions;
        mError = null;
    }

    InstructionsApiResponse(Throwable error) {
        mError = error;
        mInstructions = null;
    }

    public Instructions getInstructions() {
        return mInstructions;
    }
    public Throwable getError() {
        return mError;
    }
}