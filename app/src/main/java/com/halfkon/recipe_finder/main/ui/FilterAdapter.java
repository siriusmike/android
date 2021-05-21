package com.halfkon.recipe_finder.main.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.halfkon.recipe_finder.R;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterViewHolder> {

    List<ExampleFilterModel> data;
    Context context;

    public FilterAdapter(List<ExampleFilterModel> list, Context context){
        data = list;
        this.context = context;
    }


    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item, parent, false);
        return new FilterViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
        ExampleFilterModel model = data.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}

class FilterViewHolder extends RecyclerView.ViewHolder{
    private final TextView data;
    Context context;

    public FilterViewHolder(@NonNull View itemView, Context context){
        super(itemView);
        data = itemView.findViewById(R.id.filter_text);
        this.context = context;
    }

    public void bind(ExampleFilterModel model){
        data.setText(model.mDate);
        data.setOnClickListener(v -> ((InterfaceFilter) context).openRecyclerFragment());
    }
}