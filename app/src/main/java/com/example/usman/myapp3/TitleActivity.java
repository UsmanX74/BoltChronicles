package com.example.usman.myapp3;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;

import static com.example.usman.myapp3.TitleView.sound;

public class TitleActivity extends Activity {
    public static int sW,sH;
    TitleView tView;
    //DECLARE ALL BITMAP VARIABLES HERE:
    static SharedPreferences preferences;
    static Bitmap tempPepe;
    protected int totalCoins;
    protected static float currentSoundVolume,currentMusicVolume,currentSoundSliderValue,currentMusicSliderValue;
    protected static final float DEFAULT_MUSIC_SLIDER_VALUE = 25f ,DEFAULT_SOUND_SLIDER_VALUE = 25f, TOTAL_NUMBER_OF_STEPS = 50f;
    //static int [] highScores;
    //public static int getHighScore() {
      //  return highScores[0];
    //}
    //public static void setHighScore(int highScore) {
      //  TitleActivity.highScores[0] = highScore;
    //}
    //public static int getHighScore1() {
        //return highScores[1];
    //}
    //public static void setHighScore1(int highScore1) {
      //  TitleActivity.highScores[1] = highScore1;
    //}
    //public static int getHighScore2() {
      //  return highScores[2];
    //}
    //public static void setHighScore2(int highScore2) {
      //  TitleActivity.highScores[2] = highScore2;
    //}

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
        tView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LOW_PROFILE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            tView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //to tell the system to make our app full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Assets.loadBitmaps(getResources());
        //Assets.loadSounds(getApplicationContext());
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        //editor.putInt("TotalCoins",preferences.getInt("prevTotalCoins",0));
        //highScores[0] = preferences.getInt("highscore",0);
        //highScores[1] = preferences.getInt("highscore1",0);
        //highScores[2] = preferences.getInt("highscore2",0);
        currentSoundSliderValue = preferences.getFloat("currentSoundSliderValue",(DEFAULT_SOUND_SLIDER_VALUE));
        currentMusicSliderValue = preferences.getFloat("currentMusicSliderValue",(DEFAULT_MUSIC_SLIDER_VALUE));
        currentSoundVolume = (Assets.manageVolume(currentSoundSliderValue))/5f;
        currentMusicVolume = Assets.manageVolume(currentMusicSliderValue);
        editor.apply();
        //totalCoins = preferences.getInt("TotalCoins",0);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        Assets.mp.setVolume(currentMusicVolume,currentMusicVolume);
        if(currentMusicVolume > 0){
            sound = true;
        }else{
            sound = false;
        }
        if(!Assets.mp.isPlaying()){
            Assets.mp.start();
        }
        if(!Assets.mp.isLooping()){
            Assets.mp.setLooping(true);
        }
        //INITIALIZE ALL BITMAP RESOURCES HERE:
        tempPepe = Assets.pepeBoss;
        setContentView(tView);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //only stop if no other activity was launched
        //Assets.mp.stop(); commented out for experimental purposes
    }

    @Override
    protected void onPause() {
        super.onPause();
        //only pause if no other activity was launched
        //Assets.mp.pause(); commented out for experimental purposes
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!sound){
            Assets.mp.setVolume(0f,0f);
        }else if(sound){
            Assets.mp.setVolume(currentMusicVolume,currentMusicVolume);
        }
        if(!Assets.mp.isPlaying()){
            Assets.mp.start();
        }
        if(!Assets.mp.isLooping()){
            Assets.mp.setLooping(true);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_HOME){
            Assets.mp.pause();
        }
        return false;
    }
}
