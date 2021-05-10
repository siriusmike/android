package com.halfkon.recipe_finder.article.ui;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.halfkon.recipe_finder.R;
import com.halfkon.recipe_finder.article.ui.ArticleFragments.IngredienFragment;
import com.halfkon.recipe_finder.article.ui.ArticleFragments.InstructionFragment;
import com.halfkon.recipe_finder.article.ui.ArticleFragments.RecipeFragment;
import com.halfkon.recipe_finder.ingredient_amount.viewmodel.IngredientAmountViewModel;
import com.halfkon.recipe_finder.instructions.viewmodel.InstructionsViewModel;
import com.halfkon.recipe_finder.recipe.model.Recipe;
import com.halfkon.recipe_finder.recipe.viewmodel.RecipeViewModel;

import java.util.Objects;

public class ArticleActivity extends AppCompatActivity {
    public boolean heart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        final ImageButton imageButtonBack = findViewById(R.id.back);
        final ImageView imageViewVector = findViewById(R.id.vector);
        heart = false;

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButtonBack.startAnimation(AnimationUtils.loadAnimation(ArticleActivity.this, R.anim.like_bg));
                imageViewVector.startAnimation(AnimationUtils.loadAnimation(ArticleActivity.this, R.anim.scale_bg));
                ArticleActivity.this.finish();
            }
        });

        final ImageButton imageButtonLike = findViewById(R.id.like);
        final ImageView imageViewHeart = findViewById(R.id.heart);


        imageButtonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButtonLike.startAnimation(AnimationUtils.loadAnimation(ArticleActivity.this, R.anim.like_bg));
                imageViewHeart.startAnimation(AnimationUtils.loadAnimation(ArticleActivity.this, R.anim.scale_bg));
                if (heart){
                    imageViewHeart.setBackgroundResource(R.drawable.ic_heart);
                    heart = false;
                }
                else {
                    imageViewHeart.setBackgroundResource(R.drawable.ic_redheart);
                    heart = true;
                }

            }
        });

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            Recipe recipe = new Recipe();
            recipe.setId(bundle.getInt("id", 1));
            recipe.setName(bundle.getString("title", ""));
            recipe.setSummary(bundle.getString("summary", ""));
            recipe.setImage(bundle.getString("image", ""));
            recipe.setType(bundle.getString("type", ""));

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.recipe_container, RecipeFragment.newInstance(recipe))
                    .replace(R.id.ingredient_container, IngredienFragment.newInstance(recipe.getId()))
                    .replace(R.id.instruction_container, InstructionFragment.newInstance(recipe.getId()));
            ft.commit();

        }
    }
}
