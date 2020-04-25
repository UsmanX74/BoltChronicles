package com.example.usman.myapp3;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.github.jinatonic.confetti.confetto.Confetto;

public class Particles extends Confetto {
    private final Bitmap bitmap;
    private Paint myPaint;
    PorterDuff.Mode mode = PorterDuff.Mode.ADD;
    private final float bitmapCenterX, bitmapCenterY;

    public Particles(Bitmap bitmap){
        this.bitmap = bitmap;
        this.bitmapCenterX = bitmap.getWidth() / 2f;
        this.bitmapCenterY = bitmap.getHeight() / 2f;
        myPaint = new Paint();
        myPaint.setXfermode(new PorterDuffXfermode(mode));
    }

    @Override
    public int getWidth() {
        return this.bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return this.bitmap.getHeight();
    }

    @Override
    protected void drawInternal(Canvas canvas, Matrix matrix, Paint paint, float x, float y, float rotation, float percentAnimated) {
        paint.setXfermode(new PorterDuffXfermode(mode));
        matrix.preTranslate(x, y);
        matrix.preRotate(rotation, bitmapCenterX, bitmapCenterY);
        canvas.drawBitmap(bitmap, matrix, paint);
    }
}
