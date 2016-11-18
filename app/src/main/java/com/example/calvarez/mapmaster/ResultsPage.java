package com.example.calvarez.mapmaster;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by calvarez on 11/5/2016.
 */
public class ResultsPage extends Fragment {

    MainActivity mActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.results_page,container,false);

        //floating button that takes us back to the title page
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);

        //set up dialog pop up
        final Dialog dialog = new Dialog(mActivity);
        dialog.setContentView(R.layout.popup);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //set up edit text and button for the dialog pop up
        final EditText edit = (EditText) dialog.findViewById(R.id.edit_name);
        Button button = (Button) dialog.findViewById(R.id.enter_button);


        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String player_name = edit.getText().toString();
                addScoreToDatabase(0, player_name);
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

    public void addScoreToDatabase(int score, String name){
        DBHandler dbh = new DBHandler(mActivity);
        dbh.addScore(score, name);
    }

}
