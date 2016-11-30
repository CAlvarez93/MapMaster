package com.example.calvarez.mapmaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by adau on 11/29/2016.
 */
public class DBhandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;                   // Database version
    private static final String DATABASE_NAME = "database";         // Contacts table name
    private static final String TABLE_SCORES = "scores_table";

    private static final String KEY_NAME = "name";                   // Shops Table Columns names
    private static final String KEY_SCORE = "score";


    public DBhandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORES_TABLE = "CREATE TABLE " + TABLE_SCORES + "("+ KEY_NAME + " TEXT,"+ KEY_SCORE +" INTEGER PRIMARY KEY"+ ")";
        db.execSQL(CREATE_SCORES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);// Creating tables again
        onCreate(db);
    }


    public void addScore(Scores score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, score.getName()); // put name of player in to the content values
        values.put(KEY_SCORE, score.getScore()); // put score of player in to the content values
        db.insert(TABLE_SCORES, null, values);  // insert new rows
        db.close();                             // close database
    }

    public void deleteScore(Scores score){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCORES, KEY_NAME + " =?", new String[] {score.getName()});
        db.close();
    }

    public int getScoresCount(){
        String countQuery = "SELECT * FROM " + TABLE_SCORES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    public Scores[] getAllScores(){
        Scores[] scoreList = new Scores[6];
        String selQuery = "SELECT * FROM "+TABLE_SCORES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selQuery, null);

        int i = 0;
        if(cursor.moveToFirst()){
            while(cursor.moveToNext() && i < 5){
                Scores score = new Scores();
                score.setName(cursor.getString(0));
                score.setScore(Integer.parseInt(cursor.getString(1)));

                scoreList[i] = score;
            }
        }
        return scoreList;
    }

    public Scores[] sortScores(Scores[] scoreList){
        int k = 4;
        while(k > 0 ) {
            Scores temp;
            if (scoreList[k - 1].getScore() < scoreList[k].getScore()) {
                temp = scoreList[k];
                scoreList[k] = scoreList[k - 1];
                scoreList[k - 1] = temp;
            }
            Log.i("sorted the loop", "test");
            k--;
        }
        return scoreList;
    }



}
