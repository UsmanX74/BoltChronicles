package com.example.usman.myapp3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

import static com.example.usman.myapp3.TitleActivity.TOTAL_NUMBER_OF_STEPS;
import static com.example.usman.myapp3.TitleActivity.currentMusicSliderValue;
import static com.example.usman.myapp3.TitleActivity.currentMusicVolume;
import static com.example.usman.myapp3.TitleActivity.currentSoundSliderValue;
import static com.example.usman.myapp3.TitleActivity.currentSoundVolume;
import static com.example.usman.myapp3.TitleActivity.sH;
import static com.example.usman.myapp3.TitleActivity.sW;
import static com.example.usman.myapp3.TitleView.SCALE_CONST;
import static com.example.usman.myapp3.TitleView.scale;

public class OptionsView extends View{
    private TextPaint tp, tp1;
    private Paint sliderPaint,paint;
    private Random generator;
    int no_of_stars= 230;
    float [] genStars;
    int a=0;
    int b=1;
    int c=0;
    private float maxSlider1ValueWidth = (float)(254 * scale);
    private float maxSlider1ValueHeight = (float)(5 * scale);
    private float maxSlider2ValueWidth = (float)(254 * scale);
    private float maxSlider2ValueHeight = (float)(5 * scale);
    private float slider1X,slider2X;
    private RectF tickRect,tickRect2,musicRect,soundEffectsRect,slider1Rect,slider2Rect,slider1RectCopy,slider2RectCopy,
                    disableOnScreenPauseRect, backButtonPauseRect, viewHighscoresRect, viewControlsRect;
    private Paint rectPaint;
    static boolean backButtonPause = true;

    public OptionsView(Context context) {
        super(context);
        generator = new Random();
        genStars= new float[no_of_stars*2];//Star Co-Ordinates
        for(int i=0; i<no_of_stars; i++){
            genStars[a]=generator.nextInt(sW);
            a += 2;
        }
        for(int i=0; i<no_of_stars; i++){
            genStars[b]=generator.nextInt(sH);
            b += 2;
        }
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(37* SCALE_CONST);
        paint.setTypeface(Assets.tf);
        paint.setColor(Color.argb(255,  249, 129, 0));
        tp1 = new TextPaint();
        tp = new TextPaint();
        sliderPaint = new Paint();
        sliderPaint.setColor(Color.rgb(255,201,14));
        sliderPaint.setStyle(Paint.Style.FILL);
        tickRect = new RectF();
        tickRect2 = new RectF();
        rectPaint = new Paint();
        rectPaint.setColor(Color.BLACK);
        rectPaint.setStrokeWidth(6*SCALE_CONST);
        rectPaint.setStyle(Paint.Style.STROKE);
        tp1.setColor(Color.argb(255,  249, 129, 0));
        tp1.setTypeface(Assets.tf);
        tp1.setTextSize(37*SCALE_CONST);
        tp.setTypeface(Assets.tf);
        tp.setTextAlign(Paint.Align.CENTER);
        tp.setTextSize(70*SCALE_CONST);
        tp.setColor(Color.argb(255,  249, 129, 0));
        soundEffectsRect = stringRect("Sound Effects: p",sW*0.1f,sH*0.24f,tp1,null,false);
        musicRect = stringRect("Music: p",sW*0.1f,sH*0.34f,tp1,null,false);
        disableOnScreenPauseRect = stringRect("Disable on-screen pause button: p",0.1f,0.44f,tp1,null,false);
        backButtonPauseRect = stringRect("Back button to pause: p",0.1f,0.54f,tp1,null,false);
        viewHighscoresRect = stringRect("View highscores: p",0.1f,0.64f,tp1,null,false);
        viewControlsRect = stringRect("View controls: p",0.1f,0.74f,tp1,null,false);
        tickRect.set(disableOnScreenPauseRect.right + (3*scale),170,((sW/2f) +50),220);
        tickRect2.set(1,1,1,1);

        slider1Rect = new RectF();
        slider2Rect = new RectF();
        slider1RectCopy = new RectF();
        slider2RectCopy = new RectF();

        slider1Rect.set( soundEffectsRect.right + (3*scale),
                 ((soundEffectsRect.top+soundEffectsRect.height()/2)-Assets.slider.getHeight()/2)+scale + (3*scale),
                 soundEffectsRect.right + (3*scale) + maxSlider1ValueWidth,
                ((soundEffectsRect.top+soundEffectsRect.height()/2)-Assets.slider.getHeight()/2)+scale + (3*scale) + maxSlider1ValueHeight);
        slider2Rect.set( musicRect.right + (3*scale),
                 ((musicRect.top+musicRect.height()/2)-Assets.slider.getHeight()/2)+scale + (3*scale),
                 musicRect.right + (3*scale) + maxSlider2ValueWidth,
                ((musicRect.top+musicRect.height()/2)-Assets.slider.getHeight()/2)+scale + (3*scale) + maxSlider2ValueHeight);
        slider1RectCopy.set(slider1Rect);
        slider2RectCopy.set(slider2Rect);

        slider1RectCopy.top = slider1Rect.top - (9*scale);
        slider2RectCopy.top = slider2Rect.top - (9*scale);

        slider1RectCopy.bottom = slider1Rect.bottom + (9*scale);
        slider2RectCopy.bottom = slider2Rect.bottom + (9*scale);

        slider1RectCopy.left = slider1Rect.left - (6*scale);
        slider2RectCopy.left = slider2Rect.left - (6*scale);

        slider1RectCopy.right = slider1Rect.right + (6*scale);
        slider2RectCopy.right = slider2Rect.right + (6*scale);

        slider1X = slider1Rect.left + (currentSoundSliderValue/TOTAL_NUMBER_OF_STEPS)*slider1Rect.width();
        slider2X = slider2Rect.left + (currentMusicSliderValue/TOTAL_NUMBER_OF_STEPS)*slider2Rect.width();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //STRINGS
        canvas.drawRGB(60,25,60);
        canvas.drawText("OPTIONS",sW/2f,(sH*0.12f),tp);
        canvas.drawText("Sound Effects: ",sW*0.1f,sH*0.24f,tp1);
        canvas.drawText("Music: ", sW*0.1f,sH*0.34f,tp1);
        canvas.drawText("Disable On-Screen Pause Button: ",sW*0.1f,sH * 0.44f,tp1);
        canvas.drawText("Back Button to Pause: ",sW*0.1f,sH * 0.54f,tp1);
        canvas.drawText("View Highscores",sW*0.1f,sH * 0.64f,paint);
        canvas.drawText("View Controls",sW*0.1f,sH * 0.74f,paint);

        //canvas.drawText("slider 1 rect right: "+slider1Rect.right, 500,450,paint);
        //canvas.drawText("slider 1 rect left: "+slider1Rect.left, 500,500,paint);
        //canvas.drawText("slider 2 rect right: "+slider2Rect.right, 500,550,paint);
        //canvas.drawText("slider 2 rect left: "+slider2Rect.left, 500,600,paint);
        //canvas.drawText("slider 1 X: "+slider1X, 500,650,paint);
        //canvas.drawText("slider 2 X: "+slider2X, 500,700,paint);

        //SHAPES
        // slider 1 color rectangle
        canvas.drawRect(slider1Rect.left,slider1Rect.top,slider1X,slider1Rect.bottom, sliderPaint);
        //slider 2 color rectangle
        canvas.drawRect(slider2Rect.left,slider2Rect.top,slider2X,slider2Rect.bottom, sliderPaint);
        // disable on-screen pause rectangle
        canvas.drawRect(tickRect,rectPaint);
        canvas.drawRect(disableOnScreenPauseRect,rectPaint);
        canvas.drawRect(backButtonPauseRect,rectPaint);
        // back button pause rectangle
        canvas.drawRect(tickRect2,rectPaint);
        //experimental
        //canvas.drawRect(slider1Rect,rectPaint);
        //canvas.drawRect(slider2Rect,rectPaint);

        //BITMAPS
        canvas.drawBitmap(Assets.slider,soundEffectsRect.right,((soundEffectsRect.top+soundEffectsRect.height()/2)-Assets.slider.getHeight()/2)+scale,null);
        canvas.drawBitmap(Assets.slider,musicRect.right,((musicRect.top+musicRect.height()/2)-Assets.slider.getHeight()/2)+scale,null);
        canvas.drawBitmap(Assets.sliderbolt,slider1X,(soundEffectsRect.top+soundEffectsRect.height()/2)-(Assets.sliderbolt.getHeight()/2),null);
        canvas.drawBitmap(Assets.sliderbolt,slider2X,(musicRect.top+musicRect.height()/2)-(Assets.sliderbolt.getHeight()/2),null);

        //canvas.drawRect(tickRect,rectPaint);
        checkBothSliderXValues();
        currentSoundSliderValue = ((slider1X-slider1Rect.left)/slider1Rect.width())*(TOTAL_NUMBER_OF_STEPS-1);
        currentMusicSliderValue = ((slider2X-slider2Rect.left)/slider2Rect.width())*(TOTAL_NUMBER_OF_STEPS-1);
        checkBothVolumeSliderValues();
        TitleActivity.currentSoundVolume = Assets.manageVolume(currentSoundSliderValue)/10f; //dividing by a factor of 10 coz max volume of SoundPool Objects is TOO DAMN HIGH!! ;
        TitleActivity.currentMusicVolume = Assets.manageVolume(currentMusicSliderValue);
        setSoundAndMusicVolume();
        //move the stars
        moveStars();
        //repeat the stars
        repeatStars();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float X = event.getX();
        float Y = event.getY();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                if(slider1RectCopy.contains(X,Y) || bitmapToRect(Assets.sliderbolt,slider1X,(soundEffectsRect.top+soundEffectsRect.height()/2)-(Assets.sliderbolt.getHeight()/2)).contains(X,Y)){
                    slider1X = X;
                }
                if(slider2RectCopy.contains(X,Y) || bitmapToRect(Assets.sliderbolt,slider2X,(musicRect.top+musicRect.height()/2)-(Assets.sliderbolt.getHeight()/2)).contains(X,Y)){
                    slider2X = X;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(slider1RectCopy.contains(X,Y) || bitmapToRect(Assets.sliderbolt,slider1X,(soundEffectsRect.top+soundEffectsRect.height()/2)-(Assets.sliderbolt.getHeight()/2)).contains(X,Y)){
                    slider1X = X;
                }
                if(slider2RectCopy.contains(X,Y) || bitmapToRect(Assets.sliderbolt,slider2X,(musicRect.top+musicRect.height()/2)-(Assets.sliderbolt.getHeight()/2)).contains(X,Y)){
                    slider2X = X;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(slider1RectCopy.contains(X,Y) || bitmapToRect(Assets.sliderbolt,slider1X,(soundEffectsRect.top+soundEffectsRect.height()/2)-(Assets.sliderbolt.getHeight()/2)).contains(X,Y)){
                    slider1X = X;
                }
                if(slider2RectCopy.contains(X,Y) || bitmapToRect(Assets.sliderbolt,slider2X,(musicRect.top+musicRect.height()/2)-(Assets.sliderbolt.getHeight()/2)).contains(X,Y)){
                    slider2X = X;
                }
                break;
        }
        invalidate();
        return true;
    }
    private void checkBothVolumeSliderValues(){
        if (currentSoundSliderValue >= (TOTAL_NUMBER_OF_STEPS-1)){
            currentSoundSliderValue = TOTAL_NUMBER_OF_STEPS-1;
        }
        if (currentSoundSliderValue <= 0){
            currentSoundSliderValue = 0;
        }
        if (currentMusicSliderValue >= (TOTAL_NUMBER_OF_STEPS-1)){
            currentMusicSliderValue = TOTAL_NUMBER_OF_STEPS-1;
        }
        if (currentMusicSliderValue <= 0){
            currentMusicSliderValue = 0;
        }
    }
    private void setSoundAndMusicVolume(){
        currentSoundVolume = Assets.manageVolume(currentSoundSliderValue);
        currentMusicVolume = Assets.manageVolume(currentMusicSliderValue);
        Assets.mp.setVolume(TitleActivity.currentMusicVolume,TitleActivity.currentMusicVolume);
        //Sound pool values have to be individually set unlike Media player values.
        //So we'll simply use the currentSoundVolume variable whereever we need to play a SoundPool Object.

    }
    private void checkBothSliderXValues(){
        if(slider1X <= slider1Rect.left){
            slider1X = slider1Rect.left;
        }
        if(slider2X <= slider2Rect.left){
            slider2X = slider2Rect.left;
        }
        if(slider1X >= slider1Rect.right){
            slider1X = slider1Rect.right;
        }
        if(slider2X >= slider2Rect.right){
            slider2X = slider2Rect.right;
        }

    }
    private RectF stringRect(String string,float stringX,float stringY, TextPaint textPaint,Paint paint ,boolean centerAlignment){
        Rect stringBounds = new Rect();
        if(textPaint != null && paint == null) {
            textPaint.getTextBounds(string, 0, string.length(), stringBounds);
        }else if(paint != null && textPaint == null){
            paint.getTextBounds(string,0,string.length(),stringBounds);
        }
        RectF stringRect = new RectF();
        if(centerAlignment){
            stringRect.set((stringX)-(stringBounds.width() / 2f), stringY - stringBounds.height(),(stringX-(stringBounds.width()/2f))+(stringBounds.width()),stringY );
        }else{
            stringRect.set(stringX,stringY-stringBounds.height(),stringX+stringBounds.width(),stringY);
        }
        return stringRect;
    }
    public RectF bitmapToRect(Bitmap bitmap, float bitmapStartX, float bitmapStartY){
        RectF outRect = new RectF();
        outRect.set(bitmapStartX,bitmapStartY,bitmapStartX+bitmap.getWidth(),bitmapStartY+bitmap.getHeight());
        return outRect;
    }
    public void moveStars(){
        for(int i=0;i<no_of_stars*2;i++) {
            genStars[c] -= 7*(scale/1.5);
            c += 2;
            if(c>((no_of_stars*2)-2)){
                c=0;
            }
        }
    }
    public void repeatStars(){
        for(int d=0;d<no_of_stars*2;d+=2){
            if(genStars[d] < 0){
                genStars[d] = sW;
                if(d>((no_of_stars*2) -2)){
                    d=0;
                }
            }
        }
    }
}
