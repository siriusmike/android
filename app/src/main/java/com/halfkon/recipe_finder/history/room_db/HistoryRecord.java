package com.halfkon.recipe_finder.history.room_db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;


@Entity(tableName = "records")
public class HistoryRecord {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "recipeid")
    private Integer mRecipeId;

    @NonNull
    @ColumnInfo(name = "dt", defaultValue = "(datetime('now'))")
    private String mDt;

    public HistoryRecord(@NonNull Integer recipeId) {
        mRecipeId = recipeId;
    }

    public Integer getRecipeId() {
        return mRecipeId;
    }

    public String getDt() {
        return mDt;
    }
    public void setDt(String dt) {
        mDt = dt;
    }

}
