package com.example.usman.myapp3;

import android.graphics.Matrix;
import android.graphics.Rect;

import java.util.ArrayList;

import static com.example.usman.myapp3.GameView.scale;

public class Turret{

    private int tX,tY;
    public static float centerX,centerY;
    private boolean visible;

    public Rect gettR() {
        return tR;
    }

    private Rect tR;
    public static int health = 50;
    public static Matrix matrix = new Matrix();
    public static double rotationAngleinDeg = 0, rotationAngleinRad=0;

    public ArrayList<TurretProjectiles> getTurretProjectiles() {
        return turretProjectiles;
    }

    private ArrayList<TurretProjectiles> turretProjectiles = new ArrayList<TurretProjectiles>();

    public Turret(int startX, int startY){
        rotationAngleinRad = rotationAngleinDeg*(Math.PI/180);
        tX = startX;
        tY = startY;
        centerX = tX + Assets.turret.getHeight()/2;
        centerY = tY + Assets.turret.getHeight()/2;
        tR = new Rect(0,0,0,0);
        visible = true;
    }
    public void shoot(){
        rotationAngleinRad = rotationAngleinDeg*(Math.PI/180);
        TurretProjectiles tp = new TurretProjectiles((int)(Turret.centerX + (51.5*scale)*(Math.cos(rotationAngleinRad))), (int)(Turret.centerY + (51.5*scale)*(Math.sin(rotationAngleinRad))));
        turretProjectiles.add(tp);
    }

    public void update(){
        rotationAngleinRad = rotationAngleinDeg*(Math.PI/180);
        tR.set(tX,tY,(int)(tX+Assets.turret.getWidth()),(int)(tY+Assets.turret.getHeight()));
        if(health == 0 || health == -5){
            setVisible(false);
            tX = -800;
            tY = -800;
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
