package com.example.usman.myapp3;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    private static int sX;
    public static int getsX() {
        return sX;
    }
    public static void setsX(int sX) {
        MainActivity.sX = sX;
    }
    public static int getsY() {
        return sY;
    }
    public static void setsY(int sY) {
        MainActivity.sY = sY;
    }
    private static int sY;
    static SharedPreferences preferences;
    public static int getHighScore() {
        return highScore;
    }
    public static void setHighScore(int highScore) {
        MainActivity.highScore = highScore;
    }
    public static int getScore() {
        return score;
    }
    public static void setScore(int score) {
        MainActivity.score = score;
    }
    public static int score = 0;
    private static int highScore;
    //public static int getHs() {return hs;}
    //public static void setHs(int hs) {MainActivity.hs = hs;}
    //private static int hs = 0;

    protected void onCreate(Bundle savedInstanceState) {
        Display display = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(size);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                display.getSize(size);
            }
        sX = size.x;
        sY = size.y;
        super.onCreate(savedInstanceState);
        GameView mView = new GameView(this);
        mView.setKeepScreenOn(true);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("HighScore",preferences.getInt("prevHighScore",0));
        editor.apply();
        highScore = preferences.getInt("prevHighScore",0);
        //For Saving High score
        // Get a Display object to access screen details
        //set the screen orientation to Landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        //tell the system to hide the app title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //to tell the system to make our app full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(mView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("prevHighScore",getHighScore());
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        TitleActivity.totalCoins += TitleActivity.coins;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("prevHighScore",getHighScore());
        editor.putInt("prevTotalCoins",TitleActivity.totalCoins);
        editor.apply();
    }


    @Override
    public void onBackPressed(){
        //do nothing
    }

}
