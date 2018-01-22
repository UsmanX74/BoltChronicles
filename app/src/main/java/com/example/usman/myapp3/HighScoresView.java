package com.example.usman.myapp3;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

import static com.example.usman.myapp3.TitleActivity.sH;
import static com.example.usman.myapp3.TitleActivity.sW;
import static com.example.usman.myapp3.TitleView.SCALE_CONST;
import static com.example.usman.myapp3.TitleView.scale;

public class HighScoresView extends View{
    String resetHighscores = "Reset HighScores";
    TextPaint tp,tp1;
    Rect resetBounds;
    Rect resetRect;
    int no_of_stars= 230;
    float [] genStars;
    int a=0;
    int b=1;
    int c=1;
    Random generator;
    Paint paint;

    public HighScoresView(Context context){
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
        resetBounds = new Rect();
        resetRect = new Rect();
        tp = new TextPaint();
        tp1 = new TextPaint();
        tp.setTextAlign(Paint.Align.CENTER);
        tp.setColor(Color.WHITE);
        tp1.setColor(Color.WHITE);
        tp.setTextSize((54*SCALE_CONST));
        tp1.setTextSize((28*SCALE_CONST));
        tp.getTextBounds(resetHighscores,0,resetHighscores.length(),resetBounds);
        resetRect.set((int)((sW/2f)-(resetBounds.width()/2f)),(int)((sH*0.85)-(resetBounds.height())),
                (int)((sW/2f)-(resetBounds.width()/2f)+(resetBounds.width())),(int)((sH*0.85)));
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawRGB(45,0,45);
        canvas.drawText("HighScore 1: "+TitleActivity.preferences.getInt("highscore",0),150,(float)(sH*0.24),tp1);
        canvas.drawText("HighScore 2: "+TitleActivity.preferences.getInt("highscore1",0),150,(float)(sH*0.30),tp1);
        canvas.drawText("HighScore 3: "+TitleActivity.preferences.getInt("highscore2",0),150,(float)(sH*0.36),tp1);
        canvas.drawText(resetHighscores, sW/2f,(float)(sH*0.85),tp);
        canvas.drawPoints(genStars, paint);
        //move the stars
        moveStars();
        //repeat the stars
        repeatStars();
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        int eventaction = event.getAction();
        int X = (int) event.getX();
        int Y = (int) event.getY();
        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if(resetRect.contains(X,Y)){
                    resetHighscore();
                    Toast.makeText(getContext(),"Highscores have been reset",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        invalidate();
        return true;
    }



    public void resetHighscore(){
        TitleActivity.preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = TitleActivity.preferences.edit();
        editor.putInt("prevHighScore",0);
        editor.putInt("HighScore",0);
        editor.putInt("highscore",0);
        editor.putInt("highscore1",0);
        editor.putInt("highscore2",0);
        editor.putInt("potentialHighscore",0);
        TitleActivity.setHighScore(0);
        TitleActivity.setHighScore1(0);
        TitleActivity.setHighScore2(0);
        editor.apply();
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
