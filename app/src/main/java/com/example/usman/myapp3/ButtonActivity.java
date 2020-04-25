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
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;


public class ButtonActivity extends Activity {
    ViewFlipper views;


    StoreView iView;
    CreditsView cView;
    HighScoresView hView;
    OptionsView oView;

    final private UnityAdsListener unityAdsListener = new UnityAdsListener();
    private String unityGameID = "2644141";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UnityAds.initialize(this, unityGameID, unityAdsListener, true);
        views = new ViewFlipper(getApplicationContext());
        iView = new StoreView(getApplicationContext(),this);
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
        if(views.getParent() != null) {
            ((ViewGroup) views.getParent()).removeAllViews();
        }
        views.setDisplayedChild(TitleView.viewIndex);
        setContentView(views);
    }

    public void rewardPlayer(){
        //TitleActivity.totalCoins = TitleActivity.totalCoins +30;
    }
    public void showAd(){
        if(UnityAds.isReady() ){
            Assets.calledBy = "storeview";
            UnityAds.show(this, "rewardedVideo");
        }else{
            Toast.makeText(this,"Loading Ad, try again in 2 seconds",Toast.LENGTH_SHORT).show();
        }
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
        //Toast.makeText(getApplicationContext(),"BA onPause called",Toast.LENGTH_SHORT).show(); BA = ButtonActivity
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
        if(TitleView.viewIndex == 1 && oView.isControlsScreen() && !oView.isOptionsScreen()){
            oView.setControlsScreen(false);
            oView.setOptionsScreen(true);
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
                if(Assets.calledBy.equals("storeview")) {
                    Toast.makeText(getApplicationContext(), "40 coins awarded", Toast.LENGTH_SHORT).show();
                    TitleActivity.totalCoins += 40;
                    TitleActivity.preferences.edit().putInt("TotalCoins", TitleActivity.totalCoins).apply();
                }
                Assets.calledBy = "none";
            }
            if (finishState == UnityAds.FinishState.SKIPPED) {
                Toast.makeText(getApplicationContext(),"Video ad skipped, no coins awarded",Toast.LENGTH_SHORT).show();
            }
            if (finishState == UnityAds.FinishState.ERROR){
                //Toast.makeText(getApplicationContext(),"An error occurred",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s){
            Toast.makeText(getApplicationContext(),"error: "+s,Toast.LENGTH_LONG).show();
        }

    }
}
