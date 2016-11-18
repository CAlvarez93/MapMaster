package com.example.calvarez.mapmaster;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    TitleScreen mTitleScreen;
    GameSetup mGameSetup;
    MainGame mMainGame;
    FeedbackPage mFeedbackPage;
    ResultsPage mResultPage;

    boolean switch_widget;
    int questionNumber;

    public ArrayList<Destinations> destinations;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mTitleScreen = null;
        mGameSetup = null;
        mMainGame = null;
        mFeedbackPage = null;
        mResultPage = null;

        try {
            mTitleScreen = (TitleScreen.class).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "FAILURE - TitlePage", Toast.LENGTH_SHORT).show();
        }

        try {
            mGameSetup = (GameSetup.class).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "FAILURE - GameSetup", Toast.LENGTH_SHORT).show();
        }

        try {
            mMainGame = (MainGame.class).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "FAILURE - MainGame", Toast.LENGTH_SHORT).show();
        }

        try {
            mFeedbackPage = (FeedbackPage.class).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "FAILURE - FeedbackPage", Toast.LENGTH_SHORT).show();
        }

        try {
            mResultPage = (ResultsPage.class).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "FAILURE - ResultPage", Toast.LENGTH_SHORT).show();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.flContent,mTitleScreen);
        fragmentTransaction.commit();

        initDestinations();
        questionNumber = 0;
    }

    public void toggleScreens(int layout){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (layout){
            case R.layout.title_page:
                fragmentTransaction.replace(R.id.flContent,mTitleScreen);
                fragmentTransaction.commit();
                break;
            case R.layout.game_setup:
                fragmentTransaction.replace(R.id.flContent,mGameSetup);
                fragmentTransaction.commit();
                break;
            case R.layout.main_game:
                fragmentTransaction.replace(R.id.flContent,mMainGame);
                fragmentTransaction.commit();
                break;
            case R.layout.feedback_page:
                fragmentTransaction.replace(R.id.flContent,mFeedbackPage);
                fragmentTransaction.commit();
                break;
            case R.layout.results_page:
                fragmentTransaction.replace(R.id.flContent,mResultPage);
                fragmentTransaction.commit();
                break;
        }
    }

    public void setSwitch(boolean isChecked){
        switch_widget = isChecked;
    }

    public boolean isGameTimed(){
        if(switch_widget){
            return true;
        }
        return false;
    }

    public int getQuestionNumber(){
        return questionNumber;
    }

    /**
     *
     * @return
     *  will return false if there is no next question
     */
    public boolean nextQuestion(){
        if(questionNumber == destinations.size())
            return false;
        questionNumber++;
        return true;
    }

    public void initDestinations(){
        destinations = new ArrayList<Destinations>();

        destinations.add(new Destinations(ames,"Ames, IA, USA",1));
        destinations.add(new Destinations(chicago,"Chicago, IL, USA",2));
        destinations.add(new Destinations(nyc,"New York City, NY, USA",3));
        destinations.add(new Destinations(dsm,"Des Moines, IA, USA",4));
        destinations.add(new Destinations(london,"London, UK",5));
        destinations.add(new Destinations(paris,"Paris, France",6));
        destinations.add(new Destinations(saintlouis,"St Louis, MO, USA",7));
        destinations.add(new Destinations(la,"Los Angeles, CA, USA",8));
        destinations.add(new Destinations(miami,"Miami, FL, USA",9));
        destinations.add(new Destinations(seattle,"Seattle, WA, USA",10));

    }

    public void shuffleDestinations(){
        Collections.shuffle(destinations);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        switch (id){
            case R.id.title_sequence:
                toggleScreens(R.layout.title_page);
                break;
            case R.id.edit_game:
                toggleScreens(R.layout.game_setup);
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
