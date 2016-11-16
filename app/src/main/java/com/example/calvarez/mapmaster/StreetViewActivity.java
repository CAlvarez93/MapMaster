package com.example.calvarez.mapmaster;

/**
 * Created by Adam Dau on 11/10/2016.
 */
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.Random;

public class StreetViewActivity extends FragmentActivity implements OnStreetViewPanoramaReadyCallback {

    int count = 0;
    MainActivity mActivity;
    final Context context = this;
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



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_game);
        // TODO Obtain the SupportMapFragment and get notified when the map is ready to be used.

        StreetViewPanoramaFragment streetViewPanoramaFragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.popup);
                Window window = dialog.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button button = (Button) dialog.findViewById(R.id.dialogButtonOK);
                RadioButton r1 = (RadioButton) findViewById(R.id.radio1);
                RadioButton r2 = (RadioButton) findViewById(R.id.radio2);
                RadioButton r3 = (RadioButton) findViewById(R.id.radio3);
                RadioButton r4 = (RadioButton) findViewById(R.id.radio4);



                // if button is clicked, go to main game
                dialog.show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                        Intent i = new Intent(StreetViewActivity.this, MapActivity.class);
                        StreetViewActivity.this.startActivity(i);
                    }
                });
            }
        });

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


        panorama.setPosition(test[answer]);
        StreetViewPanoramaOptions options = new StreetViewPanoramaOptions();
        StreetViewPanoramaFragment.newInstance(options);

        Log.i("location", Double.toString(answer));
    }

    public void switchNames(){
        String name[] = new String[10];
        name[0] = "Ames, IA";
        name[1] = "Chicago, IL";
        name[2] = "Des Moines, IA";
        name[3] = "Los Angles, CA";
        name[4] = "London, England";
        name[5] = "Miami, FL";
        name[6] = "New York, NY";
        name[7] = "Paris, Italy";
        name[8] = "Saint Louis, MO";
        name[9] = "Seattle, WA";

    }

}
