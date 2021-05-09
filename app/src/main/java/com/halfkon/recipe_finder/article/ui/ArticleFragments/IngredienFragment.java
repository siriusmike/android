package com.halfkon.recipe_finder.article.ui.ArticleFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.halfkon.recipe_finder.R;
import com.halfkon.recipe_finder.article.ui.ArticleFragments.Models.IngredientModel;

import java.util.ArrayList;
import java.util.List;

public class IngredienFragment extends Fragment {

    List<IngredientModel> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] ingredients = {"2 Eggs", "50g Butter", "1/2 Potato", "1/2 Potato", "1/2 Potato", "1/2 Potato", "1/2 Potato", "1/2 Potato"};
        for (String ingredient : ingredients) {
            list.add(new IngredientModel(ingredient));
        }

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
        recyclerView.setAdapter(new IngredientAdapter(list));
    }
}

class IngredientAdapter extends RecyclerView.Adapter<IngredientViewHolder> {

    List<IngredientModel> data;

    public IngredientAdapter(List<IngredientModel> list){
        data = list;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        IngredientModel ingredientModel = data.get(position);
        holder.bind(ingredientModel);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class IngredientViewHolder extends RecyclerView.ViewHolder {
    private final TextView date;
    public IngredientViewHolder(@NonNull View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.text_ingredient);
    }
    public void bind(IngredientModel ingredientModel){
        date.setText(ingredientModel.mDate);
    }
}
