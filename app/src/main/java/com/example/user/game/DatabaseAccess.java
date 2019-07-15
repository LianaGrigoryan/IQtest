package com.example.user.game;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public List<HashMap> getQuotes() {
        List<HashMap> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM table1", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            HashMap question  = new HashMap();
            question.put("Id", cursor.getString(cursor.getColumnIndex("Id")));
            question.put("Question", cursor.getString(cursor.getColumnIndex("Question")));
            question.put("QuestionAdd", cursor.getString(cursor.getColumnIndex("QuestionAdd")));
            question.put("TrueAanswer", cursor.getString(cursor.getColumnIndex("True_answer")));
            question.put("QuestionAddImage",cursor.getBlob(4));
            question.put("TrueAnswerImage",cursor.getBlob(cursor.getColumnIndex("True_answer_Image")));
            list.add(question);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getanswers(int id){
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM table2 WHERE QuestionID="+id, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(cursor.getColumnIndex("false_answer")));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
