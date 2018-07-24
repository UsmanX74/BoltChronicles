package com.example.usman.myapp3;

import android.graphics.Rect;
import java.util.ArrayList;
import static com.example.usman.myapp3.TitleView.scale;

public abstract class Bosses {
    int x;
    int y;
    public Rect getR() {
        return r;
    }
    public void setR(Rect r) {
        this.r = r;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getSpeedY() {
        return speedY;
    }
    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
    public int getSpeedX() {
        return speedX;
    }
    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    int speedX;
    int speedY;
    static int health;
    Rect r = new Rect();
    boolean visible;
    public abstract boolean inPosition();
    public abstract void follow();
    public abstract void shoot();
    public abstract void update();
    public abstract void init();
}
class Boss2 extends Bosses{
    private int i=0;
    private ArrayList<BossProjectiles> boss2Projectiles = new ArrayList<BossProjectiles>();
    public ArrayList<BossProjectiles> getBoss2Projectiles() {
        return boss2Projectiles;
    }
    Boss2(int startX, int startY){
        setX(startX); setY(startY);
        setHealth(120);
        setVisible(false);
        r.set(0,0,0,0);
        setSpeedX((int)(2*scale/1.5));
        setSpeedY((int)(10*scale/1.5));
    }

    @Override
    public boolean inPosition() {
        if(getX() <= MainActivity.getsX()-Assets.bossShip2.getWidth()){
            return true;
        }
        return false;
    }

    @Override
    public void follow() {
        if(i==0 || i==1 || getY() < (-Assets.bossShip2.getHeight() + (Assets.bossShip2.getHeight()*0.1))){
            GameView.setBoss2Up(false);
            GameView.setBoss2Down(true);
        }
        if(getY() >= (MainActivity.getsY() + (Assets.bossShip2.getHeight()*0.1))){
            GameView.setBoss2Down(false);
            GameView.setBoss2Up(true);
        }
    }

    @Override
    public void shoot() {
        BossProjectiles bp = new BossProjectiles((int)(getX()+Assets.bossShip2.getWidth()*0.1),(int)(getY()+Assets.bossShip2.getHeight()*0.2));
        boss2Projectiles.add(bp);
    }

    public void update() {
        r.set(getX(),getY(),(getX()+Assets.bossShip2.getWidth()),(getY()+Assets.bossShip2.getHeight()));
        if(!inPosition()){
            x -= speedX;
        }
        if(inPosition()){
            setSpeedX(0);
            if (GameView.isBoss2Up()){
                y -= speedY;
            }
            if(GameView.isBoss2Down()){
                y += speedY;
            }
        }
        if(health<0){
            health=0;
        }
        if(health == 0 || health == -5 || health == -10){
            setVisible(false);
            x = -600;
            y= -600;
            GameView.setBringBoss2(false);
            //MainActivity.score += 400;
            MainActivity.setScore(MainActivity.getScore()+500);
        }
        i++;
    }

    public void specialAttack(){
        x -= (speedX*3);
    }

    @Override
    public void init() {
        setHealth(120);
        setVisible(false);
        r.set(0,0,0,0);
        setSpeedX((int)(2*scale/1.5));
        setSpeedY((int)(10*scale/1.5));
    }
}
class Boss3 extends Bosses{
    Boss3(int startX, int startY){
        setX(startX); setY(startY);
    }
    @Override
    public boolean inPosition() {
        return false;
    }

    @Override
    public void follow() {

    }

    @Override
    public void shoot() {

    }

    @Override
    public void update() {

    }

    @Override
    public void init() {

    }
}