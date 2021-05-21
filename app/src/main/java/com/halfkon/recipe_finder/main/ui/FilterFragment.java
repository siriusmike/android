package com.halfkon.recipe_finder.main.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.halfkon.recipe_finder.R;

import java.util.ArrayList;
import java.util.List;

public class FilterFragment extends Fragment {

    Context context;

    public FilterFragment (Context main_context){
        context = main_context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<ExampleFilterModel> data = new ArrayList<>();
        String[] ingredients = new String[]{"potato", "sweet potato", "waxy potato"};
        for (int i = 0; i < ingredients.length; ++i) {
            data.add(new ExampleFilterModel(ingredients[i]));
        }

        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(new FilterAdapter(data, context));

    }

}
