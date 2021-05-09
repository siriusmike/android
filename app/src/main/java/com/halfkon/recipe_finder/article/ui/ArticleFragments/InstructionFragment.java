package com.halfkon.recipe_finder.article.ui.ArticleFragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.halfkon.recipe_finder.R;
import com.halfkon.recipe_finder.article.ui.ArticleFragments.Models.InstructionModel;

import java.util.ArrayList;
import java.util.List;

public class InstructionFragment extends Fragment {

    List<InstructionModel> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] step = new String[]{"In a large bowl, sift together eggs,butter and mashed potato; mix until smooth.", "Heat a lightly oiled griddle or frying pan over medium-high heat. Pour or scoop the batter onto the griddle, using approximately 1/4 cup for each pancake."};
        String[] image = new String[]{"", ""};
        int[] number = new int[step.length];
        for (int i = 0; i < number.length; ++i ){
            number[i] = i + 1;
            list.add(new InstructionModel(step[i], number[i], image[i]));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.instruction_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.instructions_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerView.setAdapter(new InstructionAdapter(list));
    }
}

class InstructionAdapter extends RecyclerView.Adapter<InstructionViewHolder> {

    List<InstructionModel> data;

    public InstructionAdapter(List<InstructionModel> list){
        data = list;
    }

    @NonNull
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instruction_item, parent, false);
        return new InstructionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position) {
        InstructionModel instructionModel = data.get(position);
        holder.bind(instructionModel);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class InstructionViewHolder extends RecyclerView.ViewHolder {
    private final TextView step;
    private final TextView number;
    private final ImageView image;
    public InstructionViewHolder(@NonNull View itemView) {
        super(itemView);
        step = itemView.findViewById(R.id.instructions_text);
        number = itemView.findViewById(R.id.instructions_number);
        image = itemView.findViewById(R.id.instructions_image);

    }
    public void bind(InstructionModel instructionModel){
        step.setText(instructionModel.mStep);
        number.setText(String.valueOf(instructionModel.mNumber));
        image.setImageResource(R.drawable.food_pictur);
    }
}
