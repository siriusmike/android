package com.halfkon.recipe_finder.main.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.halfkon.recipe_finder.R;
import com.halfkon.recipe_finder.recipe.SharedPreferencesHandler;
import com.halfkon.recipe_finder.recipe.model.Recipe;
import com.halfkon.recipe_finder.recipe.ui.RecipesActivity;
import com.halfkon.recipe_finder.recipe.viewmodel.RecipeViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MainAdapter(this);
        recyclerView.setAdapter(mAdapter);

        RecipeViewModel recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        recipeViewModel.getApiResponse().observe(this, apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getRecipes());
            }
        });

        recipeViewModel.searchRecipes("pancake");
//        recipeViewModel.getRandomRecipes();
        ImageButton home_btn = findViewById(R.id.home_btn);
        ImageButton history_btn = findViewById(R.id.history_btn);
        ImageButton likes_btn = findViewById(R.id.likes_btn);
        ImageButton settings_btn = findViewById(R.id.settings_btn);
        ImageButton search_btn = findViewById(R.id.search_btn);

        TextView hisory_btn_text = findViewById(R.id.history_btn_text);
        TextView like_btn_text = findViewById(R.id.likes_btn_text);
        TextView setting_btn_text = findViewById(R.id.settings_btn_text);
        EditText search_text = findViewById(R.id.search_field);

        int textColor = getResources().getColor(R.color.green_active,  null);

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_btn.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_bg));
                search_text.setText("");
                recipeViewModel.searchRecipes("pancake");
//                recipeViewModel.getRandomRecipes();
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = search_text.getText().toString();
                recipeViewModel.searchRecipes(query);
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history_btn.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_bg));
                history_btn.setBackgroundResource(R.drawable.ic_history_active);
                hisory_btn_text.setTextColor(textColor);

            }
        });

        likes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likes_btn.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_bg));
                likes_btn.setBackgroundResource(R.drawable.ic_likes_active);
                like_btn_text.setTextColor(textColor);
                List<String> ids = SharedPreferencesHandler.GetAllLikes(getBaseContext());
                recipeViewModel.getRecipesBulk(ids);
            }
        });

        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings_btn.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_bg));
                settings_btn.setBackgroundResource(R.drawable.ic_settings_active);
                setting_btn_text.setTextColor(textColor);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    private void handleError(Throwable error) {
        Log.e("Main Activity", "API error: " + error.toString());
    }

    private void handleResponse(List<Recipe> data) {
        if (data != null && data.size() > 0) {
            mAdapter.setItems(data);
        } else {
            mAdapter.clearItems();
        }
        mAdapter.notifyDataSetChanged();
    }
}

class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    List<Recipe> data;
    public Context context;

    public MainAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new MainViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Recipe model = data.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setItems(List<Recipe> recipes) {
        data = recipes;
    }

    public void clearItems() {
        data.clear();
    }
}
class MainViewHolder extends RecyclerView.ViewHolder{

    private final TextView head;
    private final TextView type;
    private final ImageView image;
    private final ImageButton btn_like;
    private final ImageView like;
    private final RelativeLayout relativeLayout;
    public int btn_count = 1;
    private final Context context;

    public MainViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        head = itemView.findViewById(R.id.recycler_headtext);
        type = itemView.findViewById(R.id.recycler_type);
        image = itemView.findViewById(R.id.recycler_image);
        btn_like = itemView.findViewById(R.id.recycler_like);
        like = itemView.findViewById(R.id.recycler_heart);
        relativeLayout = itemView.findViewById(R.id.main_item);
    }
    public void bind(Recipe model) {
        Integer id = model.getId();
        head.setText(model.getName());
        if (!model.getType().equals("")) {
            type.setText(model.getType() + context.getString(R.string.minutes));
        }
        String img = model.getImage();
        if (img != null && !img.equals("")) {
            Picasso.with(this.context).load(model.getImage()).into(image);
        }

        model.setLike(SharedPreferencesHandler.IsLiked(context, id));
        if (model.getLike()) {
            like.setBackgroundResource(R.drawable.small_heart_red);
        }

        relativeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipesActivity.class);
            intent.putExtra("id", model.getId());
            intent.putExtra("title", model.getName());
            intent.putExtra("summary", model.getSummary());

            intent.putExtra("image", model.getImage());
            intent.putExtra("type", model.getType());
            context.startActivity(intent);
        });
        btn_like.setOnClickListener(v -> {
            btn_count++;
            btn_like.startAnimation(AnimationUtils.loadAnimation(context,
                    R.anim.like_bg));
            like.startAnimation(AnimationUtils.loadAnimation(context,
                    R.anim.scale_bg));
            if (model.getLike()) {
                like.setBackgroundResource(R.drawable.small_heart);
                SharedPreferencesHandler.UpdateLike(context, id, false);
                model.setLike(false);
            }
            else {
                like.setBackgroundResource(R.drawable.small_heart_red);
                SharedPreferencesHandler.UpdateLike(context, id, true);
                model.setLike(true);
            }
        });
    }
}