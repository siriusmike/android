package com.halfkon.recipe_finder.instructions.model;

import com.halfkon.recipe_finder.ingredient.model.Ingredient;
import com.squareup.moshi.Json;

import java.util.List;

public class Step {
    @Json(name = "ingredients")
    public List<Ingredient> mIngredients;
    @Json(name = "number")
    private Integer mNumber;
    @Json(name = "step")
    private String mStep;

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }
    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    public Integer getNumber() {
        return mNumber;
    }
    public void setNumber(Integer number) {
        mNumber = number;
    }

    public String getStep() {
        return mStep;
    }
    public void setStep(String step) {
        mStep = step;
    }
}
