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
    @Json(name = "readyInMinutes")
    private Integer mType;
    @Json(name = "summary")
    private String mSummary;

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

    public String getSummary() {
        if (mSummary != null) {
            return mSummary.split("\\.", 2)[0]
                    .replaceAll("<b>", "")
                    .replaceAll("</b>", "")
                    .concat(".");
        }
        return "";
    }
    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getImage() {
        return mImage;
    }
    public void setImage(String image) {
        mImage = image;
    }

    public String getType() {
        if (mType != null) {
            return mType.toString();
        }
        return "";
    }
    public void setType(String type) {
        try {
            mType = Integer.parseInt(type);
        }
        catch (NumberFormatException e) {
            mType = 0;
        }
    }

    public boolean getLike() {
        return mLike;
    }
    public void setLike(boolean like) {
        mLike = like;
    }
}
