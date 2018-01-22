package com.example.usman.myapp3;

import android.graphics.Rect;
import java.util.ArrayList;


public class Enemy {

     int eX;
    int eY;
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    int speedX;
    private boolean visible;
    private Rect enemyRect = new Rect();


    public Enemy(int startX, int startY) {
        eX = startX;
        eY = startY;
        speedX = (int)(6*(GameView.scale/1.5));
        enemyRect.set(0, 0, 0, 0);
        visible = true;
    }


    public void spawnEnemy() {
        Enemy p = new Enemy(MainActivity.getsX() + Assets.getEnShip().getWidth(), GameView.getGen());
        enemies.add(p);
    }

    public void update() {
        eX -= speedX;
        enemyRect.set(eX, eY, eX + Assets.getEnShip().getWidth(), eY + Assets.getEnShip().getHeight());
        if (eX + Assets.getEnShip().getWidth() < 0) {
            visible = false;
        }
        if (MainActivity.score > 250 && MainActivity.score < 500){
                setSpeedX((int)(7*(GameView.scale/1.5)));
        }else if(MainActivity.score > 500 && MainActivity.score < 750){
            setSpeedX((int)(7*(GameView.scale/1.5)));
        }else if(MainActivity.score > 750 && MainActivity.score < 1000){
            setSpeedX((int)(8*(GameView.scale/1.5)));
        }else if(MainActivity.score > 1000 && MainActivity.score < 1500){
            setSpeedX((int)(8*(GameView.scale/1.5)));
        }else if(MainActivity.score > 1500 && MainActivity.score < 2000){
            setSpeedX((int)(10*(GameView.scale/1.5)));
        }else if(MainActivity.score > 2000 && MainActivity.score < 3000){
            setSpeedX((int)(11*(GameView.scale/1.5)));
        }else if(MainActivity.score > 3000 && MainActivity.score < 4000){
            setSpeedX((int)(12*(GameView.scale/1.5)));
        }else if (MainActivity.score > 4000){
            setSpeedX((int)(12*(GameView.scale/1.5)));
        }
    }

    public Rect getEnemyRect() {
        return enemyRect;
    }
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }
    public int geteY() {
        return eY;
    }
    public int geteX() {
        return eX;
    }
}




