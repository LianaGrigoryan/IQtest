package com.example.user.game;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView genius;
    private Animation anim = null;
    private ProgressBar progressBar;
    private int progress = 0;
    private TextView tvProgressHorizontal;
    private String strProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        genius = findViewById(R.id.genius);
        //progressBar = new ProgressBar(this);
        progressBar = findViewById(R.id.firstProgress);
        tvProgressHorizontal = (TextView) findViewById(R.id.tv_progress_horizontal);
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myscale);
        anim.setRepeatCount(Animation.INFINITE);
        genius.startAnimation(anim);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress<100) {
                    progress += 10;
                    postProgress(progress);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        genius.startAnimation(anim);
                        startActivity(new Intent(getApplicationContext(), Levels.class));
                    }
                });
            }
        }).start();

    }

    private void postProgress(int progress) {
        strProgress = String.valueOf(progress) + " %";
        progressBar.setProgress(progress);

        if (progress == 0) {
            progressBar.setSecondaryProgress(0);
        } else {
            progressBar.setSecondaryProgress(progress + 5);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvProgressHorizontal.setText(strProgress);
            }
        });
    }
}
