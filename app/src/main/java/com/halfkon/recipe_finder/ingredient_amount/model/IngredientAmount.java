package com.halfkon.recipe_finder.ingredient_amount.model;

import com.halfkon.recipe_finder.recipe.model.Recipe;
import com.squareup.moshi.Json;

import java.util.List;

public class IngredientAmount {
    static class MetricAmount {
        @Json(name = "unit")
        String mUnit;
        @Json(name = "value")
        Float mValue;
    }
    static class Amount {
        @Json(name = "metric")
        MetricAmount mMetricAmount;
    }

    @Json(name = "name")
    private String mName;
    @Json(name = "amount")
    private Amount mAmount;

    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }

    public String getUnit() {
        return mAmount.mMetricAmount.mUnit;
    }
    public void setUnit(String unit) {
        mAmount.mMetricAmount.mUnit = unit;
    }


    public Float getValue() {
        return mAmount.mMetricAmount.mValue;
    }
    public void setValue(Float value) {
        mAmount.mMetricAmount.mValue = value;
    }
}
