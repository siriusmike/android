package com.halfkon.recipe_finder.article.ui.ArticleFragments;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.halfkon.recipe_finder.R;
import com.halfkon.recipe_finder.recipe.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class RecipeFragment extends Fragment {
    private final static String ID = "id";
    private final static String TITLE = "title";
    private final static String SUMMARY = "summary";
    private final static String TYPE = "type";
    private final static String COVER = "cover";


    public static RecipeFragment newInstance(Recipe recipe) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putInt(ID, recipe.getId());
        args.putString(TITLE, recipe.getName());
        args.putString(SUMMARY, recipe.getSummary());
        args.putString(TYPE, recipe.getType());
        args.putString(COVER, recipe.getImage());

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recipe_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            final TextView titleView = view.findViewById(R.id.name_text);
            String title = args.getString(TITLE, "");
            titleView.setText(title);

            final TextView descrView = view.findViewById(R.id.description_text);
            String summary = getArguments().getString(SUMMARY, "");
            descrView.setText(summary);

            final TextView typeView = view.findViewById(R.id.type_and_time_text);
            String type = getArguments().getString(TYPE, "");
            typeView.setText(type);

            final ImageView coverView = view.findViewById(R.id.food_pictur);
            String cover = getArguments().getString(COVER, "");
            if (!cover.equals("")) {
                Picasso.with(getContext()).load(cover).into(coverView);
            }
        }


    }
}
