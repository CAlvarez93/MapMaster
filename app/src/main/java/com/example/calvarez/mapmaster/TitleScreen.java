package com.example.calvarez.mapmaster;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by calvarez on 11/5/2016.
 */
public class TitleScreen extends Fragment {
    MainActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.title_page,container,false);

        Toast.makeText(mActivity, "Created!", Toast.LENGTH_SHORT).show();

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.toggleScreens(R.layout.game_setup);
            }
        });



        return v;
//        return super.onCreateView(inflater, container, savedInstanceState);
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

    private class GIFView extends View{

        private String gifName = "giphy.gif";
        private Movie movie;
        private long moviestart;
        public GIFView(Context context) throws IOException {
            super(context);
            movie=Movie.decodeStream(getResources().openRawResource(R.drawable.giphy));
        }
        public GIFView(Context context, AttributeSet attrs) throws IOException{
            super(context, attrs);
            movie=Movie.decodeStream(getResources().getAssets().open(gifName));
        }
        public GIFView(Context context, AttributeSet attrs, int defStyle) throws IOException {
            super(context, attrs, defStyle);
            movie= Movie.decodeStream(getResources().getAssets().open(gifName));
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            long now=android.os.SystemClock.uptimeMillis();
            Paint p = new Paint();
            p.setAntiAlias(true);
            if (moviestart == 0)
                moviestart = now;
            int relTime;
            relTime = (int)((now - moviestart) % movie.duration());
            movie.setTime(relTime);
            movie.draw(canvas,0,0);
            this.invalidate();
        }
    }
}
