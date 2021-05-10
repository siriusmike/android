package com.halfkon.recipe_finder.instructions.model;

import com.squareup.moshi.Json;

import java.util.List;

public class Instructions {
    @Json(name = "name")
    private String mName;
    @Json(name = "steps")
    private List<Step> mSteps;

    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }

    public List<Step> getSteps() {
        return mSteps;
    }
    public void setSteps(List<Step> steps) {
        mSteps = steps;
    }

    public Step getStep(int index) {
        return mSteps.get(index);
    }
}
