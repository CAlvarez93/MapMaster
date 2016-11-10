package com.example.calvarez.mapmaster;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;

/**
 * Created by calvarez on 11/10/2016.
 */
public class GIFView extends View {

    private String gifName = "giphy";
    private Movie movie;
    private long moviestart;
    public GIFView(Context context) throws IOException {
        super(context);
        movie=Movie.decodeStream(getResources().openRawResource(R.raw.giphy));
    }
    public GIFView(Context context, AttributeSet attrs) throws IOException{
        super(context, attrs);
        movie=Movie.decodeStream(getResources().openRawResource(R.raw.giphy));
    }
    public GIFView(Context context, AttributeSet attrs, int defStyle) throws IOException {
        super(context, attrs, defStyle);
        movie= Movie.decodeStream(getResources().openRawResource(R.raw.giphy));
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
