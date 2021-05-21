package com.halfkon.recipe_finder.recipe.model;


import com.halfkon.recipe_finder.R;
import com.squareup.moshi.Json;
import android.content.Context;

public class Recipe {
    @Json(name = "title")
    public String mName;
    @Json(name = "id")
    private Integer mId;
    @Json(name = "image")
    private String mImage;
    @Json(name = "readyInMinutes")
    private Integer readyInMinutes;
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
        if (mSummary != null && !mSummary.equals("")) {
            // remove trailing links
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

    public String getReadyIn() {
        if (readyInMinutes != null) {
            return readyInMinutes.toString();
        }
        return "";
    }
    public void setType(String type) {
        try {
            readyInMinutes = Integer.parseInt(type);
        }
        catch (NumberFormatException e) {
            readyInMinutes = 0;
        }
    }

    public boolean getLike() {
        return mLike;
    }
    public void setLike(boolean like) {
        mLike = like;
    }
}
