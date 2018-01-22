package com.example.usman.myapp3;


import android.graphics.Rect;
import java.util.ArrayList;
import static com.example.usman.myapp3.GameView.scale;

public class PepeBoss {
    private static int screenW;

    public static void setScreenW(int screenW) {
        PepeBoss.screenW = screenW;
    }

    public int getpX() {
        return pX;
    }

    public int getpY() {
        return pY;
    }

    private int pX,pY;
    private int speedY = (int)(9*scale/1.5);
    public int health = 100;
    private boolean visible;

    public Rect getPepeRect() {
        return pepeRect;
    }

    private Rect pepeRect = new Rect();

    public ArrayList<PepeProjectiles> getPepeProjectiles() {
        return pepeProjectiles;
    }

    private ArrayList<PepeProjectiles> pepeProjectiles = new ArrayList<PepeProjectiles>();

    public PepeBoss(int startX,int startY){
        pX = startX;
        pY = startY;
        pepeRect.set(0,0,0,0);
    }
    public void init(){
        health = 100;
        speedY = (int)(9*scale/1.5);
        pepeRect.set(0,0,0,0);
        GameView.setBringPepe(false);
    }
    public void specialAttack(){
        for(int i=0; i<300; i++){
            pX -= 2;
        }
    }
    public void goBack(){
        pX += 200;
    }

    public void update(){
        pepeRect.set(pX,pY,pX+Assets.pepeBoss.getWidth(),pY+Assets.pepeBoss.getHeight());
        if (GameView.isPepeUp()){
            pY -= speedY;
        }
        if(GameView.isPepeDown()){
            pY += speedY;
        }
        if(health<0){
            health=0;
        }
        if(health == 0){
            visible = false;
            pX = -500;
            pY= -500;
            GameView.setBringPepe(false);
            for(int i=0; i==0; i++) {
                MainActivity.score += 350;
            }

        }
    }
    public void shoot() {
        PepeProjectiles pp = new PepeProjectiles((int)(pX+Assets.pepeBoss.getWidth()*0.1),(int)(pY+Assets.pepeBoss.getHeight()*0.2));
        pepeProjectiles.add(pp);
    }

    public void follow(){
        if(pY < 1){
            GameView.setPepeUp(false);
            GameView.setPepeDown(true);
        }
        if(pY >= MainActivity.getsY()){
            GameView.setPepeDown(false);
            GameView.setPepeUp(true);
        }
    }
}
