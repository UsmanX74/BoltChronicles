package com.example.usman.myapp3;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

public class MainActivity extends Activity {
    GameView mView;
    boolean videoFinished;
    final private UnityAdsListener unityAdsListener = new UnityAdsListener();
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
        mView = new GameView(this,this);
        mView.setKeepScreenOn(true);
        String unityGameID = "2644141";
        UnityAds.initialize(this, unityGameID, unityAdsListener, true);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("HighScore",preferences.getInt("prevHighScore",0));
        editor.apply();
        highScore = preferences.getInt("prevHighScore",0);
        //For Saving High score
        //Get a Display object to access screen details
        //set the screen orientation to Landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        mView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LOW_PROFILE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
        //tell the system to hide the app title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //to tell the system to make our app full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(mView);
    }
    public void showAd(){
        if(UnityAds.isReady()){
            UnityAds.show(this, "rewardedVideo");
        }else{
            Toast.makeText(this,"Ad is loading, try again in 3 seconds",Toast.LENGTH_SHORT).show();
        }
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
        mView.pause();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("prevHighScore",getHighScore());
        editor.apply();
    }


    @Override
    public void onBackPressed(){
        if(OptionsView.backButtonPause){
            mView.pause();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        synchronized (mView.task1.mPauseLock){
            mView.task1.mPaused = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        synchronized (mView.task1.mPauseLock) {
            mView.task1.mPaused = false;
            mView.task1.mPauseLock.notifyAll();
        }
    }
    public boolean isVideoFinished() {
        return videoFinished;
    }
    public void setVideoFinished(boolean videoFinished) {
        this.videoFinished = videoFinished;
    }

    private class UnityAdsListener implements IUnityAdsListener {

        @Override
        public void onUnityAdsReady(String s) {
            //Toast.makeText(getApplicationContext(),"Video ad is ready",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUnityAdsStart(String s) {
            // Implement functionality for a user starting to watch an ad.
            //Toast.makeText(getApplicationContext(),"Video ad has started",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
            if (finishState == UnityAds.FinishState.COMPLETED) {
                //Log.d("adTest","ads finish emitted");
                //Toast.makeText(getApplicationContext(),"Video ad completed",Toast.LENGTH_SHORT).show();
                if(Assets.calledBy.equals("gameview")) {
                    mView.revive();
                }
                Assets.calledBy = "none";
                videoFinished = true;
            }
            if (finishState == UnityAds.FinishState.SKIPPED) {
                Toast.makeText(getApplicationContext(),"Video ad was skipped",Toast.LENGTH_SHORT).show();
                videoFinished = false;
            }
            if (finishState == UnityAds.FinishState.ERROR) {
                //Toast.makeText(getApplicationContext(),"An error occurred",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
            Toast.makeText(getApplicationContext(),"error: "+s,Toast.LENGTH_LONG).show();
        }

    }
}
