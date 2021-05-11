package com.halfkon.recipe_finder.recipe.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.halfkon.recipe_finder.R;
import com.halfkon.recipe_finder.history.Injection;
import com.halfkon.recipe_finder.history.viewmodel.HistoryViewModel;
import com.halfkon.recipe_finder.history.viewmodel.ViewModelFactory;
import com.halfkon.recipe_finder.ingredient.ui.IngredientFragment;
import com.halfkon.recipe_finder.instructions.ui.InstructionFragment;
import com.halfkon.recipe_finder.recipe.SharedPreferencesHandler;
import com.halfkon.recipe_finder.recipe.model.Recipe;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class RecipesActivity extends AppCompatActivity {
    public boolean mIsLiked;
    public Integer mRecipeId;
    private HistoryViewModel mViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        final ImageButton imageButtonBack = findViewById(R.id.back);
        final ImageView imageViewVector = findViewById(R.id.vector);

        mRecipeId = getIntent().getExtras().getInt("id", 1);
        LoadPreferences();

        imageButtonBack.setOnClickListener(v -> {
            imageButtonBack.startAnimation(AnimationUtils.loadAnimation(RecipesActivity.this, R.anim.like_bg));
            imageViewVector.startAnimation(AnimationUtils.loadAnimation(RecipesActivity.this, R.anim.scale_bg));
            RecipesActivity.this.finish();
        });

        final ImageButton imageButtonLike = findViewById(R.id.like);
        final ImageView imageViewHeart = findViewById(R.id.heart);

        imageButtonLike.setOnClickListener(v -> {
            imageButtonLike.startAnimation(AnimationUtils.loadAnimation(RecipesActivity.this, R.anim.like_bg));
            imageViewHeart.startAnimation(AnimationUtils.loadAnimation(RecipesActivity.this, R.anim.scale_bg));
            if (mIsLiked) {
                imageViewHeart.setBackgroundResource(R.drawable.ic_heart);
                mIsLiked = false;
            } else {
                imageViewHeart.setBackgroundResource(R.drawable.ic_redheart);
                mIsLiked = true;
            }
            SavePreferences();
        });

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            Recipe recipe = new Recipe();
            recipe.setId(bundle.getInt("id", 1));
            recipe.setName(bundle.getString("title", ""));
            recipe.setSummary(bundle.getString("summary", ""));
            recipe.setImage(bundle.getString("image", ""));
            recipe.setType(bundle.getString("type", "0"));

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.recipe_container, RecipeFragment.newInstance(recipe))
                    .replace(R.id.ingredient_container, IngredientFragment.newInstance(recipe.getId()))
                    .replace(R.id.instruction_container, InstructionFragment.newInstance(recipe.getId()));
            ft.commit();
        }

        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = new ViewModelProvider(this, viewModelFactory).get(HistoryViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDisposable.add(mViewModel.updateRecord(mRecipeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.d("Recipe activity", "History record created"),
                        throwable -> Log.e("Recipe activity",
                                "Unable to update history record", throwable)));

    }
    @Override
    protected void onStop() {
        super.onStop();
        mDisposable.clear();
    }

    private void LoadPreferences() {
        mIsLiked = SharedPreferencesHandler.IsLiked(this, mRecipeId);
        if (mIsLiked) {
            findViewById(R.id.heart).setBackgroundResource(R.drawable.ic_redheart);
        }
    }

    private void SavePreferences() {
        SharedPreferencesHandler.UpdateLike(this, mRecipeId, mIsLiked);
    }
}
