package com.example.usman.myapp3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

import static com.example.usman.myapp3.TitleActivity.sH;
import static com.example.usman.myapp3.TitleActivity.sW;
import static com.example.usman.myapp3.TitleView.SCALE_CONST;
import static com.example.usman.myapp3.TitleView.scale;

public class CreditsView extends View{

    TextPaint tp,tp1,tp2;
    String developed = "Developed And Produced By:", specialCredits="View Beta Testers";
    Rect developedBounds,specialCreditsRect,specialCreditsBounds;
    int no_of_stars= 230;
    float [] genStars;
    int a=0;
    int b=1;
    int c=1;
    private Random generator;
    Paint paint;
    public CreditsView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(scale);
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
        specialCreditsBounds = new Rect();
        specialCreditsRect = new Rect();
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
        tp2.setTextSize(31*SCALE_CONST);
        tp2.setColor(Color.RED);
        tp2.setTextAlign(Paint.Align.CENTER);
        tp2.setFakeBoldText(true);
        tp2.getTextBounds(specialCredits,0,specialCredits.length(),specialCreditsBounds);
        specialCreditsRect.set((int)((sW/2f)-(specialCreditsBounds.width()/2f)),(int)((sH*0.9f)-(specialCreditsBounds.height())),
                (int)((sW/2f)-(specialCreditsBounds.width()/2f)+(specialCreditsBounds.width())),(int)((sH*0.9f)));
    }


    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawRGB(10,0,55);
        canvas.drawText("CREDITS",sW/2f,sH*0.15f,tp);
        canvas.drawText(developed,sW*0.065f,sH*0.3f,tp1);
        canvas.drawBitmap(Assets.logo,(sW*0.065f)+developedBounds.width(),(sH*0.29f)-(developedBounds.height()),null);
        canvas.drawText("Lead Designer And Programmer: Juggernaut",(sW*0.055f),sH*0.5f,tp1);
        canvas.drawText("Animation and Graphics: Juggernaut",(sW*0.055f),sH*0.57f,tp1);
        canvas.drawText("Sound FX: bfxr.net" ,(sW*0.055f),sH*0.64f,tp1);
        canvas.drawText("Background Music: The Whip - Kevin MacLeod (incompetech.com)",(sW*0.055f),sH*0.71f,tp1);
        canvas.drawText("       Licensed under Creative Commons: By Attribution 3.0 License",(sW*0.055f),sH*0.77f,tp1);
        canvas.drawText("       http://creativecommons.org/licenses/by/3.0/",(sW*0.055f),sH*0.83f,tp1);
        canvas.drawText(specialCredits,sW/2f,sH*0.9f,tp2);
        canvas.drawPoints(genStars, paint);
        //move the stars
        moveStars();
        //repeat the stars
        repeatStars();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        float X = event.getX();
        float Y = event.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if(specialCreditsRect.contains((int)X,(int)Y)){
                    Toast.makeText(getContext(),"game is not in beta phase yet",Toast.LENGTH_LONG).show();
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
}
