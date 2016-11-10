package com.example.calvarez.mapmaster;

/**
 * Created by Adam Dau on 11/10/2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.model.LatLng;

public class StreetViewActivity extends FragmentActivity implements OnStreetViewPanoramaReadyCallback {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_game);
        // TODO Obtain the SupportMapFragment and get notified when the map is ready to be used.
        StreetViewPanoramaFragment streetViewPanoramaFragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
        //Button back = (Button) findViewById(R.id.back_button);

        panorama.setPosition(new LatLng(42.016249,-93.636185));
        StreetViewPanoramaOptions options = new StreetViewPanoramaOptions();
        StreetViewPanoramaFragment.newInstance(options);

        //          back.setOnClickListener(new View.OnClickListener() {
        //              @Override
        //               public void onClick(View view) {
        //                  StreetView2();
        //               }
       //          });
    }

    void StreetView2() {
        Intent intent2 = new Intent (this, MapActivity.class);
        startActivity(intent2);
    }

}