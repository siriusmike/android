package com.halfkon.recipe_finder.recipe.model;


import android.net.Uri;

import com.squareup.moshi.Json;

public class Recipe {
    @Json(name = "title")
    public String mName;
    @Json(name = "id")
    private Integer mId;
    @Json(name = "image")
    private String mImage;
    @Json(name = "sourceName")
    private String mType;

    private boolean mLike;

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

    public String getType() {
        return mType;
    }
    public void setType(String type) {
        mType = type;
    }

    public boolean getLike() {
        return mLike;
    }
    public void setLike(boolean like) {
        mLike = like;
    }
}
