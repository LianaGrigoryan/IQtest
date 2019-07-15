package com.example.user.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.io.StringReader;

public class Levels extends AppCompatActivity implements View.OnClickListener {
    private Button  level1, level2, level3, level4;
    private Animation animation;
    private Intent intent;
    private SharedPreferences spref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        level1 = findViewById(R.id.level1);
        level2 = findViewById(R.id.level2);
        level3 = findViewById(R.id.level3);
        level4 = findViewById(R.id.level4);
        level1.setOnClickListener(this);
        level2.setOnClickListener(this);
        level3.setOnClickListener(this);
        level4.setOnClickListener(this);
        spref = getApplicationContext().getSharedPreferences("levelScore",0);
        String s=spref.getString("level1",null);
        levelcolor(level1,"level1");
        levelcolor(level2,"level2");
        levelcolor(level3,"level3");
        levelcolor(level4,"level4");
    }

    private void levelcolor(Button btn, String btn_name){
        Integer sd=Integer.valueOf(spref.getString(btn_name,"0"));
        if (sd>7){
            btn.setBackgroundColor(getResources().getColor(R.color.level_button_after));
        }
        else if (sd==0){
            btn.setBackgroundColor(getResources().getColor(R.color.level_button_before));
        }
        else {
            btn.setBackgroundColor(getResources().getColor(R.color.false_answer));
        }
    }

    @Override
    public void onClick(View v) {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myrotate);

        intent = new Intent(getApplicationContext(), QuestionPage.class);
        switch (v.getId()){
            case R.id.level1:
                level1.startAnimation(animation);
                intent.putExtra("level","1");
                break;
            case R.id.level2:
                level2.startAnimation(animation);
                intent.putExtra("level","2");
                break;
            case R.id.level3:
                level3.startAnimation(animation);
                intent.putExtra("level","3");
                break;
            case R.id.level4:
                level4.startAnimation(animation);
                intent.putExtra("level","4");
                break;
        }
        startActivity(intent);
    }
}
