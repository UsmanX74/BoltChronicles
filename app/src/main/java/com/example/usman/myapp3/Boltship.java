package com.example.usman.myapp3;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import static com.example.usman.myapp3.GameView.scale;

public class Boltship {

    private Rect playerRect = new Rect();
    private int bX= 0,bY = 0,speedY = (int)(8*scale/1.5),health = 220;
    private ArrayList<Projectiles> projectiles = new ArrayList<Projectiles>();

    public Rect getPlayerRect() {
        return playerRect;
    }
    public  int getbY() {
        return bY;
    }
    public int getbX() {
        return bX;
    }
    public ArrayList<Projectiles> getProjectiles() {
        return projectiles;
    }

    public Boltship(){
        playerRect.set(0,0,0,0);
    }

    public void shoot() {
        Projectiles p = new Projectiles(Assets.boltShip.getWidth()-3,(bY + (int)(Assets.boltShip.getHeight()*0.74)));
        projectiles.add(p);
        //volume should be between 0.01-0.04
        Assets.sp.play(Assets.playerShoot2,0.04f,0.04f,1,0,1);
   }

    public void update(){
        if (GameView.isMoveUp()){
            bY -= speedY;
        }
        if (GameView.isMoveDown()){
            bY += speedY;
        }
        playerRect.set(bX,bY,bX + Assets.boltShip.getWidth(),bY + Assets.boltShip.getHeight());
        if (bY >= MainActivity.getsY()- (Assets.boltShip.getHeight())){
            bY = MainActivity.getsY()-(Assets.boltShip.getHeight());
        }
        if (bY <= (int)(-Assets.boltShip.getHeight()+Assets.boltShip.getHeight()*0.28)){
            bY = (int)(-Assets.boltShip.getHeight()+ Assets.boltShip.getHeight()*0.28);
        }
    }
}