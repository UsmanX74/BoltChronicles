package com.example.usman.myapp3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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


public class StoreView extends View{
    private Paint white,black,starPaint,uiPaint;
    private TextPaint tp;
    private TitleActivity t;
    private final float TAB_TEXT_LIMIT = 33*scale;
    float tabTextX,reverseTabTextX,cx,cy;
    int tabIndex = 1;
    private boolean equip,buy;
    private Random generator;
    private Bitmap[] items;
    private Bitmap[] upgrades;
    private Bitmap[] ships;
    int no_of_stars= 230;
    float [] genStars;
    int a=0;
    int b=1;
    int c=0;
    Matrix coinMatrix;
    RectF rect;
    Rect rect1;


    public StoreView(Context context) {
        super(context);
        ships = new Bitmap[]{};
        items = new Bitmap[]{Assets.shield,Assets.turret};
        coinMatrix = new Matrix();
        coinMatrix.setScale(0.55f*SCALE_CONST,0.55f*SCALE_CONST);
        coinMatrix.postTranslate(cx,cy);
        //coinMatrix.postScale(1,-1);
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
        starPaint = new Paint();
        starPaint.setColor(Color.WHITE);
        starPaint.setStrokeWidth(scale+1f);
        tp = new TextPaint();
        tp.setColor(Color.argb(255,229,244,255));
        tp.setTextAlign(Paint.Align.CENTER);
        tp.setTypeface(Assets.tf);
        tp.setTextSize(70*SCALE_CONST);
        t = new TitleActivity();
        uiPaint = new Paint();
        uiPaint.setStyle(Paint.Style.STROKE);
        uiPaint.setStrokeCap(Paint.Cap.ROUND);
        uiPaint.setStrokeWidth(6*scale);
        uiPaint.setColor(Color.argb(200,0,0,0));
        rect = new RectF();
        rect1 = new Rect();
        white = new Paint();
        black = new Paint();
        black.setColor(Color.BLACK);
        black.setStrokeWidth(5*SCALE_CONST);
        black.setStrokeCap(Paint.Cap.ROUND);
        black.setStyle(Paint.Style.STROKE);
        white.setColor(Color.rgb(229,244,255));
        white.setTextSize(30*SCALE_CONST);
        white.setTypeface(Assets.tf);
        white.setTextAlign(Paint.Align.CENTER);
        rect1.set(20,20,100,100);
        rect.set((sW/2)-(0.25f*sW),sH*0.27f-(0.05f*sH),(sW/2)+(0.25f*sW),(sH*0.61f)+Assets.storeTab.getHeight()+(0.05f*sH));
        //tabTextX = (Assets.storeTab.getWidth()-TAB_TEXT_LIMIT);
        tabTextX = (Assets.storeTab.getWidth()-TAB_TEXT_LIMIT)/2;
        reverseTabTextX = sW - tabTextX;
        t.totalCoins = Assets.stringToNum(Assets.decrypt(
                (TitleActivity.preferences.getString(Assets.encrypt("TotalCoins"),Assets.encrypt("0")))));
    }//640-256, 360-144, 640+256, 360+144
    //512, 288

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawRGB(0, 138, 111);
        canvas.drawPoints(genStars, starPaint);
        canvas.drawText("STORE",sW/2f,(sH*0.12f),tp);
        canvas.drawText("Coins: "+t.totalCoins,(float)(sW*0.079),(float)(sH*0.085),white);
        canvas.drawLine(0,sH*0.13f,sW*0.22f,sH*0.13f,uiPaint);
        canvas.drawLine(sW*0.22f,sH*0.13f,sW*0.33f,0,uiPaint);
        canvas.drawLine(sW,sH*0.13f,sW-(sW*0.22f),sH*0.13f,uiPaint);
        canvas.drawLine(sW-(sW*0.22f),sH*0.13f,sW-(sW*0.33f),0,uiPaint);
        canvas.drawRect(rect,uiPaint);
        canvas.drawBitmap(Assets.next,(sW/2+(0.08f*sW)),(rect.bottom)+(0.05f*sH),null);
        canvas.drawBitmap(Assets.previous,(sW/2)-(0.08f*sW)-(Assets.previous.getWidth()),(rect.bottom)+(0.05f*sH),null);
        canvas.drawBitmap(Assets.coin,coinMatrix,null);
        if(tabIndex==1){
            canvas.drawBitmap(Assets.storeTabSelect,0,sH*0.27f,null);
        }else if(tabIndex==2){
            canvas.drawBitmap(Assets.storeTabSelect,0,sH*0.44f,null);
        }else if(tabIndex==3){
            canvas.drawBitmap(Assets.storeTabSelect,0,sH*0.61f,null);
        }
        if(buy){
            canvas.drawBitmap(Assets.storeTabSelectReverse,sW-Assets.storeTabReverse.getWidth(),sH*0.27f,null);
        }
        if(equip){
            canvas.drawBitmap(Assets.storeTabSelectReverse,sW-Assets.storeTabReverse.getWidth(),sH*0.44f,null);
        }
        canvas.drawBitmap(Assets.storeTab,0,sH*0.27f,null);
        canvas.drawText("ships",tabTextX,(sH*0.27f)+(Assets.storeTab.getHeight()/2)+white.getTextSize()/3,white);
        canvas.drawBitmap(Assets.storeTab,0,sH*0.44f,null);
        canvas.drawText("items",tabTextX,sH*0.44f+(Assets.storeTab.getHeight()/2)+white.getTextSize()/3,white);
        canvas.drawBitmap(Assets.storeTab,0,sH*0.61f,null);
        canvas.drawText("upgrades",tabTextX,sH*0.61f+(Assets.storeTab.getHeight()/2)+white.getTextSize()/3,white);
        canvas.drawBitmap(Assets.storeTabReverse,sW-Assets.storeTabReverse.getWidth(),sH*0.27f,null);
        canvas.drawText("buy",reverseTabTextX,(sH*0.27f)+(Assets.storeTab.getHeight()/2)+white.getTextSize()/3,white);
        canvas.drawBitmap(Assets.storeTabReverse,sW-Assets.storeTabReverse.getWidth(),sH*0.44f,null);
        canvas.drawText("equip",reverseTabTextX,(sH*0.44f)+(Assets.storeTab.getHeight()/2)+white.getTextSize()/3,white);
        //canvas.drawBitmap(Assets.coin,300,300,null);
        //canvas.drawText("coin height/2 = "+Assets.coin.getHeight()/2,400,300,white);
        //coinMatrix.postScale(1,-1);
        //canvas.drawBitmap(Assets.storeTabSelect,0,300,null);//25,206,234 rgb
        //canvas.drawRect(rect,black);
        //canvas.drawBitmap(Assets.next,(((sW/2f)+sW*0.2f)+sW*0.1f)-Assets.next.getWidth(),(rect.top+rect.height()/2)-(Assets.next.getHeight()/2),null);
        //canvas.drawBitmap(Assets.previous,((sW/2f)-sW*0.2f)-sW*0.1f,(rect.top+rect.height()/2)-(Assets.previous.getHeight()/2),null);
        //canvas.drawBitmap(Assets.turret,(sW/2f)-Assets.turret.getWidth()/2,(sH/2f)-Assets.turret.getHeight()/2,null);
        moveStars();
        repeatStars();
        invalidate();
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
                if(d>((no_of_stars*2) -2)) d=0;
            }
        }
    }
    void aBunchOfCommentedOutCode(){
         /*
    void drawLineAsNeon(Canvas canvas, float x1,float y1, float x2, float y2, int Color,Paint paint, int tubeWidth, int glowWidth){
        paint.setAntiAlias(true);
        paint.setColor(Color);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(glowWidth);
        blurMaskFilter = new BlurMaskFilter(glowWidth,BlurMaskFilter.Blur.NORMAL);
        paint.setMaskFilter(blurMaskFilter);
        paint.setAlpha(255);
        canvas.drawLine(x1,y1,x2,y2,paint);

        paint.setAlpha(255);
        paint.setStrokeWidth(tubeWidth);
        bmf2 = new BlurMaskFilter(tubeWidth, BlurMaskFilter.Blur.SOLID);
        paint.setMaskFilter(bmf2);
        canvas.drawLine(x1,y1,x2,y2,paint);
    }
    void drawRectAsNeon(Canvas canvas, RectF rect, int Color,Paint paint, int tubeWidth, int glowWidth){
        paint.setAntiAlias(true);
        paint.setColor(Color);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(glowWidth);
        paint.setStyle(Paint.Style.STROKE);
        blurMaskFilter = new BlurMaskFilter(glowWidth,BlurMaskFilter.Blur.NORMAL);
        paint.setMaskFilter(blurMaskFilter);
        paint.setAlpha(255);
        canvas.drawRect(rect,paint);

        paint.setAlpha(255);
        paint.setStrokeWidth(tubeWidth);
        bmf2 = new BlurMaskFilter(tubeWidth, BlurMaskFilter.Blur.SOLID);
        paint.setMaskFilter(bmf2);
        canvas.drawRect(rect,paint);
    }
    void drawNeonRect(Canvas canvas, RectF rect, Paint glowPaint, Paint tubePaint){
        canvas.drawRect(rect,glowPaint);
        canvas.drawRect(rect,tubePaint);
                glowPaint = new Paint();
        glowPaint.setAntiAlias(true);
        glowPaint.setColor(Color.RED);
        glowPaint.setStyle(Paint.Style.STROKE);
        glowPaint.setStrokeCap(Paint.Cap.ROUND);
        glowPaint.setStrokeWidth(30);
        blurMaskFilter = new BlurMaskFilter(30,BlurMaskFilter.Blur.NORMAL);
        glowPaint.setMaskFilter(blurMaskFilter);
        tubePaint = new Paint();
        tubePaint.setColor(Color.RED);
        tubePaint.setStyle(Paint.Style.STROKE);
        tubePaint.setStrokeWidth(10);
        bmf2 = new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID);
        tubePaint.setMaskFilter(bmf2);


    }
    */
    }
    public RectF bitmapToRect(Bitmap bitmap, float bitmapStartX, float bitmapStartY){
        RectF outRect = new RectF();
        outRect.set(bitmapStartX,bitmapStartY,bitmapStartX+bitmap.getWidth(),bitmapStartY+bitmap.getHeight());
        return outRect;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float X = event.getX();
        float Y = event.getY();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                if((bitmapToRect(Assets.storeTab,0,sH*0.27f)).contains(X,Y)){
                    tabIndex = 1;
                }
                if((bitmapToRect(Assets.storeTab,0,sH*0.44f)).contains(X,Y)){
                    tabIndex = 2;
                }
                if((bitmapToRect(Assets.storeTab,0,sH*0.61f)).contains(X,Y)){
                    tabIndex = 3;
                }
                if((bitmapToRect(Assets.storeTabReverse,sW-Assets.storeTabReverse.getWidth(),sH*0.27f)).contains(X,Y)){
                    buy = true;
                }
                if((bitmapToRect(Assets.storeTabReverse,sW-Assets.storeTabReverse.getWidth(),sH*0.44f)).contains(X,Y)){
                    equip = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                buy = false;
                equip = false;
                break;
        }
        invalidate();
        return true;
    }
}

