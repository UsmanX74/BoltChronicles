package com.example.usman.myapp3;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ViewFlipper;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;



public class ButtonActivity extends Activity {
    ViewFlipper views;


    StoreView iView;
    CreditsView cView;
    HighScoresView hView;
    OptionsView oView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        views = new ViewFlipper(getApplicationContext());
        iView = new StoreView(this);
        oView = new OptionsView(this);
        cView = new CreditsView(this);
        hView = new HighScoresView(this);
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
        Log.d("mpplaying","mp playing: "+Assets.mp.isPlaying());
        if (!Assets.mp.isPlaying()){
            Assets.mp.start();
        }
        if(!Assets.mp.isLooping()){
            Assets.mp.setLooping(true);
        }
        Log.d("mpplaying","mp playing: "+Assets.mp.isPlaying());
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
        //save user settings here
    }


    @Override
    protected void onResume() {
        super.onResume();
        //iView.resume();
        if(!Assets.mp.isPlaying()){
            Assets.mp.start();
        }
        if(!Assets.mp.isLooping()){
            Assets.mp.setLooping(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //iView.pause();
        if(Assets.counter==1){
            Assets.mp.pause();
        }
        //Toast.makeText(getApplicationContext(),"BA onPause called",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if(TitleView.viewIndex == 2 && cView.isBetaTestersScreen() && !cView.isCreditsScreen()){
            cView.setBetaTestersScreen(false);
            cView.setCreditsScreen(true);
        }else{
            Assets.counter=0;
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
