package com.example.usman.myapp3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class TitleView extends View {
    Context myContext;
    static int viewIndex = 0, sound = 1;
    private int titleclick =0;
    private static int screenW, screenH;
    static float scale;
    public static boolean isBringTurret() {
        return bringTurret;
    }
    public static void setBringTurret(boolean bringTurret) {
        TitleView.bringTurret = bringTurret;
    }
    private Random generator;
    public static float SCALE_CONST;
    String title="BOLT CHRONICLES",play = "PLAY", options="OPTIONS",store="STORE",credits="CREDITS";
    int no_of_stars= 230;
    float [] genStars;
    int a=0;
    int b=1;
    int c=0;
    public static boolean bringTurret = false;
    Paint paint;
    TextPaint buttonPaint,titlePaint;
    Intent intent;
    Intent myintent;

    public TitleView(Context context) {
        super(context);
        //vs = new ViewSwitcher(context);
        screenW = TitleActivity.sW;
        screenH = TitleActivity.sH;
        scale = context.getResources().getDisplayMetrics().density;
        SCALE_CONST = (scale/1.5f);
        myContext = context;
        //vs.addView(iView,0);
        paint = new Paint();
        buttonPaint = new TextPaint();
        titlePaint = new TextPaint();
        myintent = new Intent(context,ButtonActivity.class);
        paint.setTextSize((float)(24*(scale/1.5)));
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(scale);
        buttonPaint.setTypeface(Assets.tf1);
        buttonPaint.setTextSize(70*SCALE_CONST);
        buttonPaint.setColor(Color.RED);
        buttonPaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Assets.tf);
        titlePaint.setTextSize(70*SCALE_CONST);
        titlePaint.setColor(Color.RED);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        generator = new Random();
        genStars= new float[no_of_stars*2];//Star Co-Ordinates
        for(int i=0; i<no_of_stars; i++){
            genStars[a]=generator.nextInt(screenW);
            a += 2;
        }
        for(int i=0; i<no_of_stars; i++){
            genStars[b]=generator.nextInt(screenH);
            b += 2;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenW = w;
        screenH = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(255, 0, 0, 43);

        //canvas.drawText("Right side = new features testing,    Left side = normal game.",30,50,paint);
        //canvas.drawText("tap on either the left side or right side of the screen",30,90,paint);
        canvas.drawText(title,(float)(screenW*0.5),(float)(screenH*0.12),titlePaint);
        //canvas.drawBitmap(Assets.newTitle,(float)((screenW/2)-(Assets.newTitle.getWidth()/2)),(float)(screenH*0.06),null);
        canvas.drawBitmap(Assets.playButton,(float)((screenW/2)-(Assets.playButton.getWidth()/2)),(float)(screenH*0.26),null);
        canvas.drawBitmap(Assets.optionsButton,(float)((screenW/2)-(Assets.optionsButton.getWidth()/2)),(float)(screenH*0.40),null);
        canvas.drawBitmap(Assets.storeButton,(float)((screenW/2)-(Assets.storeButton.getWidth()/2)),(float)(screenH*0.54),null);
        //canvas.drawBitmap(Assets.highscoresButton,(float)((screenW/2)-(Assets.highscoresButton.getWidth()/2)),(float)(screenH*0.68),null);
        canvas.drawBitmap(Assets.creditsButton,(float)((screenW/2)-(Assets.creditsButton.getWidth()/2)),(float)(screenH*0.68),null);
        if (sound==1){
            canvas.drawBitmap(Assets.soundOn,(float)(screenW*0.93),(float)(screenH*0.06),null);
        }else if(sound==0){
            canvas.drawBitmap(Assets.soundOff,(float)(screenW*0.93),(float)(screenH*0.06),null);
        }
        //canvas.drawText("current View: "+TitleActivity.currentView,100,150,paint);
        //canvas.drawCircle(12,12,12,blue);
        //canvas.drawBitmap(Assets.title,(float)((screenW/2)-Assets.title.getWidth()/2),(float)(screenH*0.1),null);
        //canvas.drawBitmap(Assets.playButton,(float)((screenW/2)-Assets.playButton.getWidth()/2),(float)(screenH*0.3),null);
        //canvas.drawText("actual turret X: "+ TitleActivity.turret.getWidth(),350,400,paint);
        //canvas.drawText("scaled turret X: "+ TitleActivity.getScaledTurret().getWidth(),700,400,paint);
        //canvas.drawBitmap(TitleActivity.turret,200,150,null);
        //draw the stars
        canvas.drawPoints(genStars, paint);
        //move the stars
        moveStars();
        //repeat the stars
        repeatStars();
        invalidate();
        //canvas.drawBitmap(TitleActivity.getScaledTurret(),400,150,null);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int eventaction = event.getAction();
        int X = (int) event.getX();
        int Y = (int) event.getY();
        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if(withinPlayButtonBounds(X,Y)){
                    //do nothing
                }
                if(bitmapToRect(Assets.playButton,((screenW/2)-(Assets.playButton.getWidth()/2)),(screenH*0.26f)).contains(X,Y)){
                    intent = new Intent(getContext(), MainActivity.class);
                    getContext().startActivity(intent);
                    finishAct();
                }
                if(withinCreditsButtonBound(X,Y)){
                    viewIndex = 2;
                    getContext().startActivity(myintent);
                }
                if(withinOptionButtonBound(X,Y)){
                    viewIndex = 1;
                    getContext().startActivity(myintent);
                }
                if(withinStoreButtonBound(X,Y)){
                    viewIndex = 0;
                    getContext().startActivity(myintent);
                }
                /*
                if(withinHighscoresButtonBound(X,Y)){
                    viewIndex = 3;
                    getContext().startActivity(myintent);
                }
                */
                if(withinButtonBounds(X,Y,Assets.soundOn,(float)(screenW*0.93),(float)(screenH*0.06))){
                    if(sound==1){
                        sound=0;
                        Assets.mp.setVolume(0f,0f);
                    }else if(sound==0){
                        sound=1;
                        Assets.mp.setVolume(1f,1f);
                    }
                }
                break;
        }
        invalidate();
        return true;
    }

    public void finishAct(){
        ((TitleActivity)getContext()).finish();
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
                genStars[d] = screenW;
                if(d>((no_of_stars*2) -2)){
                    d=0;
                }
            }
        }
    }
    public boolean withinButtonBounds(float touchX, float touchY, Bitmap bitmap, float bitmapX, float bitmapY){
        if(touchX> bitmapX && touchX<(bitmapX+bitmap.getWidth())
                && touchY> bitmapY && touchY<(bitmapY+bitmap.getHeight())){
            return true;
        }
        return false;
    }
    public boolean withinPlayButtonBounds(int X, int Y){
        if(X>((screenW/2)-(Assets.playButton.getWidth()/2)) && X<(((screenW/2)-(Assets.playButton.getWidth()/2))+Assets.playButton.getWidth())
                 && Y>(screenH*0.26) && Y<((screenH*0.26)+(Assets.playButton.getHeight()))){
            return true;
        }
        return false;
    }
    public boolean withinOptionButtonBound(int X, int Y){
        if(X>((screenW/2)-(Assets.optionsButton.getWidth()/2)) && X<(((screenW/2)-(Assets.optionsButton.getWidth()/2))+Assets.optionsButton.getWidth())
                && Y>(screenH*0.40) && Y<((screenH*0.40)+(Assets.optionsButton.getHeight()))){
            return true;
        }
        return false;
    }
    public boolean withinStoreButtonBound(int X, int Y){
        if(X>((screenW/2)-(Assets.storeButton.getWidth()/2)) && X<(((screenW/2)-(Assets.storeButton.getWidth()/2))+Assets.storeButton.getWidth())
                && Y>(screenH*0.54) && Y<((screenH*0.54)+(Assets.storeButton.getHeight()))){
            return true;
        }
        return false;
    }
    public boolean withinHighscoresButtonBound(int X, int Y){
        if(X>((screenW/2)-(Assets.highscoresButton.getWidth()/2)) && X<(((screenW/2)-(Assets.highscoresButton.getWidth()/2))+Assets.highscoresButton.getWidth())
                && Y>(screenH*0.68) && Y<((screenH*0.68)+(Assets.highscoresButton.getHeight()))){
            return true;
        }
        return false;
    }
    public boolean withinCreditsButtonBound(int X, int Y){
        if(X>((screenW/2)-(Assets.creditsButton.getWidth()/2)) && X<(((screenW/2)-(Assets.creditsButton.getWidth()/2))+Assets.creditsButton.getWidth())
                && Y>(screenH*0.68) && Y<((screenH*0.68)+(Assets.creditsButton.getHeight()))){
            return true;
        }
        return false;
    }
    public RectF bitmapToRect(Bitmap bitmap, float bitmapStartX, float bitmapStartY){
        RectF outRect = new RectF();
        outRect.set(bitmapStartX,bitmapStartY,bitmapStartX+bitmap.getWidth(),bitmapStartY+bitmap.getHeight());
        return outRect;
    }

}




















