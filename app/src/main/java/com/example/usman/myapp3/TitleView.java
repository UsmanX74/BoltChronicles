package com.example.usman.myapp3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

import static com.example.usman.myapp3.TitleActivity.sH;
import static com.example.usman.myapp3.TitleActivity.sW;

public class TitleView extends View {
    Context myContext;
    static int viewIndex = 0;
    public static boolean sound = true;
    private static int screenW, screenH;
    static float scale;
    public static boolean isBringTurret() {
        return bringTurret;
    }
    public static void setBringTurret(boolean bringTurret) {
        TitleView.bringTurret = bringTurret;
    }
    public static float SCALE_CONST;
    String title="BOLT CHRONICLES",play = "PLAY", options="OPTIONS",store="STORE",credits="CREDITS";
    private Random generator;
    int no_of_stars= 230;
    float [] genStars;
    int a=0;
    int b=1;
    int c=0;
    public static boolean bringTurret = false;
    Paint paint, rectPaint;
    TextPaint buttonPaint,titlePaint;
    Intent intent;
    Intent myintent;
    private Rect rect;
    private RectF playRect,optionsRect,storeRect,creditsRect;
    public TitleView(Context context) {
        super(context);
        rectPaint = new Paint();
        rectPaint.setColor(Color.BLACK);
        rectPaint.setStyle(Paint.Style.FILL);
        rect = new Rect(150,150,300,300);
        //vs = new ViewSwitcher(context);
        screenW = sW;
        screenH = sH;
        scale = context.getResources().getDisplayMetrics().density;
        SCALE_CONST = (scale/1.5f);
        myContext = context;
        //vs.addView(iView,0);
        paint = new Paint();
        buttonPaint = new TextPaint();
        titlePaint = new TextPaint();
        myintent = new Intent(context,ButtonActivity.class);
        paint.setTextSize((24*SCALE_CONST));
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(scale);
        playRect = new RectF();
        optionsRect = new RectF();
        storeRect = new RectF();
        creditsRect = new RectF();
        buttonPaint.setTypeface(Assets.tf);
        buttonPaint.setTextSize(65*SCALE_CONST);
        buttonPaint.setColor(Color.rgb(202,255,244));
        buttonPaint.setTextAlign(Paint.Align.CENTER);
        playRect = stringRect("PLAY",sW*0.5f,sH*0.33f,buttonPaint,null,true);
        optionsRect = stringRect("OPTIONS",sW*0.5f,sH*0.47f,buttonPaint,null,true);
        storeRect = stringRect("STORE",sW*0.5f,sH*0.61f,buttonPaint,null,true);
        creditsRect = stringRect("CREDITS",sW*0.5f,sH*0.75f,buttonPaint,null,true);
        titlePaint.setTypeface(Assets.tf);
        titlePaint.setTextSize(72*SCALE_CONST);
        titlePaint.setColor(Color.rgb(202,255,244));
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
        canvas.drawText(title,(float)(screenW*0.5),(float)(screenH*0.13),titlePaint);
        //canvas.drawBitmap(Assets.newTitle,(float)((screenW/2)-(Assets.newTitle.getWidth()/2)),(float)(screenH*0.06),null);
        //canvas.drawBitmap(Assets.playButton,(float)((screenW/2)-(Assets.playButton.getWidth()/2)),(float)(screenH*0.26),null);
        //canvas.drawBitmap(Assets.optionsButton,(float)((screenW/2)-(Assets.optionsButton.getWidth()/2)),(float)(screenH*0.40),null);
        //canvas.drawBitmap(Assets.storeButton,(float)((screenW/2)-(Assets.storeButton.getWidth()/2)),(float)(screenH*0.54),null);
        canvas.drawRect(rect,rectPaint);
        canvas.drawText("PLAY",sW*0.5f,sH*0.33f,buttonPaint);
        canvas.drawText("OPTIONS",sW*0.5f,sH*0.47f,buttonPaint);
        canvas.drawText("STORE",sW*0.5f,sH*0.61f,buttonPaint);
        canvas.drawText("CREDITS",sW*0.5f,sH*0.75f,buttonPaint);
        //canvas.drawBitmap(Assets.highscoresButton,(float)((screenW/2)-(Assets.highscoresButton.getWidth()/2)),(float)(screenH*0.68),null);
        //canvas.drawBitmap(Assets.creditsButton,(float)((screenW/2)-(Assets.creditsButton.getWidth()/2)),(float)(screenH*0.68),null);
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
                if(playRect.contains(X,Y)){
                    intent = new Intent(getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getContext().startActivity(intent);
                    finishAct();
                }
                if(optionsRect.contains(X,Y)){
                    viewIndex = 1;
                    Assets.counter=1;
                    myintent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    getContext().startActivity(myintent);
                }
                if(storeRect.contains(X,Y)){
                    viewIndex = 0;
                    Assets.counter=1;
                    myintent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    getContext().startActivity(myintent);
                }
                if(creditsRect.contains(X,Y)){
                    viewIndex = 2;
                    Assets.counter=1;
                    myintent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    getContext().startActivity(myintent);
                }
                if(rect.contains(X,Y)){
                    viewIndex = 3;
                    Assets.counter=1;
                    myintent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    getContext().startActivity(myintent);
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
}



/*
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
    */
/*
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
