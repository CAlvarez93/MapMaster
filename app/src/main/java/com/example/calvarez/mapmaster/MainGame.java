package com.example.calvarez.mapmaster;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.maps.OnMapReadyCallback;
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
    ArrayList<Integer> selections = new ArrayList<>();

    final LatLng ames = new LatLng(42.016249,-93.636185);
    final LatLng chicago = new LatLng(41.8781,-87.6298);
    final LatLng nyc = new LatLng(40.6892,-74.0445);
    final LatLng dsm = new LatLng(41.587584,-93.616643);
    final LatLng london = new LatLng(51.5007,-0.1246);
    final LatLng paris = new LatLng(48.8584,2.2945);
    final LatLng saintlouis = new LatLng(38.624422,-90.183932);
    final LatLng la = new LatLng(34.0522,-118.2437);
    final LatLng miami = new LatLng(25.7617,-80.1918);
    final LatLng seattle = new LatLng(47.6205,-122.3493);

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
        }else{
            prepNonTimedGame();
        }

        if(mActivity.getQuestionNumber() == 0){
            mActivity.shuffleDestinations();
        }

        for(int i=0;i<mActivity.destinations.size();i++){
            selections.add(i);
        }
        Collections.shuffle(selections);
        try {
            curDestination = mActivity.destinations.get(mActivity.getQuestionNumber());
        }catch (IndexOutOfBoundsException e){
            mActivity.toggleScreens(R.layout.results_page);
        }
        Random rn = new Random();
        for(int i = 0;i<4;i++){
            if(selections.get(i) == mActivity.getQuestionNumber())
                selections.remove(i);
            choices[i]=selections.get(i);
        }
        choices[rn.nextInt(4)] = mActivity.getQuestionNumber();

        final StreetViewPanoramaFragment streetViewPanoramaFragment = (StreetViewPanoramaFragment) mActivity.getFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // custom dialog
                final Dialog dialog = new Dialog(mActivity);
                dialog.setContentView(R.layout.popup);
                Window window = dialog.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button button = (Button) dialog.findViewById(R.id.dialogButtonOK);
                RadioButton r1 = (RadioButton) dialog.findViewById(R.id.radio1);
                RadioButton r2 = (RadioButton) dialog.findViewById(R.id.radio2);
                RadioButton r3 = (RadioButton) dialog.findViewById(R.id.radio3);
                RadioButton r4 = (RadioButton) dialog.findViewById(R.id.radio4);

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

        updatableInfo.setText(""+(mActivity.getQuestionNumber() + 1));
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {

        // sets a random LatLng as the first place we will be going, and sets street view at that place.
        Random rn = new Random();
        int answer = rn.nextInt(10);

        LatLng test[] = new LatLng[10];
        test[0] = ames;
        test[1] = chicago;
        test[2] = dsm;
        test[3] = la;
        test[4] = london;
        test[5] = miami;
        test[6] = nyc;
        test[7] = paris;
        test[8] = saintlouis;
        test[9] = seattle;


        panorama.setPosition(curDestination.getLatLng());
        StreetViewPanoramaOptions options = new StreetViewPanoramaOptions();
        StreetViewPanoramaFragment.newInstance(options);

        Log.i("location", Double.toString(answer));
    }


}
