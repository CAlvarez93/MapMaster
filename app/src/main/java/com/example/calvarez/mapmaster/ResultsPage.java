package com.example.calvarez.mapmaster;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by calvarez on 11/5/2016.
 */
public class ResultsPage extends Fragment {

    MainActivity mActivity;
    private View v;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (v != null) {
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null)
                parent.removeView(v);
        }
        try {
            v = inflater.inflate(R.layout.results_page,container,false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }


        /**
         * This is only here until we figure out how to have two leaderboards - one for each mode.
         * For now, I'll just use a Toast thing to check and see how much time has elapsed for the
         * RaceTo10 mode.
         */
       /* if(!mActivity.isGameTimed()) {
            final long timeElapsed = mActivity.stopUntimedGame();
            Toast.makeText(mActivity, "Done! You finish in: " + (timeElapsed / 1000) + "sec", Toast.LENGTH_SHORT).show();
        }*/



        //floating button that takes us back to the title page
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);

        //set up dialog pop up
        final Dialog dialog = new Dialog(mActivity);
        dialog.setContentView(R.layout.results_popup);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        //set up edit text and button for the dialog pop up
        final EditText edit = (EditText) dialog.findViewById(R.id.edit_name);
        Button button = (Button) dialog.findViewById(R.id.enter_button);

        /**
         * The first five text views are for the name of the player, the next five display the score of
         * each player. The text views are in a grid layout.
         */
        final long timeElapsed = mActivity.stopUntimedGame();
        final TextView title = (TextView) v.findViewById(R.id.title);
        final TextView t1 = (TextView) v.findViewById(R.id.text1);
        final TextView t2 = (TextView) v.findViewById(R.id.text2);
        final TextView t3 = (TextView) v.findViewById(R.id.text3);
        final TextView t4 = (TextView) v.findViewById(R.id.text4);
        final TextView t5 = (TextView) v.findViewById(R.id.text5);
        final TextView t11 = (TextView) v.findViewById(R.id.text11);
        final TextView t22 = (TextView) v.findViewById(R.id.text22);
        final TextView t33 = (TextView) v.findViewById(R.id.text33);
        final TextView t44 = (TextView) v.findViewById(R.id.text44);
        final TextView t55 = (TextView) v.findViewById(R.id.text55);

        dialog.show();
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String player_name = edit.getText().toString();
                if(mActivity.isGameTimed()) {

                    /**
                     * if it is a timed game then it will go to dbSorter and create a scores array there, then
                     * use those values to populate the results page.
                     */
                    Scores[] scores = dbSorter(mActivity.getNumCorrect(), player_name); // adds the new score to list if it's a high score.
                    title.setText(" Top Five Power Minuet Scores!");
                    t1.setText("Player:  "+scores[0].getName()+"");
                    t2.setText("Player:  "+scores[1].getName()+"");
                    t3.setText("Player:  "+scores[2].getName()+"");
                    t4.setText("Player:  "+scores[3].getName()+"");
                    t5.setText("Player:  "+scores[4].getName()+"");
                    t11.setText("Score: "+scores[0].getScore()+"");
                    t22.setText("Score: "+scores[1].getScore()+"");
                    t33.setText("Score: "+scores[2].getScore()+"");
                    t44.setText("Score: "+scores[3].getScore()+"");
                    t55.setText("Score: "+scores[4].getScore()+"");
                }else{

                    /**
                     * if it is a power 10 game then it will go to dbSorter2 and create a scores array there, then
                     * use those values to populate the results page.
                     */
                    Log.i("timeElapsed long", Long.toString(timeElapsed));
                    Scores[] scores = dbSorter2((int) (timeElapsed/1000), player_name);
                    title.setText(" Top Five Race to 10 Times!");
                    t1.setText("Player:  "+scores[0].getName()+"");
                    t2.setText("Player:  "+scores[1].getName()+"");
                    t3.setText("Player:  "+scores[2].getName()+"");
                    t4.setText("Player:  "+scores[3].getName()+"");
                    t5.setText("Player:  "+scores[4].getName()+"");
                    t11.setText("Score: "+scores[0].getScore()+" sec");
                    t22.setText("Score: "+scores[1].getScore()+" sec");
                    t33.setText("Score: "+scores[2].getScore()+" sec");
                    t44.setText("Score: "+scores[3].getScore()+" sec");
                    t55.setText("Score: "+scores[4].getScore()+" sec");
                }
                dialog.dismiss();

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.toggleScreens(R.layout.title_page);
            }
        });

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }

    /**
     * dbSorter adds the score of the last game to the database and then retrives all
     * of the scores in the database from getAllScores(). Then it sends all those to be sorted
     * in sortScores() then returns the sorted array back to be displayed.
     * @param score
     * @param name
     * @return
     */
    public Scores[] dbSorter(int score, String name){

        DBhandler db = new DBhandler(mActivity);
        Scores s1 = new Scores(score, name);  // makes the current score and name into a score object

        db.addScore(s1);
        Scores[] scoreList = db.getAllScores(); // gets score list from main activity
        scoreList = db.sortScores(scoreList);
        Log.i("Scores count", Integer.toString(db.getScoresCount()));

       return scoreList;
    }

    /**
     * dbSorter2 adds the time of the last game to the database and then retrives all
     * of the scores in the database from getAllScores2(). Then it sends all those to be sorted
     * in sortScores2() then returns the sorted array back to be displayed.
     * @param time
     * @param name
     * @return
     */
    public Scores[] dbSorter2(int time, String name){
        DBhandler db = new DBhandler(mActivity);
        Scores s2 = new Scores(time, name);  // makes the current score and name into a score object

        db.addScore2(s2);
        Scores[] scoreList = db.getAllScores2(); // gets score list from main activity
        scoreList = db.sortScores2(scoreList);
        Log.i("Scores count", Integer.toString(db.getScoresCount2()));
        Log.i("Scores time", Integer.toString(time));

        return scoreList;
    }

}
