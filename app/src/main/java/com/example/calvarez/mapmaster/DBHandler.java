package com.example.calvarez.mapmaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adau on 11/17/2016.
 */
public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;                   // database version
    private static final String DATABASE_NAME = "Leaderboard";      // database name
    private static final String TABLE_SCORES = "Score";            // score table name
    private static final String KEY_NAME = "name";                // shop table column names
    private static final String KEY_SCORE = "score";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORES_TABLE = "CREATE TABLE " + TABLE_SCORES + "( "+KEY_NAME+" TEXT, "+KEY_SCORE+" TEXT "+")";  // creates table with the above columns and title
        db.execSQL(CREATE_SCORES_TABLE);  // sends the table to be created
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_SCORES);                        // drop older table if already existing, creates table again.
        onCreate(db);
    }
    public void addScore(int score, String name){
        Scores s = new Scores(score, name);

        s.setName(name);
        s.setScore(score);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // put in name of player
        values.put(KEY_SCORE, score); // put in score of player
        db.insert(TABLE_SCORES, null, values);// insert the changes into database as a new row
        db.close(); // close database connection
    }

   /* public List<Scores> getAllScores() {
        List<Scores> scoreList = new ArrayList<Scores>();

        String selectQuery = "SELECT * FROM " + TABLE_SCORES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Scores s = new Scores();
                s.setName(cursor.getString(1));
                s.setScore(Integer.parseInt(cursor.getString(0)));

                scoreList.add(s);
            }while(cursor.moveToNext());
        }
        return scoreList;
    }*/

    public Scores [] getAllScores(){
        Scores[] scoreList = new Scores[5];
        String selectQuery = "SELECT * FROM " + TABLE_SCORES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


            int i = 0;
            if (cursor.moveToFirst()) {
                do {
                    Scores s = new Scores();
                    s.setName(cursor.getString(1));
                    s.setScore(Integer.parseInt(cursor.getString(0)));
                    scoreList[i] = s;
                    i++;
                } while (cursor.moveToNext());
            }

        return scoreList;
    }


   public int updateScore(int score, String name) {
       Scores s = new Scores(score, name);
       s.setName(name);
       s.setScore(score);

       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues values = new ContentValues();
       values.put(KEY_NAME, name); // put in name of player
       values.put(KEY_SCORE, score); // put in score of player

       return db.update(TABLE_SCORES, values, KEY_NAME + " = ?", new String[]{name});
   }

}
