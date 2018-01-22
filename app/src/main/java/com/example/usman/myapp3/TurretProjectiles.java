package com.example.usman.myapp3;

import android.graphics.Rect;

import static com.example.usman.myapp3.GameView.scale;
import static com.example.usman.myapp3.Turret.rotationAngleinRad;

public class TurretProjectiles{

    private int tpX;
    private int tpY;
    private boolean visible;
    private Rect r;
    private float speed=(float)(13*(scale/1.5)),speedY,speedX;
    private int rectWidth=(int)(10*(scale/1.5)),rectHeight=(int)(10*(scale/1.5));

    public TurretProjectiles(int startX, int startY){
        tpX = startX;
        tpY = startY;
        r = new Rect(0,0,0,0);
        speedX = (float)(speed*Math.cos(rotationAngleinRad));
        speedY = (float)(speed*Math.sin(rotationAngleinRad));
        visible = true;
    }

    public void update(){
        //rotationAngleinRad = rotationAngleinDeg*(Math.PI/180);
        //speedX = (float)(speed*Math.cos(rotationAngleinRad));
        //speedY = (float)(speed*Math.sin(rotationAngleinRad));
        //tpX = (int)(Turret.centerX + (51.5*scale)*(Math.cos(Turret.rotationAngle)));
        //tpY = (int)(Turret.centerY + (51.5*scale)*(Math.sin(Turret.rotationAngle)));
        tpX += speedX;
        tpY += speedY;
        r.set(tpX,tpY,tpX+rectWidth,tpY+rectHeight);
        if(tpX > MainActivity.getsX() || tpX < -200 || tpY < -200){
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