package com.example.calvarez.mapmaster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by calvarez on 11/5/2016.
 */
public class MainGame extends Fragment {

    int count = 0;

    MainActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_game,container,false);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.toggleScreens(R.layout.game_setup);
            }
        });

        StreetView(); // starts the streetview activity


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

    void StreetView() {
        Intent intent = new Intent (mActivity, StreetViewActivity.class); // starts streetview activity
        startActivityForResult(intent, 200);

    }


}
