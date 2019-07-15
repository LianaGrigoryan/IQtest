package com.example.user.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LevelPoint extends AppCompatActivity {
    private TextView levelpointtv, level_number;
    private String levelpoint, getLevel;
    private ImageView scoreImage;
    private SharedPreferences spref;
    private SharedPreferences.Editor editor;
    private Animation animation;
    private Button levelslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_point);
        spref = getApplicationContext().getSharedPreferences("levelScore", 0);
        editor = spref.edit();

        levelslist = findViewById(R.id.levelspage);
        levelpointtv = findViewById(R.id.levelpointtv);
        level_number = findViewById(R.id.levelnumber);
        scoreImage = findViewById(R.id.scoreimage);
        levelpoint = getIntent().getStringExtra("levelpoint");
        getLevel = getIntent().getStringExtra("level");
        level_number.setText("Points for level "+getLevel);
        levelpointtv.setText(levelpoint);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scoreaimation);
        scoreImage.startAnimation(animation);
        if (Integer.valueOf(levelpoint)<7){
            scoreImage.setImageResource(R.mipmap.fail);
        }
        else {
            scoreImage.setImageResource(R.mipmap.pass);
        }
        editor.putString("level"+getLevel, levelpoint);
        editor.commit();
        levelslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Levels.class));
            }
        });
    }
}
