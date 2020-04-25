package com.example.usman.myapp3;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;

public class TitleActivity extends Activity {
    public static int sW,sH;
    int uiOptions;
    TitleView tView;
    //DECLARE ALL BITMAP VARIABLES HERE:
    static SharedPreferences preferences;
    static Bitmap tempPepe;
    protected static int totalCoins;
    protected static int sessionCoins;
    protected static float currentSoundVolume,currentMusicVolume,currentSoundSliderValue,currentMusicSliderValue;
    protected static final float DEFAULT_MUSIC_SLIDER_VALUE = 1f ,DEFAULT_SOUND_SLIDER_VALUE = 20f, TOTAL_NUMBER_OF_STEPS = 50f;

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
        //Assets.loadFonts(getApplicationContext());
        super.onCreate(savedInstanceState);
        if(!(Thread.getDefaultUncaughtExceptionHandler() instanceof CustomExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler
                    (new CustomExceptionHandler(Environment.getExternalStorageDirectory().getPath()));
        }
        tView = new TitleView(this);
        tView.setKeepScreenOn(true);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            uiOptions = (View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_LOW_PROFILE);
        }else{
            uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        tView.setSystemUiVisibility(uiOptions);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //to tell the system to make our app full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        currentSoundSliderValue = preferences.getFloat("currentSoundSliderValue",(DEFAULT_SOUND_SLIDER_VALUE));
        currentMusicSliderValue = preferences.getFloat("currentMusicSliderValue",(DEFAULT_MUSIC_SLIDER_VALUE));
        currentSoundVolume = (Assets.manageVolume(currentSoundSliderValue))/5f;
        currentMusicVolume = Assets.manageVolume(currentMusicSliderValue);
        totalCoins = preferences.getInt("TotalCoins",0);//https://www.youtube.com/watch?v=I-Tn6PoJ-z0&t=0s discord custom roles
        editor.apply();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        Assets.mp.setVolume(currentMusicVolume,currentMusicVolume);
        if(!Assets.mp.isPlaying()){
            Assets.mp.start();
        }
        if(!Assets.mp.isLooping()){
            Assets.mp.setLooping(true);
        }
        Log.d("currentvol","music playing: "+Assets.mp.isPlaying());
        tempPepe = Assets.pepeBoss;
        setContentView(tView);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(Assets.counter==0){
            Assets.mp.pause();
        }
        //only pause if no other activity was launched
        //Assets.mp.pause(); commented out for experimental purposes
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!Assets.mp.isPlaying()){
            Assets.mp.start();
        }
        if(!Assets.mp.isLooping()){
            Assets.mp.setLooping(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("keycodes","keycode: "+keyCode);
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Assets.mp.pause();
            this.onDestroy();
            finish();
        }
        return false;
    }
    */
}
