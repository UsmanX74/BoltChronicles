package com.example.usman.myapp3;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ViewFlipper;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;



public class ButtonActivity extends Activity {
    ViewFlipper views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        views = new ViewFlipper(getApplicationContext());
        StoreView iView = new StoreView(this);
        OptionsView oView = new OptionsView(this);
        CreditsView cView = new CreditsView(this);
        HighScoresView hView = new HighScoresView(this);
        iView.setKeepScreenOn(true);
        oView.setKeepScreenOn(true);
        cView.setKeepScreenOn(true);
        hView.setKeepScreenOn(true);
        views.addView(iView,0);
        views.addView(oView,1);
        views.addView(cView,2);
        views.addView(hView,3);
        views.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LOW_PROFILE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            views.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
        if (!Assets.mp.isPlaying()){
            Assets.mp.start();
            if(!Assets.mp.isLooping()){
                Assets.mp.setLooping(true);
            }
        }
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
    }

    public void rewardPLayer(){
        //TitleActivity.totalCoins = TitleActivity.totalCoins +30;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!Assets.mp.isPlaying()){
            Assets.mp.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Assets.mp.pause();

    }

    private class UnityAdsListener implements IUnityAdsListener{

        @Override
        public void onUnityAdsReady(String s) {

        }

        @Override
        public void onUnityAdsStart(String s) {

        }

        @Override
        public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {

        }

        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {

        }

    }
}
