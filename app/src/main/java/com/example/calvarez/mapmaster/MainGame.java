package com.example.calvarez.mapmaster;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by calvarez on 11/5/2016.
 */
public class MainGame extends Fragment implements OnStreetViewPanoramaReadyCallback {

    int[] choices = {0,0,0,0};
    private MainActivity mActivity;
    private View v;
    private boolean isGameTimed;
    private Destinations curDestination;
    ArrayList<Integer> selections;
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (v != null) {
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null)
                parent.removeView(v);
        }
        try {
            v = inflater.inflate(R.layout.main_game,container,false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }


        isGameTimed = mActivity.isGameTimed();
        if(isGameTimed){
            prepTimedGame();
            mActivity.startTimedGame(v);
        }else{
            prepNonTimedGame();
            mActivity.startUntimedGame();
        }

        if(mActivity.getQuestionNumber() == 0){
            mActivity.shuffleDestinations();
        }
        selections = new ArrayList<>();
        for(int i=0;i<mActivity.destinations.size();i++){
            selections.add(i);
        }
        selections.remove(mActivity.getQuestionNumber());
        Collections.shuffle(selections);

        //This is just a bug fix... shouldn't be a permanent fix...
        try {
            curDestination = mActivity.destinations.get(mActivity.getQuestionNumber());
        }catch (IndexOutOfBoundsException e){

            mActivity.restartGame();

        }

        Random rn = new Random();
        for(int i = 0;i<4;i++){
            choices[i]=selections.get(i);
        }
        choices[rn.nextInt(4)] = mActivity.getQuestionNumber();

        final StreetViewPanoramaFragment streetViewPanoramaFragment = (StreetViewPanoramaFragment) mActivity.getFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);

        dialog = new Dialog(mActivity);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // custom dialog
                dialog = new Dialog(mActivity);
                dialog.setContentView(R.layout.popup);
                Window window = dialog.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button button = (Button) dialog.findViewById(R.id.dialogButtonOK);
                RadioGroup group = (RadioGroup) dialog.findViewById(R.id.radio_group);

                final RadioButton r1 = (RadioButton) dialog.findViewById(R.id.radio1);
                final RadioButton r2 = (RadioButton) dialog.findViewById(R.id.radio2);
                final RadioButton r3 = (RadioButton) dialog.findViewById(R.id.radio3);
                final RadioButton r4 = (RadioButton) dialog.findViewById(R.id.radio4);

                group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i){
                            case R.id.radio1:
                                mActivity.checkGuess(curDestination, mActivity.destinations.get(choices[0]));
                                break;
                            case R.id.radio2:
                                mActivity.checkGuess(curDestination,mActivity.destinations.get(choices[1]));
                                break;
                            case R.id.radio3:
                                mActivity.checkGuess(curDestination,mActivity.destinations.get(choices[2]));
                                break;
                            case R.id.radio4:
                                mActivity.checkGuess(curDestination,mActivity.destinations.get(choices[3]));
                                break;
                        }
                    }
                });

                r1.setText(mActivity.destinations.get(choices[0]).getLocationName());
                r2.setText(mActivity.destinations.get(choices[1]).getLocationName());
                r3.setText(mActivity.destinations.get(choices[2]).getLocationName());
                r4.setText(mActivity.destinations.get(choices[3]).getLocationName());

                // if button is clicked, go to main game
                dialog.show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                        mActivity.toggleScreens(R.layout.feedback_page);
                    }
                });
            }
        });

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;

    }

    private void prepTimedGame(){
        TextView expendableItem = (TextView) v.findViewById(R.id.expendableItem);
        TextView updatableInfo = (TextView) v.findViewById(R.id.updatableInfo);

        expendableItem.setVisibility(View.GONE);

        updatableInfo.setText("1:00");
    }

    private void prepNonTimedGame(){
        TextView expendableItem = (TextView) v.findViewById(R.id.expendableItem);
        TextView updatableInfo = (TextView) v.findViewById(R.id.updatableInfo);

        expendableItem.setVisibility(View.VISIBLE);
        expendableItem.setText("/10");

        updatableInfo.setText("" + mActivity.getNumCorrect());
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
        Random discostick = new Random();
        double offsetLat = ((double)discostick.nextInt(80) - 40.0)/10000.0;
        double offsetLong = ((double)discostick.nextInt(80) - 40.0)/10000.0;
        offsetLat = curDestination.getLatLng().latitude - offsetLat;
        offsetLong = curDestination.getLatLng().longitude - offsetLong;
        LatLng ranDestination = new LatLng(offsetLat,offsetLong);

        panorama.setPosition(ranDestination,1000);
        StreetViewPanoramaOptions options = new StreetViewPanoramaOptions();
        StreetViewPanoramaFragment.newInstance(options);
    }


}
