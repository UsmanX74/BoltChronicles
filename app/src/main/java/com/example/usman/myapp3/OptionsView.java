package com.example.usman.myapp3;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class OptionsView extends View{
    int i;
    public OptionsView(Context context) {
        super(context);
        i=0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRGB(15, 0, 100);
        i++;
        change();
        invalidate();
    }
    public void change(){
        if(i>60){
            i=0;
        }
    }
}
