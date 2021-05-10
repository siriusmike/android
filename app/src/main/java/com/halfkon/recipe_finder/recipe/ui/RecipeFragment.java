package com.halfkon.recipe_finder.recipe.ui;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.halfkon.recipe_finder.R;
import com.halfkon.recipe_finder.recipe.model.Recipe;

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
        args.putString(TYPE, recipe.getReadyIn());
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
            if (summary.equals("")) {
                summary = getString(R.string.no_summary);
            }
            descrView.setText(summary);

            final TextView typeView = view.findViewById(R.id.type_and_time_text);
            String type = getArguments().getString(TYPE, "");
            if (!type.equals("") && !type.equals("0")) {
                typeView.setText(getString(R.string.readyIn, type));
            }

            final ImageView coverView = view.findViewById(R.id.food_picture);
            String cover = getArguments().getString(COVER, "");
            Context ctx = getContext();
            if (!cover.equals("") && ctx != null) {
                Glide.with(getContext()).load(cover).thumbnail(0.2f).into(coverView);
            }
        }
    }
}
