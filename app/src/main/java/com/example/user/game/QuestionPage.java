package com.example.user.game;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.example.user.game.R.color.backgroundcol;
import static com.example.user.game.R.color.true_answer;

public class QuestionPage extends AppCompatActivity implements View.OnClickListener {
    private TextView questiontv, questionaddtv;
    private ImageView questionaddimage;
    private Button answer1, answer2, answer3, answer4, answer5, answer6;
    private boolean flag=false;
    private String getLevel;
    private int count, i, number=0, levelpoint=0;
    private List<HashMap> listofquestions;
    private List<String> listofanswers;
    private DatabaseAccess databaseAccess;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionpage);

        questiontv = findViewById(R.id.questiontv);
        questionaddtv = findViewById(R.id.questionaddtv);
        questionaddimage = findViewById(R.id.questionaddimagetv);
        questionaddimage.setVisibility(View.INVISIBLE);
        answer1= findViewById(R.id.answer1);
        answer2= findViewById(R.id.answer2);
        answer3= findViewById(R.id.answer3);
        answer4= findViewById(R.id.answer4);
        answer5= findViewById(R.id.answer5);
        answer6= findViewById(R.id.answer6);
        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        answer4.setOnClickListener(this);
        answer5.setOnClickListener(this);
        answer6.setOnClickListener(this);
        getLevel = getIntent().getStringExtra("level");
        count = getcount(getLevel);
        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        listofquestions = databaseAccess.getQuotes();
        i=count-10+1;
        setQuestion(i, databaseAccess);
        databaseAccess.close();
    }

    public void checkEnd() {
        if (i == count) {
            intent = new Intent(getApplicationContext(), LevelPoint.class);
            intent.putExtra("levelpoint", String.valueOf(levelpoint));
            intent.putExtra("level", getLevel);
            startActivity(intent);
        } else {
            i++;
            setQuestion(i, databaseAccess);
        }
    }

    private int getcount(String level){
        switch (level){
            case "1":
                count=10;
                break;
            case "2":
                count=20;
                break;
            case "3":
                count=30;
                break;
            case "4":
                count=40;
                break;
        }
        return count;
    }

    private void setQuestion(int num, DatabaseAccess databaseAccess){
        answer1.setVisibility(View.GONE);
        answer2.setVisibility(View.GONE);
        answer3.setVisibility(View.GONE);
        answer4.setVisibility(View.GONE);
        answer5.setVisibility(View.GONE);
        answer6.setVisibility(View.GONE);
        databaseAccess.open();
        questiontv.setText((++number)+". "+listofquestions.get(num-1).get("Question").toString());
        listofanswers = databaseAccess.getanswers(num);
        listofanswers.add(listofquestions.get(num-1).get("TrueAanswer").toString());
        Log.i("listttt",listofanswers.toString());
        shuffleList(listofanswers);
        Log.i("listttt after mix",listofanswers.toString());
        Log.i("answers",listofanswers.toString());
        if (listofquestions.get(num-1).get("QuestionAdd")!=null){
            questionaddtv.setVisibility(View.VISIBLE);
            questionaddtv.setText(listofquestions.get(num-1).get("QuestionAdd").toString());
        }
        else {
            questionaddtv.setVisibility(View.INVISIBLE);
        }
        byte[] blob = (byte[]) listofquestions.get(num-1).get("QuestionAddImage");
        if (blob!=null){
            if (num==20){
                ViewGroup.LayoutParams lp = (LinearLayout.LayoutParams) questionaddimage.getLayoutParams();
                ((LinearLayout.LayoutParams) lp).setMargins(0,-450,0,0);
                questionaddimage.setLayoutParams(lp);
            }
            Bitmap btm = BitmapFactory.decodeByteArray(blob,0,blob.length);
            questionaddimage.setVisibility(View.VISIBLE);
            questionaddimage.setImageBitmap(btm);
        }
        else {
            questionaddimage.setVisibility(View.INVISIBLE);
        }

        for (int j=1; j<=listofanswers.size(); j++)
        {
            switch (j){
                case 1:
                    setButton(answer1, j);
                    break;
                case 2:
                    setButton(answer2, j);
                    break;
                case 3:
                    setButton(answer3, j);
                    break;
                case 4:
                    setButton(answer4, j);
                    break;
                case 5:
                    setButton(answer5, j);
                    break;
                case 6:
                    setButton(answer6, j);
                    break;
            }
        }
        databaseAccess.close();
    }

    private void setButton(Button button, int j){
        button.setVisibility(View.VISIBLE);
        button.setText(listofanswers.get(j-1));
    }

    private void checkAnswer(final Button button) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (button.getText() == listofquestions.get(i - 1).get("TrueAanswer")) {
                            levelpoint++;
                            button.setBackgroundColor(getResources().getColor(R.color.true_answer));
                        } else {
                            button.setBackgroundColor(getResources().getColor(R.color.false_answer));
                        }
                    }
                });
                try {
                    Thread.sleep(250);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            button.setBackground(getResources().getDrawable(R.drawable.answer_button));
                            checkEnd();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.answer1:
               checkAnswer(answer1);
                break;
            case R.id.answer2:
                checkAnswer(answer2);
                break;
            case R.id.answer3:
                checkAnswer(answer3);
                break;
            case R.id.answer4:
                checkAnswer(answer4);
                break;
            case R.id.answer5:
                checkAnswer(answer5);
                break;
            case R.id.answer6:
                checkAnswer(answer6);
                break;
        }
    }
    public static void shuffleList(List<String> a) {
        int n = a.size();
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }

    private static void swap(List<String> a, int i, int change) {
        String helper = a.get(i);
        a.set(i, a.get(change));
        a.set(change, helper);
    }
}