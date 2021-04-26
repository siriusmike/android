package com.halfkon.recipe_finder.recipe.model;


import com.squareup.moshi.Json;

public class Recipe {
    @Json(name = "name")
    public String mName;
    @Json(name = "id")
    private Integer mId;
    @Json(name = "image")
    private String mImage;

    public Integer getId() {return mId;}
    public void setId(Integer id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }

    public String getImage() {
        return mImage;
    }
    public void setImage(String image) {
        mImage = image;
    }
}
