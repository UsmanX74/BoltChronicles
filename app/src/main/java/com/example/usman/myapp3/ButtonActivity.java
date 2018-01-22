package com.example.usman.myapp3;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ViewFlipper;

public class ButtonActivity extends Activity {
    ViewFlipper views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        views = new ViewFlipper(getApplicationContext());
        ItemsAndUpgrades iView = new ItemsAndUpgrades(getApplicationContext());
        OptionsView oView = new OptionsView(getApplicationContext());
        CreditsView cView = new CreditsView(getApplicationContext());
        HighScoresView hView = new HighScoresView(getApplicationContext());
        iView.setKeepScreenOn(true);
        oView.setKeepScreenOn(true);
        cView.setKeepScreenOn(true);
        hView.setKeepScreenOn(true);
        views.addView(iView,0);
        views.addView(oView,1);
        views.addView(cView,2);
        views.addView(hView,3);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //to tell the system to make our app full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        if(views.getParent() != null) {
            ((ViewGroup) views.getParent()).removeAllViews();
        }
        views.setDisplayedChild(TitleView.viewIndex);
        setContentView(views);
        if (!Assets.mp.isPlaying()){
            Assets.mp.start();
            if(!Assets.mp.isLooping()){
                Assets.mp.setLooping(true);
            }
        }
    }


}
