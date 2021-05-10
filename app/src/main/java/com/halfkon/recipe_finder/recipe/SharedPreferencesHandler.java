package com.halfkon.recipe_finder.recipe;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SharedPreferencesHandler {
    public static boolean IsLiked(Context ctx, Integer recipeId) {
        android.content.SharedPreferences sharedPreferences = ctx.getSharedPreferences(
                "SHARED_PREFERENCES", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(recipeId.toString(), false);
    }

    public static void UpdateLike(Context ctx, Integer recipeId, boolean isLiked) {
        android.content.SharedPreferences sharedPreferences = ctx.getSharedPreferences(
                "SHARED_PREFERENCES", Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(recipeId.toString(), isLiked);
        editor.apply();
    }

    public static List<String> GetAllLikes(Context ctx) {
        android.content.SharedPreferences sharedPreferences = ctx.getSharedPreferences(
                "SHARED_PREFERENCES", Context.MODE_PRIVATE);
        Map<String, ?> map = sharedPreferences.getAll();
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, ?> pair : map.entrySet()) {
            Boolean v = (Boolean) pair.getValue();
            if (v) {
                result.add(pair.getKey());
            }
        }
        return result;
    }

}
