package com.example.calvarez.mapmaster;

/**
 * Created by Adam Dau on 11/10/2016.
 */
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StreetViewActivity extends FragmentActivity implements OnStreetViewPanoramaReadyCallback {

    int count = 0;
    MainActivity mActivity;
    final Context context = this;
    final LatLng ames = new LatLng(42.016249,-93.636185);
    final LatLng chicago = new LatLng(41.8781,-87.6298);
    final LatLng nyc = new LatLng(40.6892,-74.0445);
    final LatLng dsm = new LatLng(41.6005,-93.6091);
    final LatLng london = new LatLng(51.5007,-0.1246);
    final LatLng paris = new LatLng(48.8584,2.2945);
    final LatLng saintlouis = new LatLng(38.627003,-90.199404);
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

       /* List<LatLng> location = new ArrayList<LatLng>();
        location.add(ames);
        location.add(chicago);
        location.add(nyc);
        location.add(dsm);
        location.add(london);
        location.add(paris);
        location.add(saintlouis);
        location.add(la);
        location.add(miami);
        location.add(seattle);*/


        Random rn = new Random();
        int answer = rn.nextInt(10);

        panorama.setPosition(test[answer]);
        StreetViewPanoramaOptions options = new StreetViewPanoramaOptions();
        StreetViewPanoramaFragment.newInstance(options);

        Log.i("location", Double.toString(answer));

    }


}
