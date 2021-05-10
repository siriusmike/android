package com.halfkon.recipe_finder.instructions.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.halfkon.recipe_finder.R;
import com.halfkon.recipe_finder.instructions.model.Instructions;
import com.halfkon.recipe_finder.instructions.model.Step;
import com.halfkon.recipe_finder.instructions.viewmodel.InstructionsViewModel;

import java.util.ArrayList;
import java.util.List;

public class InstructionFragment extends Fragment {
    private final static String ID = "id";

    private InstructionAdapter mAdapter;

    public static InstructionFragment newInstance(Integer recipeId) {
        InstructionFragment fragment = new InstructionFragment();
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
        mAdapter = new InstructionAdapter(this.getContext());
        recyclerView.setAdapter(mAdapter);

        Bundle args = getArguments();
        int recipeId  = 1;
        if (args != null) {
            recipeId = args.getInt(ID, 1);
        }

        InstructionsViewModel instructionsViewModel =
                new ViewModelProvider(this).get(InstructionsViewModel.class);
        instructionsViewModel.getApiResponse().observe(this, apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getInstructions());
            }
        });
        instructionsViewModel.getInstructions(recipeId);
    }

    private void handleError(Throwable error) {
        Log.e("Instructions Fragment", "API response error: " + error.toString());
    }

    private void handleResponse(Instructions data) {
        if (data != null && data.getSteps().size() > 0) {
            mAdapter.setItems(data);
        } else {
            mAdapter.clearItems();
        }
        mAdapter.notifyDataSetChanged();
    }
}

class InstructionAdapter extends RecyclerView.Adapter<InstructionViewHolder> {

    List<Step> data;
    public final Context context;
    public InstructionAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    @NonNull
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instruction_item, parent, false);
        return new InstructionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position) {
        Step step = data.get(position);
        holder.bind(step);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setItems(Instructions instructions) {
        data = instructions.getSteps();
    }

    public void clearItems() {
        data.clear();
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
    public void bind(Step stepModel){
        step.setText(stepModel.getStep());
        number.setText(String.valueOf(stepModel.getNumber()));
        image.setImageResource(R.drawable.food_picture);
    }
}
