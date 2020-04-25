package com.example.usman.myapp3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

import static com.example.usman.myapp3.TitleActivity.sH;
import static com.example.usman.myapp3.TitleActivity.sW;
import static com.example.usman.myapp3.TitleView.SCALE_CONST;
import static com.example.usman.myapp3.TitleView.scale;

public class CreditsView extends View{

    TextPaint tp,tp1,tp2;
    private int logo = 0;
    private boolean betaTestersScreen = false,creditsScreen = true;
    public boolean isCreditsScreen(){
        return this.creditsScreen;
    }
    public boolean isBetaTestersScreen(){
        return this.betaTestersScreen;
    }
    public void setCreditsScreen(boolean visible){
        this.creditsScreen = visible;
    }
    public void setBetaTestersScreen(boolean visible){
        this.betaTestersScreen = visible;
    }
    String developed = "Developed And Produced By:";
    Rect developedBounds;
    RectF betaTestersRect, backButtonRect;
    int no_of_stars= 230;
    float [] genStars;
    int a=0;
    int b=1;
    int c=1;
    float backX = (sW*0.05f);
    float backY = (sH*0.09f);
    private Random generator;
    Paint paint;
    public CreditsView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(34*SCALE_CONST);
        paint.setTypeface(Assets.tf);
        paint.setStrokeWidth(scale+0.5f);
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
        developedBounds = new Rect();
        tp = new TextPaint();
        tp1 = new TextPaint();
        tp2 = new TextPaint();
        tp.setTypeface(Assets.tf);
        tp1.setTypeface(Assets.tf);
        tp2.setTypeface(Assets.tf);
        tp.setTextAlign(Paint.Align.CENTER);
        //tp.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD_ITALIC));
        tp.setTextSize(70*SCALE_CONST);
        tp.setColor(Color.GREEN);
        tp1.setColor(Color.rgb(190,200,200));
        tp1.setTextSize(28*SCALE_CONST);
        tp1.getTextBounds((developed+"a"),0,(developed+"a").length(),developedBounds);
        tp2.setTextSize(34*SCALE_CONST);
        tp2.setColor(Color.rgb(255,81,66));
        tp2.setTextAlign(Paint.Align.CENTER);
        tp2.setFakeBoldText(true);
        betaTestersRect = stringRect("View Beta Testers",sW/2f,sH*0.91f,tp2,null,true);
        //backButtonRect = stringRect("Back",sW*0.05f,sH*0.15f,tp1,null,false);
        backButtonRect = bitmapToRect(Assets.back,backX,backY);

    }


    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if(!betaTestersScreen && creditsScreen) {
            canvas.drawARGB(255,77, 147, 180);
            canvas.drawText("CREDITS", sW / 2f, sH * 0.12f, tp);
            canvas.drawText(developed, sW * 0.065f, sH * 0.3f, tp1);
            if (logo == 0) {
                canvas.drawBitmap(Assets.logo, (sW * 0.065f) + developedBounds.width(), (sH * 0.29f) - (developedBounds.height()), null);
            } else if (logo == 1) {
                canvas.drawBitmap(Assets.blackLogo, (sW * 0.065f) + developedBounds.width(), (sH * 0.29f) - (developedBounds.height()), null);
            }
            canvas.drawText("Lead Designer And Programmer: Juggernaut", (sW * 0.055f), sH * 0.5f, tp1);
            canvas.drawText("Animation and Graphics: Juggernaut", (sW * 0.055f), sH * 0.57f, tp1);
            canvas.drawText("Sound FX: bfxr.net", (sW * 0.055f), sH * 0.64f, tp1);
            canvas.drawText("Background Music: The Whip - Kevin MacLeod (incompetech.com)", (sW * 0.055f), sH * 0.71f, tp1);
            canvas.drawText("       Licensed under Creative Commons: By Attribution 3.0 License", (sW * 0.055f), sH * 0.77f, tp1);
            canvas.drawText("       http://creativecommons.org/licenses/by/3.0/", (sW * 0.055f), sH * 0.83f, tp1);
            canvas.drawText("View Beta Testers", sW / 2f, sH * 0.91f, tp2);
            canvas.drawPoints(genStars, paint);
            //move the stars
            moveStars();
            //repeat the stars
            repeatStars();
            invalidate();
        }else if(!creditsScreen && betaTestersScreen){
            canvas.drawARGB(255,77,147,190);
            canvas.drawPoints(genStars,paint);
            canvas.drawText("Beta Testers",sW/2f,sH*0.15f,tp);
            canvas.drawBitmap(Assets.back,backX,backY,null);
            //canvas.drawText("Back",sW*0.05f,sH*0.15f,paint);
            canvas.drawText("Juggernaut", (sW * 0.07f), sH * 0.30f, tp1);
            canvas.drawText("DeathBeeo", (sW * 0.07f), sH * 0.38f, tp1);
            canvas.drawText("Horny Psycho", (sW * 0.07f), sH * 0.46f, tp1);
            canvas.drawText("Zaptek", (sW * 0.07f), sH * 0.54f, tp1);
            canvas.drawText("Zayn", (sW * 0.07f), sH * 0.62f, tp1);
            canvas.drawText("Rafay",(sW * 0.07f), sH * 0.70f, tp1);
            canvas.drawText("Biscuit", (sW * 0.07f), sH * 0.78f, tp1);
            canvas.drawText("Inferno", (sW * 0.07f), sH * 0.86f, tp1);
            canvas.drawText("Kori", (sW * 0.07f), sH * 0.94f, tp1);
            moveStars();
            repeatStars();
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        float X = event.getX();
        float Y = event.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                if(bitmapToRect(Assets.blackLogo,(sW * 0.065f) + developedBounds.width(), (sH * 0.29f) - (developedBounds.height())).contains(X,Y)) {
                    logo = 1;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(bitmapToRect(Assets.blackLogo,(sW * 0.065f) + developedBounds.width(), (sH * 0.29f) - (developedBounds.height())).contains(X,Y)) {
                    logo = 1;
                }else{
                    logo = 0;
                }
                break;
            case MotionEvent.ACTION_UP:
                logo = 0;
                if(betaTestersRect.contains(X,Y) && creditsScreen && !betaTestersScreen){
                    //Toast.makeText(getContex
                    // t(),"game is not in beta phase yet",Toast.LENGTH_LONG).show();
                    creditsScreen = false;
                    betaTestersScreen = true;
                }
                if(backButtonRect.contains(X,Y) && !creditsScreen && betaTestersScreen){
                    creditsScreen = true;
                    betaTestersScreen = false;
                }
                break;
        }
        invalidate();
        return true;
    }

    public void moveStars(){
        for(int i=0;i<no_of_stars*2;i++){
            genStars[c] += 7*(scale/1.5);
            c += 2;
            if(c>((no_of_stars*2)-1)){
                c=1;
            }
        }
    }
    public void repeatStars(){
        for(int d=1; d<no_of_stars*2; d+=2){
            if(genStars[d] > sH){
                genStars[d] = 0;
                if(d>((no_of_stars*2) -1)){
                    d=1;
                }
            }
        }
    }
    public RectF bitmapToRect(Bitmap bitmap, float bitmapStartX, float bitmapStartY){
        RectF outRect = new RectF();
        outRect.set(bitmapStartX,bitmapStartY,bitmapStartX+bitmap.getWidth(),bitmapStartY+bitmap.getHeight());
        return outRect;
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

    //rgb color applet: https://yuilibrary.com/yui/docs/color/rgb-slider.html
    //https://www.youtube.com/watch?v=ImjX7O6PN5A

}
