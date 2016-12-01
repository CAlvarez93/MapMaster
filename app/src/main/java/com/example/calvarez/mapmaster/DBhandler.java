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
    private static final int DATABASE_VERSION = 2;                   // Database version
    private static final String DATABASE_NAME = "database";         // Contacts table name
    private static final String TABLE_SCORES = "scores_table";
    private static final String TABLE_TIMES = "times_table";


    /**
     * These are the columns for the SQLite database 1st table. id is only used to keep each row unique.
     * Also it is autoincremented so there is no need to change or increment it in the code. It does
     * that by itself
     */
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SCORE = "score";

    /**
     * These are the columns for the SQLite database 2nd table. id is only used to keep each row unique.
     * Also it is autoincremented so there is no need to change or increment it in the code. It does
     * that by itself
     */
    private static final String KEY_NAME2 = "name2";
    private static final String KEY_TIME = "time";

    public DBhandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the table for the SQLite database. uses the Key ID for the Primary Key
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORES_TABLE = "CREATE TABLE " + TABLE_SCORES + "("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_NAME + " TEXT,"+ KEY_SCORE +" INTEGER"+ ")";
        String CREATE_SCORES_TABLE2 = "CREATE TABLE " + TABLE_TIMES + "("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_NAME2 + " TEXT,"+ KEY_TIME +" INTEGER"+ ")";

        db.execSQL(CREATE_SCORES_TABLE);
        db.execSQL(CREATE_SCORES_TABLE2);
    }


    /**
     * Upgrades table for SQLite database
     * @param db
     * @param oldVersion
     * @param newVersion
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);// Creating tables again
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMES);// Creating tables again
        onCreate(db);
    }

    /**
    * adds the newest score to the scores table in the SQLite database
    * */
    public void addScore(Scores score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, score.getName()); // put name of player in to the content values
        values.put(KEY_SCORE, score.getScore()); // put score of player in to the content values
        db.insert(TABLE_SCORES, null, values);  // insert new rows
        db.close();                             // close database
    }

    /**
     * adds the newest time to the times table in the SQLite database
     * @param score
     */
    public void addScore2(Scores score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME2, score.getName()); // put name of player in to the content values
        values.put(KEY_TIME, score.getScore()); // put score of player in to the content values
        db.insert(TABLE_TIMES, null, values);  // insert new rows
        db.close();                             // close database
    }

    /**
     * deletes the specified score from the SQLite database
     * @param score
     */
    public void deleteScore(Scores score){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCORES, KEY_NAME + " =?", new String[] {score.getName()});
        db.close();
    }

    /**
     * deletes the specified time from the SQLite database
     * @param score
     */
    public void deleteScore2(Scores score){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TIMES, KEY_NAME2 + " =?", new String[] {score.getName()});
        db.close();
    }

    /**
     * Finds the number of score objects that are in the SCORES_TABLE, then returns
     * that int. This function is used mainly to give a length for sorting and
     * retrieving funcitons
     * @return
     */
    public int getScoresCount(){
        String countQuery = "SELECT * FROM " + TABLE_SCORES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int j = cursor.getCount();
        cursor.close();
        return j;
    }

    /**
     * Finds the number of score objects that are in the TIMES_TABLE, then returns
     * that int. This function is used mainly to give a length for sorting and
     * retrieving funcitons
     * @return
     */
    public int getScoresCount2(){
        String countQuery = "SELECT * FROM " + TABLE_TIMES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int j = cursor.getCount();
        cursor.close();
        return j;
    }

    /**
     * puts all the scores stored in the SQLite database into an array of Scores and then returns
     * that array. the scores are just in the order in which they happened.
     * @return
     */
    public Scores[] getAllScores(){
        Scores[] scoreList;
        int i = 0;
        int j;

        /* determines how big the SQLite database is and if we need to use null values*/
        if(getScoresCount()< 5){
            scoreList = new Scores[6];
            j = 5;
        }else{
            scoreList = new Scores[getScoresCount()+1];
            j = getScoresCount();
        }

        String selQuery = "SELECT * FROM " + TABLE_SCORES;  // gets the cursor set up to go through the SQLite database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selQuery, null);


        if(cursor.moveToFirst()){
            do{                                                     // this do while function captures all the scores that exist
                Scores score = new Scores();
                score.setName(cursor.getString(1));
                score.setScore(Integer.parseInt(cursor.getString(2)));
                Log.i("making null scores", "all score score list");
                scoreList[i] = score;
                i++;
            }while(cursor.moveToNext() && i < j );
            if (i < j) {
                Log.i("Value of i", Integer.toString(i));     // if there are not enough scores to fill the database then this loop fills the rest will blanks
                while (i < j) {
                    Scores score = new Scores();
                    score.setName("");
                    score.setScore(0);
                    scoreList[i] = score;
                    Log.i("making null scores", "tests");
                    i++;
                }
            }
        }
        return scoreList;
    }

    /**
     * puts all the times stored in the SQLite database into an array of Scores and then returns
     * that array. the times are just in the order in which they happened.
     * @return
     */
    public Scores[] getAllScores2(){
        Scores[] scoreList;
        int i = 0;
        int j;

        /* determines how big the SQLite database is and if we need to use null values*/
        if(getScoresCount2()< 5){
            scoreList = new Scores[6];
            j = 5;
        }else{
            scoreList = new Scores[getScoresCount2()+1];
            j = getScoresCount2();
        }

        String selQuery = "SELECT * FROM " + TABLE_TIMES;  // gets the cursor set up to go through the SQLite database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selQuery, null);


        if(cursor.moveToFirst()){
            do{                                                     // this do while function captures all the scores that exist
                Scores score = new Scores();
                score.setName(cursor.getString(1));
                score.setScore(Integer.parseInt(cursor.getString(2)));
                Log.i("making null scores", "in Time Table");
                scoreList[i] = score;
                i++;
            }while(cursor.moveToNext() && i < j );
            if (i < j) {
                Log.i("Value of i", Integer.toString(i));     // if there are not enough scores to fill the database then this loop fills the rest will blanks
                while (i < j) {
                    Scores score = new Scores();
                    score.setName("");
                    score.setScore(0);
                    scoreList[i] = score;
                    Log.i("making null scores", "tests");
                    i++;
                }
            }
        }
        return scoreList;
    }

    /**
     * sorts through the given Scores array and puts the list in descending order.
     * It uses a bubble sort to go through all the variables, and uses getScoresCount()
     * to determine the size if there are more then 5 entries.
     * @param scoreList
     * @return
     */
    public Scores[] sortScores(Scores[] scoreList){
        int k = 5;
        Scores temp;
        if(getScoresCount()> 4){
            k = getScoresCount();
        }

        for(int i = 0; i < k; i++){
            for(int j = 1; j < (k - i); j++){
                if(scoreList[j-1].getScore() < scoreList[j].getScore()){
                    Log.i("sorted the loop", scoreList[j].getName());
                    temp = scoreList[j-1];
                    scoreList[j-1] = scoreList[j];
                    scoreList[j] = temp;
                }
            }
        }
        Log.i("sorted the loop", "test");
        return scoreList;
    }

    /**
     * sorts through the given Scores array and puts the list in descending order.
     * It uses a bubble sort to go through all the variables, and uses getScoresCount()
     * to determine the size if there are more then 5 entries.
     * @param scoreList
     * @return
     */
    public Scores[] sortScores2(Scores[] scoreList){
        int k = 5;
        Scores temp;
        if(getScoresCount2()> 4){
            k = getScoresCount2();
        }

        for(int i = 0; i < k; i++){
            for(int j = 1; j < (k - i); j++){
                if(scoreList[j-1].getScore() > scoreList[j].getScore()){
                    Log.i("sorted the loop", scoreList[j].getName());
                    if(scoreList[j].getScore() != 0) {
                        temp = scoreList[j - 1];
                        scoreList[j - 1] = scoreList[j];
                        scoreList[j] = temp;
                    }
                }
            }
        }
        Log.i("sorted the loop", "test");
        return scoreList;
    }

}
