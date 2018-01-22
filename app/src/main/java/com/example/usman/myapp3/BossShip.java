package com.example.usman.myapp3;

import android.graphics.Rect;
import java.util.ArrayList;

import static com.example.usman.myapp3.GameView.scale;

public class BossShip {
    private int vX;
    private int vY;
    private int speedY = (int)(10*scale/1.5);
    private int speedX = (int)(2*scale/1.5);
    public static int health = 100;
    private ArrayList<BossProjectiles> bossProjectiles = new ArrayList<BossProjectiles>();
    private Rect r = new Rect();
    private boolean visible;
    private int i = 0;
    private int n;
    Boltship bs;

    public BossShip(int startX, int startY){
        vX = startX;
        vY = startY;
        health = 100;
        r.set(0, 0, 0, 0);
        n=vX;
    }
    public void init(){
        health = 100;
        speedX = (int)(2*scale/1.5);
        speedY = (int)(10*scale/1.5);
        r.set(0,0,0,0);
        GameView.setBringBoss(false);
    }

    public void update(){
        r.set(vX,vY,(vX+Assets.bossShip.getWidth()),(vY+Assets.bossShip.getHeight()));
        if(!inPosition()){
            vX -= speedX;
        }
        if(inPosition()){
            setSpeedX(0);
            if (GameView.isBossUp()){
                vY -= speedY;
            }
            if(GameView.isBossDown()){
                vY += speedY;
            }
        }
        if(health == 0 || health == -10){
            visible = false;
            vX = -600;
            vY= -600;
            GameView.setBringBoss(false);
            MainActivity.setScore(MainActivity.getScore() + 400);
        }
        i++;
    }
    public void die(){
        visible = false;

    }

    public void shoot(){
        BossProjectiles bp = new BossProjectiles((int)(vX+Assets.bossShip.getWidth()*0.1),(int)(vY+Assets.bossShip.getHeight()*0.2));
        bossProjectiles.add(bp);
    }
    public void specialAttack(){
        vX-=170;
    }
    public void goBack(){
        vX += 170;
    }

    public void follow(){
        if(i==0 || i==1 || vY < (-Assets.bossShip.getHeight() + (Assets.bossShip.getHeight()*0.1))){
            GameView.setBossUp(false);
            GameView.setBossDown(true);
        }
        if(vY >= (MainActivity.getsY() + (Assets.bossShip.getHeight()*0.1))){
            GameView.setBossDown(false);
            GameView.setBossUp(true);
        }
    }

    public boolean inPosition(){
        if(vX <= MainActivity.getsX()-Assets.bossShip.getWidth()){
            return true;
        }
        return false;
    }

    public int getvY() {
        return vY;
    }
    public int getvX() {
        return vX;
    }
    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }
    public Rect getR() {
        return r;
    }
    public void setR(Rect r) {
        this.r = r;
    }
    public ArrayList<BossProjectiles> getBossProjectiles() {
        return bossProjectiles;
    }
}