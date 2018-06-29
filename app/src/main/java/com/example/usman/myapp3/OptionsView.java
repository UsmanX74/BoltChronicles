package com.example.usman.myapp3;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class OptionsView extends View{
    public OptionsView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRGB(15, 0, 100);
        invalidate();
    }
}
