package com.example.usman.myapp3;

import android.graphics.Rect;

import static com.example.usman.myapp3.GameView.scale;

public class Projectiles {
    private int x,y,speedX;
    private boolean visible;
    private Rect r;
    private int rectWidth= (int)(15*(scale/1.5));
    private int rectHeight= (int)(7*scale/1.5);


    public Projectiles(int startX, int startY){
        x = startX;
        y = startY;
        speedX = (int)(13*(scale/1.5));
        visible = true;
        r = new Rect(x, y, 200, 200);
    }
    public void update(){
        x += speedX;
        r.set(x, y, x+rectWidth, y+rectHeight);
        if (x > MainActivity.getsX()){
            visible = false;
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Rect getR() {
        return r;
    }

    public void setR(Rect r) {
        this.r = r;
    }
}


