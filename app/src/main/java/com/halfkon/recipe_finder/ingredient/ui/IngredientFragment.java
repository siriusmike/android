package com.halfkon.recipe_finder.ingredient.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.halfkon.recipe_finder.R;
import com.halfkon.recipe_finder.ingredient_amount.model.IngredientAmount;
import com.halfkon.recipe_finder.ingredient_amount.viewmodel.IngredientAmountViewModel;

import java.util.ArrayList;
import java.util.List;

public class IngredientFragment extends Fragment {
    private final static String ID = "id";

    private IngredientAdapter mAdapter;

    public static IngredientFragment newInstance(Integer recipeId) {
        IngredientFragment fragment = new IngredientFragment();
        Bundle args = new Bundle();
        args.putInt(ID, recipeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ingredient_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.ingredient_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdapter = new IngredientAdapter(this.getContext());
        recyclerView.setAdapter(mAdapter);

        Bundle args = getArguments();
        int recipeId  = 1;
        if (args != null) {
            recipeId = args.getInt(ID, 1);
        }

        IngredientAmountViewModel ingredientAmountViewModel =
                new ViewModelProvider(this).get(IngredientAmountViewModel.class);
        ingredientAmountViewModel.getApiResponse().observe(this, apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getIngredients());
            }
        });
        ingredientAmountViewModel.getIngredientsAmount(recipeId);
    }

    private void handleError(Throwable error) {
        Log.e("Ingredient Fragment", "API response error: " + error.toString());
    }

    private void handleResponse(List<IngredientAmount> data) {
        if (data != null && data.size() > 0) {
            mAdapter.setItems(data);
        } else {
            mAdapter.clearItems();
        }
        mAdapter.notifyDataSetChanged();
    }
}

class IngredientAdapter extends RecyclerView.Adapter<IngredientViewHolder> {

    List<IngredientAmount> data;
    public final Context context;
    public IngredientAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        IngredientAmount ingredientModel = data.get(position);
        holder.bind(ingredientModel);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setItems(List<IngredientAmount> recipes) {
        data = recipes;
    }

    public void clearItems() {
        data.clear();
    }
}

class IngredientViewHolder extends RecyclerView.ViewHolder {
    private final TextView name;
    public IngredientViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.text_ingredient);
    }
    public void bind(IngredientAmount ingredient){
        String info = ingredient.getName()
                .concat(" · " + ingredient.getValue().toString() + " ")
                .concat(ingredient.getUnit());
        name.setText(info);
    }
}
