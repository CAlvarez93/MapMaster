package com.example.calvarez.mapmaster;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.VideoView;

/**
 * Created by calvarez on 11/5/2016.
 */
public class GameSetup extends Fragment {

   private MainActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.game_setup,container,false);

        final ImageView power_minute = (ImageView) v.findViewById(R.id.power_minute);
        final ImageView race_to_ten = (ImageView) v.findViewById(R.id.race_to_ten);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.toggleScreens(R.layout.main_game);
            }
        });

        final Dialog dialog = new Dialog(mActivity);
        dialog.setContentView(R.layout.tutorial_popup);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        FloatingActionButton info = (FloatingActionButton) v.findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        //Uncomment for actually presenting... commenting out for the sake of debug time

        final VideoView video = (VideoView) v.findViewById(R.id.video);
        Uri uri = Uri.parse("android.resource://"+mActivity.getPackageName()+"/"+R.raw.game_setup_background);
        video.setVideoURI(uri);
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                video.start();
            }
        });
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                video.start();
            }
        });

        Switch switch_widget = (Switch) v.findViewById(R.id.switch_widget);
        mActivity.setSwitch(switch_widget.isChecked());
        if(switch_widget.isChecked()){
            power_minute.setBackgroundColor(Color.argb(0, 255, 255, 0));
            race_to_ten.setBackgroundColor(Color.argb(50,255, 255, 0));
        } else {
            power_minute.setBackgroundColor(Color.argb(50, 255, 255, 0));
            race_to_ten.setBackgroundColor(Color.argb(0, 255, 255, 0));
        }

        switch_widget.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mActivity.setSwitch(isChecked);
                if(isChecked){
                    power_minute.setBackgroundColor(Color.argb(0, 255, 255, 0));
                    race_to_ten.setBackgroundColor(Color.argb(50,255, 255, 0));
                } else {
                    power_minute.setBackgroundColor(Color.argb(50, 255, 255, 0));
                    race_to_ten.setBackgroundColor(Color.argb(0, 255, 255, 0));
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
}
