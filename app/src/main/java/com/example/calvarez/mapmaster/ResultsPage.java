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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by calvarez on 11/5/2016.
 */
public class ResultsPage extends Fragment {

    MainActivity mActivity;
    private View v;
    final Scores[] scoreList = new Scores[5];

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

        final TextView t1 = (TextView) v.findViewById(R.id.text1);
        final TextView t2 = (TextView) v.findViewById(R.id.text2);
        final TextView t3 = (TextView) v.findViewById(R.id.text3);
        final TextView t4 = (TextView) v.findViewById(R.id.text4);
        final TextView t5 = (TextView) v.findViewById(R.id.text5);


        dialog.show();
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String player_name = edit.getText().toString();
                Scores[] scores = dbSorter(0, player_name);
                dialog.dismiss();

                t1.setText("Player: "+scores[0].getName()+"               Score: "+scores[0].getScore()+"");
                t2.setText("Player: "+scores[1].getName()+"               Score: "+scores[1].getScore()+"");
                t3.setText("Player: "+scores[2].getName()+"               Score: "+scores[2].getScore()+"");
                t4.setText("Player: "+scores[3].getName()+"               Score: "+scores[3].getScore()+"");
                t5.setText("Player: "+scores[4].getName()+"               Score: "+scores[4].getScore()+"");
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

    public Scores[] dbSorter(int score, String name){
        //DBHandler dbh = new DBHandler(mActivity); // all to call methods in the DB handler class
        Scores s = new Scores(score, name); // makes the current score and name into a score object
        //Scores[] scoreList =  dbh.getAllScores();// gets all the current scores from score list*/
        if(scoreList[0] == null) {
            Scores s1 = new Scores();
            s1.setName("null");
            s1.setScore(0);
            scoreList[0] = s1;
            scoreList[1] = s1;
            scoreList[2] = s1;
            scoreList[3] = s1;
            scoreList[4] = s1;
            Log.i("set firt to null thing", "test");
        }

        outerloop:
            for (int i = 0; i < scoreList.length; i++) {
                if (scoreList[i].getName().equalsIgnoreCase(name)) {
                    if (scoreList[i].getScore() < score) {
                        scoreList[i] = s;
                        break outerloop;
                    } else {
                        break outerloop;
                    }
                }
                if(scoreList[i].getName() == "null" ){
                    scoreList[i] = s;
                    break outerloop;
                }
                if (i == scoreList.length - 1 && scoreList.length - 1 < 5) {
                    scoreList[i] = s;
                }
            }

        //Sorting loop to sort from highest score to lowest


            for (int j = 1; j < scoreList.length; j++) {
                Scores temp;
                if (scoreList[j].getScore() < scoreList[j - 1].getScore()) {
                    temp = scoreList[j];
                    scoreList[j] = scoreList[j - 1];
                    scoreList[j - 1] = temp;
                }
            }


       return scoreList;
    }

}
