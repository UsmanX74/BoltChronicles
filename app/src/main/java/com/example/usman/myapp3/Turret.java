package com.example.usman.myapp3;

import android.graphics.Matrix;
import android.graphics.Rect;

import java.util.ArrayList;

import static com.example.usman.myapp3.GameView.scale;

public class Turret{

    private int tX,tY;
    public static float centerX,centerY;
    public Rect gettR() {
        return tR;
    }

    private Rect tR;
    int health = 50;
    Matrix matrix = new Matrix();
    protected static double rotationAngleinDeg, rotationAngleinRad;

    public ArrayList<TurretProjectiles> getTurretProjectiles() {
        return turretProjectiles;
    }

    private ArrayList<TurretProjectiles> turretProjectiles = new ArrayList<TurretProjectiles>();

    Turret(int startX, int startY){
        rotationAngleinRad = rotationAngleinDeg*(Math.PI/180);
        tX = startX;
        tY = startY;
        centerX = tX + Assets.turret.getHeight()/2f;
        centerY = tY + Assets.turret.getHeight()/2f;
        tR = new Rect(0,0,0,0);
    }
    void shoot(){
        rotationAngleinRad = rotationAngleinDeg*(Math.PI/180);
        TurretProjectiles tp = new TurretProjectiles((int)(Turret.centerX + (51.5*scale)*(Math.cos(rotationAngleinRad))), (int)(Turret.centerY + (51.5*scale)*(Math.sin(rotationAngleinRad))));
        turretProjectiles.add(tp);
    }

    public void update(){
        rotationAngleinRad = rotationAngleinDeg*(Math.PI/180);
        tR.set(tX,tY,(tX+Assets.turret.getWidth()),(tY+Assets.turret.getHeight()));
        if(health == 0 || health == -5){
            tX = -800;
            tY = -800;
        }
    }
}
