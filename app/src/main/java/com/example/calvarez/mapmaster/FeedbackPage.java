package com.example.calvarez.mapmaster;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by calvarez on 11/5/2016.
 */
public class FeedbackPage extends Fragment implements OnMapReadyCallback {

    MainActivity mActivity;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private View v;
    private Destinations curDestination;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (v != null) {
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null)
                parent.removeView(v);
        }
        try {
            v = inflater.inflate(R.layout.feedback_page,container,false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }

        if(mActivity.isGameTimed()) {
            mActivity.stopTimer();
        }

        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        curDestination = mActivity.destinations.get(mActivity.getQuestionNumber());

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        TextView feedback = (TextView) v.findViewById(R.id.feedback);

        if(mActivity.getGuessResult()){
            mActivity.incrementCorrectAnswer();
            feedback.setText("Correct");
            feedback.setTextColor(Color.GREEN);
        }else{
            feedback.setText("Incorrect");
            feedback.setTextColor(Color.RED);
        }

        mActivity.resetGuessResult();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.nextQuestion();
                if(mActivity.getNumCorrect() > (mActivity.UNTIMED_QUESTION_LIMIT - 1)) {
                    if(mActivity.isGameTimed()){
                        mActivity.toggleScreens(R.layout.main_game);
                    }else {
                        mActivity.toggleScreens(R.layout.results_page);
                    }
                }
                else{
                    mActivity.toggleScreens(R.layout.main_game);
                }
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
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in your hometown
        final LatLng ams = curDestination.getLatLng();
        Marker Ames = mMap.addMarker(new MarkerOptions().position(ams).title(curDestination.getLocationName()));
        CameraPosition AI = CameraPosition.fromLatLngZoom(ams, 5.0f);

        // Move camera to marker position
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(AI));

        // Change the initial states of the Map.
        GoogleMapOptions options = new GoogleMapOptions();
        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(true)
                .rotateGesturesEnabled(false)
                .scrollGesturesEnabled(false)
                .zoomGesturesEnabled(false)
                .zoomControlsEnabled(false)
                .tiltGesturesEnabled(false);

    }
}
