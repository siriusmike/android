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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import com.bumptech.glide.Glide;
import com.halfkon.recipe_finder.R;
import com.halfkon.recipe_finder.history.Injection;
import com.halfkon.recipe_finder.history.viewmodel.HistoryViewModel;
import com.halfkon.recipe_finder.history.viewmodel.ViewModelFactory;
import com.halfkon.recipe_finder.ingredient.ui.IngredientFragment;
import com.halfkon.recipe_finder.instructions.ui.InstructionFragment;
import com.halfkon.recipe_finder.recipe.SharedPreferencesHandler;
import com.halfkon.recipe_finder.recipe.model.Recipe;
import com.halfkon.recipe_finder.recipe.ui.RecipeFragment;
import com.halfkon.recipe_finder.recipe.ui.RecipesActivity;
import com.halfkon.recipe_finder.recipe.viewmodel.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements InterfaceFilter {
    private final int HISTORY_ELEM_COUNT = 10;
    private MainAdapter mAdapter;
    private String mSearchQuery;
    private HistoryViewModel mViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().
                beginTransaction()
                .add(R.id.recycler_container, new RecyclerFragment(this))
                .commit();

        RecipeViewModel recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        recipeViewModel.getApiResponse().observe(this, apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getRecipes());
            }
        });

        recipeViewModel.searchRecipes(getString(R.string.mainPage));
        ImageButton homeBtn = findViewById(R.id.home_btn);
        ImageButton historyBtn = findViewById(R.id.history_btn);
        ImageButton likesBtn = findViewById(R.id.likes_btn);
        ImageButton settingsBtn = findViewById(R.id.settings_btn);
        ImageButton searchBtn = findViewById(R.id.search_btn);

        TextView historyBtnText = findViewById(R.id.history_btn_text);
        TextView likeBtnText = findViewById(R.id.likes_btn_text);
        TextView settingBtnText = findViewById(R.id.settings_btn_text);
        EditText searchText = findViewById(R.id.search_field);

        int activeColor = getResources().getColor(R.color.green_active,  null);
        int inactiveColor = getResources().getColor(R.color.gray,  null);

        mSearchQuery = getString(R.string.mainPage);

        homeBtn.setOnClickListener(v -> {
            homeBtn.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_bg));
            homeBtn.setBackgroundResource(R.drawable.ic_home_active);
            historyBtn.setBackgroundResource(R.drawable.ic_history);
            likesBtn.setBackgroundResource(R.drawable.ic_likes);
            searchText.setText(mSearchQuery);
            recipeViewModel.searchRecipes(mSearchQuery);
        });

        searchText.setOnClickListener(v -> {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.recycler_container, new FilterFragment(this))
                    .commit();

        });

        searchBtn.setOnClickListener(v -> {
            mSearchQuery = searchText.getText().toString();
            recipeViewModel.searchRecipes(mSearchQuery);
            View view = getCurrentFocus();
            if (view != null) {
                // Hide keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });


        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = new ViewModelProvider(this, viewModelFactory).get(HistoryViewModel.class);

        historyBtn.setOnClickListener(v -> {
            historyBtn.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_bg));
            historyBtn.setBackgroundResource(R.drawable.ic_history_active);
            homeBtn.setBackgroundResource(R.drawable.ic_home);
            likesBtn.setBackgroundResource(R.drawable.ic_likes);
//            historyBtnText.setTextColor(activeColor);
            mDisposable.add(mViewModel.getRecordIds(HISTORY_ELEM_COUNT)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(recipeViewModel::getRecipesBulk,
                            throwable -> Log.e("Main Activity", "History gathering error", throwable)));
        });

        likesBtn.setOnClickListener(v -> {
            likesBtn.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_bg));
            likesBtn.setBackgroundResource(R.drawable.ic_likes_active);
            homeBtn.setBackgroundResource(R.drawable.ic_home);
            historyBtn.setBackgroundResource(R.drawable.ic_history);
//            likeBtnText.setTextColor(activeColor);
            List<String> ids = SharedPreferencesHandler.GetAllLikes(getBaseContext());
            recipeViewModel.getRecipesBulk(ids);
        });

        settingsBtn.setOnClickListener(v -> {
            settingsBtn.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_bg));
        });
    }

    public void openRecyclerFragment(){
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.recycler_container, new RecyclerFragment(this))
                .commit();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        mAdapter.notifyDataSetChanged();
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        mDisposable.clear();
//    }

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
    public final Context context;

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

    private final TextView mHead;
    private final TextView mType;
    private final ImageView mImage;
    private final ImageButton mBtnLike;
    private final ImageView mLike;
    private final RelativeLayout mRelativeLayout;
    private final Context mContext;

    public MainViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.mContext = context;
        mHead = itemView.findViewById(R.id.recycler_headtext);
        mType = itemView.findViewById(R.id.recycler_type);
        mImage = itemView.findViewById(R.id.recycler_image);
        mBtnLike = itemView.findViewById(R.id.recycler_like);
        mLike = itemView.findViewById(R.id.recycler_heart);
        mRelativeLayout = itemView.findViewById(R.id.main_item);
    }
    public void bind(Recipe model) {
        Integer id = model.getId();
        mHead.setText(model.getName());
        String readyIn = model.getReadyIn();
        if (!readyIn.equals("")) {
            mType.setText(mContext.getString(R.string.readyIn, readyIn));
        }
        String img = model.getImage();
        if (img != null && !img.equals("")) {
            Glide.with(this.mContext).load(model.getImage()).thumbnail(0.2f).into(mImage);
        }

        model.setLike(SharedPreferencesHandler.IsLiked(mContext, id));
        if (model.getLike()) {
            mLike.setBackgroundResource(R.drawable.small_heart_red);
        } else {
            mLike.setBackgroundResource(R.drawable.small_heart);
        }

        mRelativeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, RecipesActivity.class);
            intent.putExtra("id", model.getId());
            intent.putExtra("title", model.getName());
            intent.putExtra("summary", model.getSummary());

            intent.putExtra("image", model.getImage());
            intent.putExtra("type", model.getReadyIn());
            mContext.startActivity(intent);
        });
        mBtnLike.setOnClickListener(v -> {
            mBtnLike.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.like_bg));
            mLike.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.scale_bg));
            if (model.getLike()) {
                mLike.setBackgroundResource(R.drawable.small_heart);
                SharedPreferencesHandler.UpdateLike(mContext, id, false);
                model.setLike(false);
            }
            else {
                mLike.setBackgroundResource(R.drawable.small_heart_red);
                SharedPreferencesHandler.UpdateLike(mContext, id, true);
                model.setLike(true);
            }
        });
    }
}