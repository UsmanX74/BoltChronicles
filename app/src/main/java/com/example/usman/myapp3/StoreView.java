package com.example.usman.myapp3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.view.View;
import static com.example.usman.myapp3.TitleActivity.sH;
import static com.example.usman.myapp3.TitleActivity.sW;
import static com.example.usman.myapp3.TitleView.SCALE_CONST;


public class StoreView extends View{
    private Paint white,black;
    private TextPaint tp;
    private TitleActivity t;


    RectF rect;
    Rect rect1;
    public StoreView(Context context) {
        super(context);
        tp = new TextPaint();
        tp.setColor(Color.argb(255,229,244,255));
        tp.setTextAlign(Paint.Align.CENTER);
        tp.setTypeface(Assets.tf);
        tp.setTextSize(70*SCALE_CONST);
        t = new TitleActivity();
        rect = new RectF();
        rect1 = new Rect();
        white = new Paint();
        black = new Paint();
        black.setColor(Color.BLACK);
        black.setStrokeWidth(3*SCALE_CONST);
        black.setStyle(Paint.Style.STROKE);
        white.setColor(Color.WHITE);
        white.setTextSize(40*SCALE_CONST);
        rect1.set(20,20,100,100);
        white.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD_ITALIC));
        rect.set((sW/2f)-sW*0.2f,(sH/2f)-sH*0.2f,(sW/2f)+sW*0.2f,(sH/2f)+sH*0.2f);
        t.totalCoins = Assets.stringToNum(Assets.decrypt(
                (TitleActivity.preferences.getString(Assets.encrypt("TotalCoins"),Assets.encrypt("0")))));
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawRGB(0, 138, 111);
        canvas.drawText("STORE",sW/2f,(sH*0.12f),tp);
        canvas.drawBitmap(Assets.coin,(float)((sW*0.076)-(Assets.coin.getWidth())),0,null);
        canvas.drawText("Coins: "+t.totalCoins,(float)(sW*0.079),(float)(sH*0.085),white);
        canvas.drawRect(rect,black);
        //canvas.drawRect(rect1,black);
        //canvas.drawBitmap(Assets.turret,null,rect,null);
        canvas.drawBitmap(Assets.turret,(sW/2f)-Assets.turret.getWidth()/2,(sH/2f)-Assets.turret.getHeight()/2,null);
    }
}
