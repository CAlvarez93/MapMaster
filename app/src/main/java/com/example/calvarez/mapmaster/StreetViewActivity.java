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

public class StreetViewActivity extends FragmentActivity implements OnStreetViewPanoramaReadyCallback {

    int count = 0;
    MainActivity mActivity;
    final Context context = this;




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
                dialog.setTitle("Title...");

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

        panorama.setPosition(new LatLng(42.016249,-93.636185));
        StreetViewPanoramaOptions options = new StreetViewPanoramaOptions();
        StreetViewPanoramaFragment.newInstance(options);

    }


}
