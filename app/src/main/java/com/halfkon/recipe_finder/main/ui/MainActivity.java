package com.halfkon.recipe_finder.main.ui;

import android.content.Context;
import android.content.Intent;
import android.media.MediaCodec;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.halfkon.recipe_finder.R;
import com.halfkon.recipe_finder.article.ui.ArticleActivity;
import com.halfkon.recipe_finder.article.ui.ArticleFragments.Models.InstructionModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Model> list = new ArrayList<>();


        String[] head = new String[]{"Pancake","Pancake","Pancake","Salad"};
        String[] type = new String[]{"Breakfast · 15 mins","Breakfast · 15 mins","Breakfast · 15 mins","Breakfast · 15 mins","Breakfast · 15 mins"};
        String[] image = new String[]{"","","",""};
        boolean[] like = new boolean[]{true, false, true, false};
        for (int i = 0; i < head.length; ++i ){
            list.add(new Model(head[i], type[i], image[i], like[i]));
        }


        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new MainAdapter(list, this));

        ImageButton home_btn = findViewById(R.id.home_btn);
        ImageButton history_btn = findViewById(R.id.history_btn);
        ImageButton likes_btn = findViewById(R.id.likes_btn);
        ImageButton settings_btn = findViewById(R.id.settings_btn);

        TextView hisory_btn_text = findViewById(R.id.history_btn_text);
        TextView like_btn_text = findViewById(R.id.likes_btn_text);
        TextView setting_btn_text = findViewById(R.id.settings_btn_text);

        int textColor = getResources().getColor(R.color.green_active,  null);

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_btn.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_bg));
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
}

class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    List<Model> data;
    public Context context;

    public MainAdapter(List<Model> list, Context context) {
        data = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new MainViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Model model = data.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class MainViewHolder extends RecyclerView.ViewHolder{
    private final TextView head;
    private final TextView type;
    private final ImageView image;
    private final ImageButton btn_like;
    private final ImageView like;
    public int btn_count = 1;
    private final Context context;
    public boolean heart;

    public MainViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        head = itemView.findViewById(R.id.recycler_headtext);
        type = itemView.findViewById(R.id.recycler_type);
        image = itemView.findViewById(R.id.recycler_image);
        btn_like = itemView.findViewById(R.id.recycler_like);
        like = itemView.findViewById(R.id.recycler_heart);
        RelativeLayout relativeLayout = itemView.findViewById(R.id.main_item);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ArticleActivity.class));
            }
        });
    }
    public void bind(Model model) {

        head.setText(model.mHead);
        type.setText(model.mType);
        image.setImageResource(R.drawable.food_pictur);
        heart = model.mLike;
        if (heart) like.setBackgroundResource(R.drawable.small_heart_red);
        btn_like.setOnClickListener(v -> {
            btn_count++;
            btn_like.startAnimation(AnimationUtils.loadAnimation(context,
                    R.anim.like_bg));
            like.startAnimation(AnimationUtils.loadAnimation(context,
                    R.anim.scale_bg));
            if (heart){
                like.setBackgroundResource(R.drawable.small_heart);
                heart = false;
            }
            else {
                like.setBackgroundResource(R.drawable.small_heart_red);
                heart = true;
            }

        });
    }



}