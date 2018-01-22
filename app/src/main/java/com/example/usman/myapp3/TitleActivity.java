package com.example.usman.myapp3;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class TitleActivity extends Activity {
    public static int sW,sH;
    TitleView tView;
    boolean handled = false;
    //DECLARE ALL BITMAP VARIABLES HERE:
    static SharedPreferences preferences;
    static Bitmap tempPepe;
    static int coins;
    static int totalCoins;
    static int [] highScores;
    public static int getHighScore() {
        return highScores[0];
    }
    public static void setHighScore(int highScore) {
        TitleActivity.highScores[0] = highScore;
    }
    public static int getHighScore1() {
        return highScores[1];
    }
    public static void setHighScore1(int highScore1) {
        TitleActivity.highScores[1] = highScore1;
    }
    public static int getHighScore2() {
        return highScores[2];
    }
    public static void setHighScore2(int highScore2) {
        TitleActivity.highScores[2] = highScore2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(!(Thread.getDefaultUncaughtExceptionHandler() instanceof CustomExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(
                    Environment.getExternalStorageDirectory().getPath()));
        }
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
            sW = size.x;
            sH = size.y;
        super.onCreate(savedInstanceState);
        highScores = new int[3];
        if(!(Thread.getDefaultUncaughtExceptionHandler() instanceof CustomExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler
                    (new CustomExceptionHandler(Environment.getExternalStorageDirectory().getPath()));
        }
        tView = new TitleView(this);
        tView.setKeepScreenOn(true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //to tell the system to make our app full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Assets.loadBitmaps(getResources());
        Assets.loadSounds(getApplicationContext());
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("TotalCoins",preferences.getInt("prevTotalCoins",0));
        highScores[0] = preferences.getInt("highscore",0);
        highScores[1] = preferences.getInt("highscore1",0);
        highScores[2] = preferences.getInt("highscore2",0);
        editor.apply();
        totalCoins = preferences.getInt("TotalCoins",0);
        setContentView(tView);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        Assets.mp.start();
        if(!Assets.mp.isLooping()){
            Assets.mp.setLooping(true);
        }
        //INITIALIZE ALL BITMAP RESOURCES HERE:
        tempPepe = Assets.pepeBoss;
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Assets.mp.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Assets.mp.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Assets.mp.start();
        if(!Assets.mp.isLooping()){
            Assets.mp.setLooping(true);
        }
    }
}
