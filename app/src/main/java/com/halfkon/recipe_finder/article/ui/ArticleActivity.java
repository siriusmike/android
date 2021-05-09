package com.halfkon.recipe_finder.article.ui;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.halfkon.recipe_finder.R;
import com.halfkon.recipe_finder.article.ui.ArticleFragments.IngredienFragment;
import com.halfkon.recipe_finder.article.ui.ArticleFragments.InstructionFragment;
import com.halfkon.recipe_finder.article.ui.ArticleFragments.RecipeFragment;

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
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_container, new RecipeFragment())
                    .replace(R.id.ingredient_container, new IngredienFragment())
                    .replace(R.id.instruction_container, new InstructionFragment())
                    .commit();
        }
    }
}
