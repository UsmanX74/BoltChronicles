package com.example.usman.myapp3;

import android.graphics.Rect;

import static com.example.usman.myapp3.GameView.scale;

public class PepeProjectiles {
    private int x,y,speedX;

    public Rect getR() {
        return r;
    }

    public void setR(Rect r) {
        this.r = r;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    private Rect r = new Rect();
    private boolean visible;
    private int rectWidth = (int)(23*scale),rectHeight = (int)(7*scale);

    public PepeProjectiles(int startX, int startY){
        x = startX;
        y = startY;
        speedX = (int)(12*scale/1.5);
        visible = true;
        r = new Rect(x, y, rectWidth, rectHeight);
    }
    public void update(){
        x -= speedX;
        r.set(x, y, x+rectWidth, y+rectHeight);
        if((x+rectWidth)<0){
            visible = false;
        }
    }

}
