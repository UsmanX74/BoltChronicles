package com.example.usman.myapp3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;
import static com.example.usman.myapp3.TitleActivity.sH;
import static com.example.usman.myapp3.TitleActivity.sW;
import static com.example.usman.myapp3.TitleView.SCALE_CONST;


public class ItemsAndUpgrades extends View{
    private Paint white,black;
    private Boolean owned = false;
    int itemIndex;
    RectF rect;
    public ItemsAndUpgrades(Context context) {
        super(context);
        rect = new RectF();
        white = new Paint();
        black = new Paint();
        black.setColor(Color.BLACK);
        black.setStrokeWidth(3*SCALE_CONST);
        black.setStyle(Paint.Style.STROKE);
        white.setColor(Color.WHITE);
        white.setTextSize(40*SCALE_CONST);
        white.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD_ITALIC));
        rect.set((sW/2f)-sW*0.2f,(sH/2f)-sH*0.2f,(sW/2f)+sW*0.2f,(sH/2f)+sH*0.2f);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawRGB(40,40,200);
        canvas.drawBitmap(Assets.coin,(float)((sW*0.076)-(Assets.coin.getWidth())),0,null);
        canvas.drawText("Coins: "+TitleActivity.totalCoins,(float)(sW*0.079),(float)(sH*0.085),white);
        canvas.drawRect(rect,black);
        canvas.drawBitmap(Assets.turret,(sW/2f)-Assets.turret.getWidth()/2,(sH/2f)-Assets.turret.getHeight()/2,null);
    }
}
