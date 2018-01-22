package com.example.usman.myapp3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import static com.example.usman.myapp3.TitleView.SCALE_CONST;

public class GameView extends View{
    int gunclip = 60;
    public static int i=1;
    boolean handled = false;
    long startTime,delTime;
    static float touchX,touchY;
    private static int screenW,screenH;
    public static float scale;
    int enemySpawnInterval;
    private float wi,he,pepe_wi,pepe_he,boss_wi,boss_he,boss2_wi,boss2_he;
    int pepe_diff = 240;
    float starSpeed;
    public static boolean gameEnded = false, gamePaused = false, gameRunning = false;
    private static Boltship player;
    private Enemy enemy;
    private static boolean bringPepe;
    private static boolean bringBoss;
    private static boolean bringBoss2;
    boolean turretButtonPressed;
    private Paint paint;
    private Paint bulletPaint, pepeBulletPaint,bossBulletPaint,redPaint,bluePaint,greenPaint,yellowPaint,whitePaint;
    private TextPaint tp;
    private String resume="Resume",quit="Quit to Main Menu",play="Play Again",restart="Restart";
    boolean reloading = false;
    private Random generator;
    int no_of_stars= 230;
    float [] genStars;
    int a=0;
    int b=1;
    int c=0;
    private static int gen = 0;
    public static int ennum = 0;
    Iterator itr,eItr;
    Context myContext;
    ArrayList enemies,projectiles,pepeProjectiles,bossProjectiles,turretProjectiles,boss2Projectiles;
    Projectiles s;
    Enemy d;
    PepeBoss pepeboss;
    BossShip bossship;
    Boss2 bossship2;
    Turret turretGun;
    int densityDpi;
    private int gen2 = 0,gen3 = 0;
    public static long eventDownDuration;
    Timer enemyTimer,coinTimer,turretShootTimer,reloadTimer;
    Animation anim;
    int mi=0;
    int globalx=-300,globaly=-300;
    TitleActivity t;
    boolean bringCoin,turretUp,turretDown;
    int genCoinX=-30,genCoinY=-30,mf,lastx,lasty;
    Boss3 boss3;
    Rect resumeBounds,quitBounds,resumeRect,quitRect,playBounds,playRect,restartBounds,restartRect;
    private boolean okap;
    int pointerCount,eventaction;

    public GameView(final Context context){
        super(context);
        MainActivity.score = 0;
        TitleActivity.coins = 0;
        TitleActivity.preferences.edit().putInt("potentialHighscore",0).apply();
        resumeBounds = new Rect();
        quitBounds = new Rect();
        resumeRect = new Rect();
        quitRect = new Rect();
        playBounds = new Rect();
        restartBounds = new Rect();
        playRect = new Rect();
        restartRect = new Rect();
        gameRunning = true;
        gamePaused = false;
        gameEnded = false;
        myContext = context;
        t = new TitleActivity();
        bringPepe = false;
        bringBoss = false;
        bringBoss2 = false;
        scale = context.getResources().getDisplayMetrics().density;
        screenW = MainActivity.getsX();
        screenH = MainActivity.getsY();
        wi=220*(float)(scale);
        he=3*(float)(scale);
        pepe_wi=196* (float)(scale);
        pepe_he=5*(float)(scale);
        boss_wi=244*(float)(scale);
        boss_he=5*(float)(scale);
        boss2_wi=244*(float)(scale);
        boss2_he=5*(float)(scale);
        paint = new Paint();
        bulletPaint = new Paint();
        redPaint = new Paint();
        bluePaint = new Paint();
        greenPaint = new Paint();
        yellowPaint = new Paint();
        whitePaint = new Paint();
        bulletPaint.setColor(Color.YELLOW);
        paint.setColor(Color.WHITE);
        paint.setTextSize((float)(23.4*SCALE_CONST));
        paint.setStrokeWidth(scale);
        //Log.d("Tag","stroke width: " + (1*(float)(scale/1.5)));
        //Log.d("Tag","scale: " + scale);
        //paint.setStrokeWidth(1*(float)(scale/1.5));
        redPaint.setColor(Color.RED);
        bluePaint.setColor(Color.BLUE);
        greenPaint.setColor(Color.GREEN);
        yellowPaint.setColor(Color.YELLOW);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setTextSize((float)(70*SCALE_CONST));
        whitePaint.setFakeBoldText(true);
        tp = new TextPaint();
        tp.setColor(Color.WHITE);
        tp.setTextSize((float)(56*SCALE_CONST));
        //tp.setFakeBoldText(true);
        tp.setTextAlign(TextPaint.Align.CENTER);
        tp.getTextBounds(resume,0,resume.length(),resumeBounds);
        tp.getTextBounds(quit,0,quit.length(),quitBounds);
        tp.getTextBounds(play,0,play.length(),playBounds);
        resumeRect.set((int)((screenW/2f)-(resumeBounds.width()/2f)),(int)((screenH*0.48)-(resumeBounds.height())),
                (int)((screenW/2f)-(resumeBounds.width()/2f)+(resumeBounds.width())),(int)((screenH*0.48)));
        quitRect.set((int)((screenW/2f)-(quitBounds.width()/2f)),(int)((screenH*0.62)-(quitBounds.height())),
                (int)((screenW/2f)-(quitBounds.width()/2f)+(quitBounds.width())),(int)((screenH*0.62)));
        playRect.set((int)((screenW/2f)-(quitBounds.width()/2f)),(int)((screenH*0.48f)-(quitBounds.height())),
                (int)((screenW/2f)-(quitBounds.width()/2f)+(quitBounds.width())),(int)((screenH*0.48f)));
        whitePaint.setTextAlign(Paint.Align.CENTER);
        pepeBulletPaint = new Paint();
        pepeBulletPaint.setColor(Color.RED);
        bossBulletPaint = new Paint();
        bossBulletPaint.setColor(Color.BLUE);
        generator = new Random();
        genStars= new float[no_of_stars*2];//Star Co-Ordinates
        for(int i=0; i<no_of_stars; i++){
            genStars[a]=generator.nextInt(screenW);
            a += 2;
        }
        for(int i=0; i<no_of_stars; i++){
            genStars[b]=generator.nextInt(screenH);
            b += 2;
        }
        starSpeed = 6f;
        player = new Boltship();
        enemySpawnInterval = 750;
        enemy = new Enemy(0,0);
        densityDpi = context.getResources().getDisplayMetrics().densityDpi;
        //Log.d(TAG, "densityDPI: " + densityDpi);
        //Log.d(TAG, "scaled ship X: " + scaledShipX);
        projectiles = player.getProjectiles();
        enemies = enemy.getEnemies();
        pepeboss = new PepeBoss((int)(MainActivity.getsX()-Assets.pepeBoss.getWidth()),0);
        bossship = new BossShip((int)(MainActivity.getsX()),0);
        //bossManager = new BossManager();
        //bossship2 = bossManager.new BossShip2((int)MainActivity.getsX(),0);
        bossship2 = new Boss2((int)MainActivity.getsX(),0);
        boss3 = new Boss3(20,20);
        //bossship2 = new BossManager().new BossShip2((int)(MainActivity.getsX()),0);
        turretGun = new Turret(0,screenH/2);
        pepeProjectiles = pepeboss.getPepeProjectiles();
        bossProjectiles = bossship.getBossProjectiles();
        boss2Projectiles = bossship2.getBoss2Projectiles();
        turretProjectiles = turretGun.getTurretProjectiles();
        d = new Enemy(0,0);
        s = new Projectiles(0,0);
        enemyTimer = new Timer();
        coinTimer = new Timer();
        turretShootTimer = new Timer();
        reloadTimer = new Timer();
        anim = new Animation();
        anim.addFrame(Assets.explosion1,100);
        anim.addFrame(Assets.explosion2,100);
        anim.addFrame(Assets.explosion3,100);
        anim.addFrame(Assets.explosion4,100);
        anim.addFrame(Assets.explosion5,100);
        anim.addFrame(Assets.explosion6,100);
        anim.addFrame(Assets.explosion7,100);
        anim.addFrame(Assets.explosion8,100);
        anim.addFrame(Assets.explosion9,100);
        anim.addFrame(Assets.explosion10,100);
        anim.addFrame(Assets.explosion11,100);
        anim.addFrame(Assets.explosion12,100);
        gameRunning = true;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenW = w;
        screenH = h;
        MainActivity.setsX(w);
        MainActivity.setsY(h);
        setScreenW(w);
        setScreenH(h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(gameRunning){
            startTime = System.currentTimeMillis();
            //resetHighscore();
            //draw background color
            canvas.drawARGB(255,0,0,40);
            //draw the stars
            canvas.drawPoints(genStars,paint);
            //move the stars
            moveStars();
            //repeat the stars
            repeatStars();
            gen2 = generator.nextInt(pepe_diff);
            gen3 = generator.nextInt(185);
            //rem = MainActivity.score%50;
            //gen4 = generator.nextInt(6);
            checkLevel();
            checkEnemy();
            checkPepeTime();
            checkBossTime();
            checkBoss2Time();
            //checkTurretTime();
            PepeBoss.setScreenW(screenW);
            //whitePaint.setStrokeWidth(1*scale);
            //canvas.drawPoint(200,200,whitePaint);
            player.update();
            canvas.drawBitmap(Assets.boltShip, player.getbX(), player.getbY(),null);

            //CODE TO BRING A COIN //STARTS HERE
            if(coinTimer.isTime(70000)){
                generateCoinPosition();
                coinTimer.resetWaitFor();
                lastx = genCoinX;
                lasty = genCoinY;
            }

            if(genCoinX == lastx || genCoinY==lasty) {
                if (coinTimer.waitFor(2500)) {
                    bringCoin = true;
                }else{
                    bringCoin=false;
                }
            }
            if(bringCoin) {
                    canvas.drawBitmap(Assets.coin, genCoinX, genCoinY, null);
            }
            //CODE TO BRING A COIN //ENDS HERE

            //bossship2.update();
            //bossship2.follow();
            //canvas.drawBitmap(scaledBossShip2,bossship2.getvX(),bossship2.getvY(),null);
            //canvas.drawText("boss2 health" + bossship2.health,300,300,paint);

            for (int i = 0; i < projectiles.size(); i++) {
                Projectiles p = (Projectiles) projectiles.get(i);
                p.update();
                canvas.drawRect(p.getR(), bulletPaint);
                itr = projectiles.iterator();
                while (itr.hasNext()) {
                    s = (Projectiles) itr.next();
                    if (!s.isVisible()) {
                        itr.remove();
                    }
                }
            }
            for (int i = 0; i < turretProjectiles.size(); i++) {
                TurretProjectiles tp = (TurretProjectiles) turretProjectiles.get(i);
                tp.update();
                canvas.drawRect(tp.getR(), bulletPaint);
                if (!tp.isVisible()) {
                    turretProjectiles.remove(i);
                }
            }

            //canvas.drawText("asd: "+asd,200,200,paint);

            for (int q = 0; q < pepeProjectiles.size(); q++) {
                PepeProjectiles qp = (PepeProjectiles) pepeProjectiles.get(q);
                qp.update();
                canvas.drawRect(qp.getR(), pepeBulletPaint);
                if (!qp.isVisible()) {
                    pepeProjectiles.remove(q);
                }
            }

            enemy.update();
            for (int j = 0; j < enemies.size(); j++) {
                Enemy e = (Enemy) enemies.get(j);
                e.update();
                canvas.drawBitmap(Assets.getEnShip(), e.geteX(), e.geteY(), null);
                eItr = enemies.iterator();
                while (eItr.hasNext()) {
                    d = (Enemy) eItr.next();
                    if (!d.isVisible()) {
                        eItr.remove();
                    }
                }
            }

            for (int w = 0; w < bossProjectiles.size(); w++) {
                BossProjectiles bp = (BossProjectiles) bossProjectiles.get(w);
                bp.update();
                canvas.drawRect(bp.getR(), bossBulletPaint);
                if (!bp.isVisible()) {
                    bossProjectiles.remove(w);
                }
            }
            for (int w = 0; w < boss2Projectiles.size(); w++) {
                BossProjectiles bp = (BossProjectiles) boss2Projectiles.get(w);
                bp.update();
                canvas.drawRect(bp.getR(), bossBulletPaint);
                if (!bp.isVisible()) {
                    boss2Projectiles.remove(w);
                }
            }
            //SPAWN ENEMIES BY TIMER
            if(enemyTimer.isTime(enemySpawnInterval) && !bringBoss && !bringPepe && !bringBoss2){
              enemy.spawnEnemy();
            }

            //if(explosionTimer.waitFor(20000)){
              //  canvas.drawBitmap(scaledExplosion1,500,250,null);
            //}

            if (bringPepe) {
                pepeboss.update();
                pepeboss.follow();
                canvas.drawBitmap(t.tempPepe, pepeboss.getpX(), pepeboss.getpY(), null);
                canvas.drawBitmap(Assets.pepeHealthBar, (int) (2 * screenW / 3), (int) (screenH * 0.004), null);
                canvas.drawRect((int) (2 * screenW / 3) + (3 * scale), (int) (screenH * 0.004) + (3 * scale), (2 * screenW / 3) + (3 * scale) + pepe_wi, (int) (screenH * 0.004) + (3 * scale) + pepe_he, greenPaint);
            }
            if (bringBoss) {
                bossship.update();
                bossship.follow();
                canvas.drawBitmap(Assets.bossShip, bossship.getvX(), bossship.getvY(), null);
                canvas.drawBitmap(Assets.bossHealthBar, (int) (screenW * 0.58), (int) (screenH * 0.004), null);
                canvas.drawRect((int) (screenW * 0.58) + (3 * scale), (int) (screenH * 0.004) + (3 * scale), (int) (screenW * 0.58) + (3 * scale) + boss_wi, (int) (screenH * 0.004) + (3 * scale) + boss_he, bluePaint);
            }
            if (bringBoss2) {
                bossship2.update();
                bossship2.follow();
                canvas.drawBitmap(Assets.bossShip2, bossship2.getX(), bossship2.getY(), null);
                canvas.drawBitmap(Assets.bossHealthBar, (int) (screenW * 0.58), (int) (screenH * 0.004), null);
                canvas.drawRect((int) (screenW * 0.58) + (3 * scale), (int) (screenH * 0.004) + (3 * scale), (int) (screenW * 0.58) + (3 * scale) + boss2_wi, (int) (screenH * 0.004) + (3 * scale) + boss2_he, bluePaint);
            }
            if (TitleView.isBringTurret()) {
                turretGun.matrix.reset();
                turretGun.matrix.setTranslate(-Assets.turret.getHeight()/2, -Assets.turret.getHeight()/2);
                turretGun.matrix.postRotate((float)turretGun.rotationAngleinDeg);
                turretGun.matrix.postTranslate(turretGun.centerX,turretGun.centerY);
                canvas.drawBitmap(Assets.turret,turretGun.matrix,null);
                //canvas.drawBitmap(Assets.shield,180,150,null);
                //canvas.drawBitmap(Assets.turretButton,250,250,null);
                //canvas.drawBitmap(Assets.shieldButton,300,300,null);
                if(turretShootTimer.isTime(800)){
                    turretGun.shoot();
                }
            }

            gen = generator.nextInt(screenH - ((int) Assets.getEnShip().getWidth()/2));
            t.tempPepe = Assets.pepeBoss;

            if (angry()){
                t.tempPepe = Assets.pepeHurt;
                pepe_diff = 220;
            }
            checkHighScore();
            checkCollision();
            checkPlayerCollision();

            if (bringPepe){
                if (shouldPepeShoot() || pepeboss.getpY() == player.getbY()) {
                    pepeboss.shoot();
                }
                if (gen3==5 || gen3==30 || gen3==65){
                    okap = true;
                }else{
                    okap = false;
                }
                if (okap){
                    if(pepeboss.getpX() > (Assets.boltShip.getWidth()+10)){
                        //pepeboss.specialAttack();
                    }else{
                        //pepeboss.goBack();
                    }
                }

                checkPepeCollision();
                checkPlayerPepeCollision();
                checkBulletsCollision();
            }
            if (bringBoss) {
                if (shouldBossShoot()) {
                    bossship.shoot();
                }
                if (gen3==30 || gen3==65 ||   gen3==100 ||  gen3==150){
                    if (bossship.getvX() > (player.getbX()+Assets.boltShip.getWidth()+10)){
                        //bossship.specialAttack();
                    }
                }
                checkBossCollision();
                checkPlayerBossCollision();
                checkBulletsCollision();
                checkTurretBulletCollision();
            }
            if (bringBoss2){
                if (shouldBoss2Shoot()) {
                    bossship2.shoot();
                }
                if (gen3==30 || gen3==60 || gen3==90 || gen3==120 || gen3==150){
                    if (bossship2.getX() < (Assets.boltShip.getWidth()+10)){
                        //bossship2.specialAttack();
                    }
                }
                checkBoss2Collision();
                checkPlayerBoss2Collision();
                checkBulletsCollision();
                checkTurretBulletCollision();
                checkTurretCollision();
            }
            if(mf<30){
                mf++;
            }
            if(mf == 1){
                turretDown = true;
            }
            turretUpdate();
            rotateTurret();
            checkTurretBulletCollision();

            //resetHighscore();
            /*
            if(bringCoin){
                if(coinTimer.isTime(5000)){
                    generateCoinPosition();
                }
            }
            */
            //canvas.drawBitmap(chExplosion,500,300,null);
            canvas.drawText("Score: " + MainActivity.score, (float)(screenW*0.06), (float)(screenH*0.08), paint);
            canvas.drawBitmap(Assets.healthBar, (int) (screenW * 0.06), (int) (screenH * 0.004), null);
            canvas.drawBitmap(Assets.pause,(float)(screenW*0.018),(float)(screenH*0.01),null);
            canvas.drawRect((int) (screenW * 0.06) + (3 * scale), (int) (screenH * 0.004) + (3 * scale), (int) (screenW * 0.06) + (3 * scale) + wi, (int) (screenH * 0.004) + (3 * scale) + he, redPaint);
            //canvas.drawRect(100,100,200,200,pepeBulletPaint);
            //canvas.drawText("event down duration: " + eventDownDuration/1000 + " seconds",300,300,paint);
            canvas.drawText("HighScore: " + MainActivity.preferences.getInt("HighScore", 0), 20, (int) (screenH * 0.99), paint);
            canvas.drawText("Coins: "+ TitleActivity.coins, (float)(getScreenW()/3),(float)(screenH * 0.99), paint);
            canvas.drawText("high1 "+TitleActivity.getHighScore(),(float)(getScreenW()/3),(float)(screenH * 0.20), paint);
            canvas.drawText("high2 "+TitleActivity.getHighScore1(),(float)(getScreenW()/3),(float)(screenH * 0.25), paint);
            canvas.drawText("high3 "+TitleActivity.getHighScore2(),(float)(getScreenW()/3),(float)(screenH * 0.30), paint);
            canvas.drawText("Potential High "+TitleActivity.preferences.getInt("potentialHighscore",0),(float)(getScreenW()/3),(float)(screenH * 0.35), paint);
            if(reloading) {
                canvas.drawText("Reloading", (float) (getScreenW() / 2), (float) (screenH * 0.99), paint);
            }else{
                canvas.drawText("Clip: " + gunclip, (float) (getScreenW() / 2), (float) (screenH * 0.99), paint);
            }
            //canvas.drawText("Boss 2 x: " + boss2.getX() + " boss2 speedX="+boss2.getSpeedX(), screenW / 2, (float) (screenH * 0.2), paint);
            //canvas.drawText("game paused: " + gamePaused,300,300,paint);
            //canvas.drawRect(350,350,450,450,paint);
            //canvas.drawText("oldW"+ oldW,100,450,paint);
            //canvas.drawText("oldH"+ oldH,100,550,paint);
            //canvas.drawText("explosionTimer: " + explosionTimer.waitFor(20000,12),300,500,paint);
            //canvas.drawText("turret angle: " + turretGun.rotationAngleinDeg, 300,400,paint);
            //canvas.drawBitmap(anim.getImage(),200,200,null);
            //resetHighscore();
            if (mi < 27){
                doOneExplosion(canvas,globalx,globaly, 30);
            }
            mi++;
            //canvas.drawText("pointer count: " + pointerCount,200,200,paint);
            //canvas.drawText("test_var: "+testvar,200,250,paint);
            //checkPause();
            checkEnd();
            if(gunclip <= 0){
                reload();
            }
            //if(reloadButtonPressed){
            //  reload();
            // }
            delTime = System.currentTimeMillis() - startTime;
            //float FPS = 1000/delTime;
            //canvas.drawText("FPS: "+FPS,(float)(getScreenW()*0.75f),(float)(screenH * 0.99), paint);
            //rotateTurret();
            invalidate();
        }else if(gameEnded){
            canvas.drawARGB(255, 0, 0, 50);
            canvas.drawPoints(genStars,paint);
            canvas.drawText("GAME OVER", screenW / 2, (float) (screenH * 0.2), whitePaint);
            MainActivity.preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = MainActivity.preferences.edit();
            editor.putInt("prevHighScore", MainActivity.getHighScore());
            editor.apply();
            canvas.drawText("high1 "+TitleActivity.getHighScore(),(float)(getScreenW()*0.25f),(float)(screenH * 0.20), paint);
            canvas.drawText("high2 "+TitleActivity.getHighScore1(),(float)(getScreenW()*0.25f),(float)(screenH * 0.25), paint);
            canvas.drawText("high3 "+TitleActivity.getHighScore2(),(float)(getScreenW()*0.25f),(float)(screenH * 0.30), paint);
            canvas.drawText("Potential High "+TitleActivity.preferences.getInt("potentialHighscore",0),(float)(getScreenW()/3),(float)(screenH * 0.35), paint);
            canvas.drawText(play,(screenW/2),(float)(screenH*0.48f),tp);
            canvas.drawText(quit,(float)(screenW/2f),(float)(screenH*0.62f),tp);
            canvas.drawText("Score: " + MainActivity.score, (float)(screenW*0.06), (float)(screenH*0.08), paint);
            canvas.drawText("HighScore: " + MainActivity.preferences.getInt("prevHighScore", 0), 20, (int) (screenH * 0.99), paint);
            canvas.drawText("Coins: "+ TitleActivity.coins, (float)(getScreenW()/3),(float)(screenH * 0.99), paint);
        }else if(gamePaused){
            canvas.drawARGB(255, 0, 0, 50);
            canvas.drawPoints(genStars,paint);
            canvas.drawText("GAME PAUSED",screenW/2,(float)(screenH*0.2),whitePaint);
            /*
            canvas.drawRect((float)((screenW/2f)-(resumeBounds.width()/2f)),(float)((screenH*0.48)-(resumeBounds.height())),
                    (float)((screenW/2f)-(resumeBounds.width()/2f)+(resumeBounds.width())),(float)((screenH*0.48)),
                    redPaint);
            canvas.drawRect((float)((screenW/2f)-(quitBounds.width()/2f)),(float)((screenH*0.61)-(quitBounds.height())),
                    (float)((screenW/2f)-(quitBounds.width()/2f)+(quitBounds.width())),(float)((screenH*0.61)),
                    redPaint);
            */
            canvas.drawText(resume,(float)(screenW/2f),(float)(screenH*0.48),tp);
            canvas.drawText(quit,(float)(screenW/2f),(float)(screenH*0.62),tp);
            //canvas.drawRect(200,200,200+resumeBounds.width(),200+resumeBounds.height(),redPaint);
            //canvas.drawText("Resume Game",200,200,tp);
            //canvas.drawBitmap(Assets.right,(float)((screenW/2)-(Assets.right.getWidth()/2)),(float)(screenH*0.4),null);
            //canvas.drawText("Score: " + MainActivity.score, screenW / 2, (float) (screenH * 0.7), whitePaint);
        }
    }

    public void generateCoinPosition(){
            genCoinX = generator.nextInt(screenW - Assets.coin.getWidth());
            genCoinY = generator.nextInt(screenH - Assets.coin.getHeight());
    }
    public void rotateTurret(){
        if(turretGun.rotationAngleinDeg > 45){
            turretDown = false;
            turretUp = true;
        }
        if(turretGun.rotationAngleinDeg < -45){
            turretUp = false;
            turretDown = true;
        }
    }
    public void turretUpdate(){
        if(turretDown){
            turretGun.rotationAngleinDeg += 3;
        }
        if(turretUp){
            turretGun.rotationAngleinDeg -= 3;
        }
    }
    public void moveStars(){
        for(int i=0;i<no_of_stars*2;i++) {
            if(!bringBoss && !bringPepe && !bringBoss2){
            genStars[c] -= starSpeed*SCALE_CONST;
            }
            c += 2;
            if(c>((no_of_stars*2)-2)){
                c=0;
            }
        }
    }
    public void repeatStars(){
        for(int d=0;d<no_of_stars*2;d+=2){
            if(genStars[d] < 0){
                genStars[d] = screenW;
                if(d>((no_of_stars*2) -2)){
                    d=0;
                }
            }
        }
    }
    public void checkCollision(){//checks if player bullets hit enemies
       for (Projectiles p: player.getProjectiles())for(Enemy e: enemy.getEnemies()){
          if(p.getR().intersect(e.getEnemyRect())){
           p.setVisible(false);
              e.setVisible(false);
              mi=0;
              globalx=e.geteX();
              globaly=e.geteY();
              Assets.sp.play(Assets.invaderExplode,0.017f,0.017f,1,0,1);
              MainActivity.score += 7;
          }
       }
     }
    public boolean shouldPepeShoot(){//checks if pepe can shoot
        if(pepeboss.getpY() == player.getbY() || pepeboss.getpY() == player.getbY()+ Assets.boltShip.getHeight()/2 || gen2 == 210 ||gen2 == 100 || gen2 == 200 || gen2 == 80
                || pepeboss.getpY() == player.getbY()+Assets.boltShip.getHeight()/3 || pepeboss.getpY() == player.getbY() || gen2 == 50 || gen2 == 150){
            return true;
        }
        return false;
    }
    public boolean shouldBossShoot(){//checks if boss can shoot
        if((bossship.getvY() == player.getbY() || gen3 == 40 || gen3 == 80 || gen3 == 120 || gen3 == 160 || gen3 == 20 || gen3 == 135
                ||gen3 == 100 || gen3 == 50 || gen3 == 130 || gen3 == 150 || gen3 == 70) && bossship.inPosition()){
            return true;
        }
        return false;
    }
    public boolean shouldBoss2Shoot(){//checks if boss can shoot
        if((bossship2.getY() == player.getbY() || gen3 == 40 || gen3 == 80 || gen3 == 120 || gen3 == 160 || gen3 == 20 || gen3 == 135
                ||gen3 == 100 || gen3 == 50 || gen3 == 130 || gen3 == 150 || gen3 == 70) && bossship2.inPosition()){
            return true;
        }
        return false;
    }
    public void checkPlayerCollision(){//checks if enemies collide with player
        for(Enemy w: enemy.getEnemies()){
            if(w.getEnemyRect().intersect(player.getPlayerRect())){
                w.setVisible(false);
                mi=0;
                globalx=w.geteX();
                globaly=w.geteY();
                Assets.sp.play(Assets.invaderExplode,0.017f,0.017f,1,0,1);
                if(wi != 0){
                wi -= 25*scale;
                }
                if(wi < 0){wi =0;}
                //l -= 1;
            }
        }
    }
    public void checkEnd(){
        if(wi == 0){
            gameEnded = true;
            gameRunning = false;
            gamePaused = false;
        }
    }
    public void checkPlayerPepeCollision(){//checks if pepe bullets hit the player
        for(PepeProjectiles qw: pepeboss.getPepeProjectiles()){
            if(qw.getR().intersect(player.getPlayerRect())){
                qw.setVisible(false);
                //MainActivity.score -= 2;
                if(wi > 0){
                    wi -= 3*scale;
                }
                if(wi < 0){
                    wi = 0;
                }
            }
        }
    }
    public void checkPlayerBossCollision(){//checks if BossShip bullets hit the player
        for(BossProjectiles qe: bossship.getBossProjectiles()){
            if(qe.getR().intersect(player.getPlayerRect())){
                qe.setVisible(false);
                //MainActivity.score -= 2;
                if(wi != 0){
                    wi -= 3*scale;
                }
                if(wi < 0){wi =0;}
            }
        }
    }
    public void checkPlayerBoss2Collision(){//checks if BossShip2 bullets hit the player
        for(BossProjectiles ql: bossship2.getBoss2Projectiles()){
            if(ql.getR().intersect(player.getPlayerRect())){
                ql.setVisible(false);
                //MainActivity.score -= 2;
                if(wi != 0){
                    wi -= 3*scale;
                }
                if(wi < 0){wi =0;}
            }
        }
    }
    public void checkBulletsCollision() {//checks if player bullets collide with pepe bullets or with BossShip bullets
        for (Projectiles p : player.getProjectiles())
            for (PepeProjectiles pp : pepeboss.getPepeProjectiles()) {
                if (p.getR().intersect(pp.getR())) {
                    p.setVisible(false);
                    pp.setVisible(false);
                }
            }
        for (BossProjectiles bp : bossship.getBossProjectiles())
            for (Projectiles p : player.getProjectiles()) {
                if (p.getR().intersect(bp.getR())) {
                    p.setVisible(false);
                    bp.setVisible(false);
                }
            }
        for (BossProjectiles bp2 : bossship2.getBoss2Projectiles())
            for (Projectiles p : player.getProjectiles()) {
                if (p.getR().intersect(bp2.getR())) {
                    p.setVisible(false);
                    bp2.setVisible(false);
                }
            }
        }
    public void checkTurretBulletCollision(){
        for (TurretProjectiles tp: turretGun.getTurretProjectiles()) for(Enemy e: enemy.getEnemies()){
                if(tp.getR().intersect(e.getEnemyRect())){
                    tp.setVisible(false);
                    e.setVisible(false);
                    mi=0;
                    Assets.sp.play(Assets.invaderExplode,0.017f,0.017f,1,0,1);
                    MainActivity.score += 2;
                }
        }
    }
    public void doOneExplosion(Canvas c, int x, int y, long updateTime){
            anim.update(updateTime);
            c.drawBitmap(anim.getImage(),x,y,null);
    }
    public void checkTurretCollision(){
        for(Enemy e: enemy.getEnemies()){
            if(e.getEnemyRect().intersect(turretGun.gettR())){
                e.setVisible(false);
                turretGun.health -= 1;
            }
        }
    }
    public void checkPepeCollision(){//checks if player bullets hit pepe
        for(Projectiles pr: player.getProjectiles()){
            if(pr.getR().intersect(pepeboss.getPepeRect())){
                t.tempPepe = Assets.pepeHurt;
                pepeboss.health -= 1;
                MainActivity.score += 1;
                if(pepe_wi != 0){
                    pepe_wi -= 1.96f*scale;
                }
                pr.setVisible(false);
            }
        }
    }
    public void checkBossCollision(){//checks if player bullets hit BossShip
        for(Projectiles pr: player.getProjectiles()){
            if(pr.getR().intersect(bossship.getR())){
                bossship.health -= 1;
                MainActivity.score += 1;
                if(boss_wi != 0){
                    boss_wi -= 2.44f*scale;
                }
                pr.setVisible(false);
            }
        }
    }
    public void checkBoss2Collision(){//checks if player bullets hit BossShip
        for(Projectiles pl: player.getProjectiles()){
            if(pl.getR().intersect(bossship2.getR())){
                bossship2.health -= 1;
                MainActivity.score += 1;
                if(boss2_wi != 0){
                    boss2_wi -= 2.44f*scale;
                }
                pl.setVisible(false);
            }
        }
    }
    public boolean angry(){//checks if pepe is angry
        if(pepeboss.health < 30){
        return true;}
        return false;
    }
    public void checkPepeTime(){//checks if it is time to bring pepe
        if(MainActivity.score > 150 && pepeboss.health != 0){
            bringPepe = true;
        }//else{bringPepe = false;}
    }
    public void checkBossTime(){//checks if it is time to bring BossShip
        if(MainActivity.score > 850 && bossship.health != 0){
            bringBoss = true;
        }
    }
    public void checkBoss2Time(){//checks if it is time to bring BossShip
        if(MainActivity.score > 1500 && bossship2.getHealth() != 0){
            bringBoss2 = true;
        }
    }
    public void checkTurretTime(){//checks if is time to bring turretGun
        if(turretButtonPressed && turretGun.health != 0){

        }
    }
    public void checkHighScore(){
        if (MainActivity.score > MainActivity.getHighScore()){
            MainActivity.setHighScore(MainActivity.score);
        }
        if(MainActivity.score > TitleActivity.getHighScore2()){
            TitleActivity.preferences.edit().putInt("potentialHighscore",MainActivity.score).apply();
        }
        // TitleActivity.setHighScore(TitleActivity.preferences.getInt("highscore",0));
        //TitleActivity.setHighScore1(TitleActivity.preferences.getInt("highscore1",0));
        //TitleActivity.setHighScore2(TitleActivity.preferences.getInt("highscore2",0));
    }
    public static void applyHighscores(){
        if(TitleActivity.preferences.getInt("potentialHighscore",0) > TitleActivity.getHighScore() && i==1){
            //TitleActivity.setHighScore2(TitleActivity.getHighScore1());
            //TitleActivity.setHighScore1(TitleActivity.getHighScore());
            //TitleActivity.setHighScore(TitleActivity.preferences.getInt("potentialHighscore",0));
            TitleActivity.preferences.edit().putInt("highscore2",TitleActivity.getHighScore1()).apply();
            TitleActivity.preferences.edit().putInt("highscore1",TitleActivity.getHighScore()).apply();
            TitleActivity.preferences.edit().putInt("highscore",TitleActivity.preferences.getInt("potentialHighscore",0)).apply();
            i=2;
        }else if(TitleActivity.preferences.getInt("potentialHighscore",0) > TitleActivity.getHighScore1() && i==1){
            //TitleActivity.setHighScore2(TitleActivity.getHighScore1());
            //TitleActivity.setHighScore1(TitleActivity.preferences.getInt("potentialHighscore",0));
            TitleActivity.preferences.edit().putInt("highscore2",TitleActivity.getHighScore1()).apply();
            TitleActivity.preferences.edit().putInt("highscore1",TitleActivity.preferences.getInt("potentialHighscore",0)).apply();
            i=2;
        }else if(TitleActivity.preferences.getInt("potentialHighscore",0) > TitleActivity.getHighScore2() && i==1){
            //TitleActivity.setHighScore2(TitleActivity.preferences.getInt("potentialHighscore",0));
            TitleActivity.preferences.edit().putInt("highscore2",TitleActivity.preferences.getInt("potentialHighscore",0)).apply();
            i=2;
        }
    }
    public void checkLevel(){
        if (MainActivity.score < 500){
            ennum = 0;
        }else if(MainActivity.score > 500 && MainActivity.score < 1000){
            ennum = 1;
            starSpeed=7;
            enemySpawnInterval = 700;
        }else if (MainActivity.score > 1000 && MainActivity.score < 1500){
            ennum = 2;
            starSpeed=8;
            enemySpawnInterval = 675;
        }else if (MainActivity.score > 1500 && MainActivity.score < 2000){
            ennum = 3;
            starSpeed=9;
            enemySpawnInterval = 650;
        }else if (MainActivity.score > 2000){
            ennum = 4;
            starSpeed=10;
            enemySpawnInterval = 625;
        }
    }
    public void checkEnemy(){
        if(ennum == 0){
            Assets.setEnShip(Assets.enemy4);
        }else if (ennum == 1){
            Assets.setEnShip(Assets.enemy2);
        }else if (ennum == 2){
            Assets.setEnShip(Assets.enemy3);
        }else if (ennum == 3){
            Assets.setEnShip(Assets.enemy1);
        }else if (ennum == 4){
            Assets.setEnShip(Assets.enemy5);
        }
    }
    public void finishAct(){
        ((MainActivity)getContext()).finish();
    }
    public void reload(){
        if(!reloading){
            reloadTimer.resetWaitFor();
        }
        if(reloadTimer.waitFor(4000)){
            gunclip = 0;
            reloading = true;
        }else{
            gunclip = 60;
            reloading = false;
        }
    }
    public boolean withinButtonBounds(float touchX, float touchY, Bitmap bitmap, float bitmapX, float bitmapY){
        if(touchX> bitmapX && touchX<(bitmapX+bitmap.getWidth())
                && touchY> bitmapY && touchY<(bitmapY+bitmap.getHeight())){
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        eventaction = (event.getAction() & event.getActionMasked());
        pointerCount = event.getPointerCount();
        float X = event.getX(0);
        float Y = event.getY(0);
        touchX = X; touchY = Y;
        eventDownDuration = event.getEventTime() - event.getDownTime();
        switch (eventaction){
            case MotionEvent.ACTION_DOWN:
                if(gameRunning) {
                    if (X < screenW / 2) {
                        if (Y > (screenH / 2)) {
                            moveDown = true;
                        } else {
                            moveDown = false;
                        }
                        if (Y < (screenH / 2)) {
                            moveUp = true;
                        } else {
                            moveUp = false;
                        }
                    }
                    if(event.getActionIndex() == 0 && gameRunning){
                        if(event.getX(0) > screenW/2  && gunclip > 0 && !reloading){
                            player.shoot();
                            gunclip--;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(gameRunning) {
                    if (X < screenW / 2) {
                        if (Y > (screenH / 2)) {
                            moveDown = true;
                        } else {
                            moveDown = false;
                        }
                        if (Y < (screenH / 2)) {
                            moveUp = true;
                        } else {
                            moveUp = false;
                        }
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                if (gameRunning && bringCoin && X > genCoinX && X< genCoinX+Assets.coin.getWidth() && Y > genCoinY && Y < genCoinY+Assets.coin.getHeight()){
                    TitleActivity.coins += 1;
                    Assets.sp.play(Assets.coinPickup,0.2f,0.2f,1,0,1);
                    bringCoin = false;
                }

                if ((X>0) && (X < 125) && (Y > 0) && (Y < 130) && (gameRunning)) {
                    gameRunning = false;
                    gamePaused = true;
                    gameEnded = false;
                }
                if (resumeRect.contains((int)X,(int)Y) && !gameRunning && gamePaused){
                    gamePaused = false;
                    gameRunning = true;
                }
                if (quitRect.contains((int)X,(int)Y) && !gameRunning && gamePaused){
                    Intent intent = new Intent(getContext(),TitleActivity.class);
                    getContext().startActivity(intent);
                    finishAct();
                    gameRunning = false;
                    gamePaused = false;
                }
                if (gameEnded && playRect.contains((int)X,(int)Y)){
                    //EXPERIMENTAL CODE TO RESTART THIS ACTIVITY
                    gameEnded = false;
                    init();
                    pepeboss.init();
                    bossship.init();
                    bossship2.init();
                    MainActivity.preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = MainActivity.preferences.edit();
                    editor.putInt("HighScore",MainActivity.getHighScore());
                    editor.apply();
                    gameRunning = true;
                }
                if (gameEnded && quitRect.contains((int)X,(int)Y)){
                    Intent intent = new Intent(getContext(),TitleActivity.class);
                    getContext().startActivity(intent);
                    finishAct();
                    gameRunning = false;
                    gamePaused = false;
                }
                if (gameRunning) {
                    moveDown = false;
                    moveUp = false;
                }
                eventDownDuration = 0;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getActionIndex() > 0 && gameRunning){
                    if(gameRunning && bringCoin && event.getX(1) > genCoinX && event.getX(1)< genCoinX+Assets.coin.getWidth() && event.getY(1) > genCoinY && event.getY(1) < genCoinY+Assets.coin.getHeight()){
                        TitleActivity.coins += 1;
                        Assets.sp.play(Assets.coinPickup,0.2f,0.2f,1,0,1);
                        bringCoin = false;
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getActionIndex() > 0 && gameRunning) {
                    if (event.getX(1) > screenW / 2 && gunclip > 0 && !reloading) {
                        player.shoot();
                        gunclip--;
                    }
                }
                break;
        }
        invalidate();
        return true;
    }

    //EVERYTHING OTHER THAN BITMAPS AND THEIR FLOATS
    public static boolean isMoveUp() {
        return moveUp;
    }
    public void setMoveUp(boolean moveUp) {
        GameView.moveUp = moveUp;
    }
    public static int getGen() {
        return gen;
    }
    public static boolean isMoveDown() {
        return moveDown;
    }
    public void setMoveDown(boolean moveDown) {
        GameView.moveDown = moveDown;
    }
    private static boolean moveUp = false, moveDown = false;
    public static Boltship getPlayer() {
        return player;
    }
    public static void setPlayer(Boltship player) {
        GameView.player = player;
    }
    public static boolean isBringPepe() {
        return bringPepe;
    }
    public static void setBringPepe(boolean bringPepe) {
        GameView.bringPepe = bringPepe;
    }
    public static int getScreenW() {
        return screenW;
    }
    public static void setScreenW(int screenW) {
        GameView.screenW = screenW;
    }
    public static void setScreenH(int screenH) {
        GameView.screenH = screenH;
    }
    public static boolean isPepeUp() {
        return pepeUp;
    }
    public static void setPepeUp(boolean pepeUp) {
        GameView.pepeUp = pepeUp;
    }
    public static boolean isPepeDown() {
        return pepeDown;
    }
    public static void setPepeDown(boolean pepeDown) {
        GameView.pepeDown = pepeDown;
    }
    private static boolean pepeUp=false;
    private static boolean pepeDown=false;
    private static boolean bossUp=false;
    private static boolean bossDown=false;
    public static boolean isBoss2Down() {
        return boss2Down;
    }
    public static void setBoss2Down(boolean boss2Down) {
        GameView.boss2Down = boss2Down;
    }
    public static boolean isBoss2Up() {
        return boss2Up;
    }
    public static void setBoss2Up(boolean boss2Up) {
        GameView.boss2Up = boss2Up;
    }
    private static boolean boss2Up=false;
    private static boolean boss2Down=false;
    public static boolean isBossDown() {
        return bossDown;
    }
    public static void setBossDown(boolean bossDown) {
        GameView.bossDown = bossDown;
    }
    public static boolean isBossUp() {
        return bossUp;
    }
    public static void setBossUp(boolean bossUp) {
        GameView.bossUp = bossUp;
    }
    public static long getEventDownDuration() {return eventDownDuration;}
    public static void setEventDownDuration(long eventDownDuration) {GameView.eventDownDuration = eventDownDuration;}
    public static boolean isBringBoss() {return bringBoss;}
    public static void setBringBoss(boolean bringBoss) {GameView.bringBoss = bringBoss;}
    public static boolean isBringBoss2() {return bringBoss2;}
    public static void setBringBoss2(boolean bringBoss2) {GameView.bringBoss2 = bringBoss2;}

    //public int getScreenW() {return screenW;}
    //public void setScreenW(int screenW) {this.screenW = screenW;}
    //public int getScreenH() {return screenH;}
    //public void setScreenH(int screenH) {this.screenH = screenH;}

    public void init(){
        myContext = getContext();
        MainActivity.score = 0;
        TitleActivity.coins = 0;
        i=1;
        scale = getContext().getResources().getDisplayMetrics().density;
        screenW = MainActivity.getsX();
        screenH = MainActivity.getsY();
        TitleActivity.preferences.edit().putInt("potentialHighscore",0).apply();
        wi=220*scale;
        he=3*scale;
        pepe_wi=196*scale;
        pepe_he=5*scale;
        boss_wi=244*scale;
        boss_he=5*scale;
        starSpeed = 6;
        paint = new Paint();
        bulletPaint = new Paint();
        redPaint = new Paint();
        bluePaint = new Paint();
        greenPaint = new Paint();
        yellowPaint = new Paint();
        whitePaint = new Paint();
        bulletPaint.setColor(Color.YELLOW);
        paint.setColor(Color.WHITE);
        paint.setTextSize((float)(23.4*(scale/1.5)));
        paint.setStrokeWidth(scale);
        redPaint.setColor(Color.RED);
        bluePaint.setColor(Color.BLUE);
        greenPaint.setColor(Color.GREEN);
        yellowPaint.setColor(Color.YELLOW);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setTextSize((float)(70*(scale/1.5)));
        whitePaint.setTextAlign(Paint.Align.CENTER);
        pepeBulletPaint = new Paint();
        pepeBulletPaint.setColor(Color.RED);
        bossBulletPaint = new Paint();
        bossBulletPaint.setColor(Color.BLUE);
        player = new Boltship();
        enemy = new Enemy(0,0);
        densityDpi = getContext().getResources().getDisplayMetrics().densityDpi;
        //Log.d(TAG, "densityDPI: " + densityDpi);
        t.tempPepe = Assets.pepeBoss;
        projectiles = player.getProjectiles();
        enemies = enemy.getEnemies();
        pepeboss = new PepeBoss((int)(MainActivity.getsX()- Assets.pepeBoss.getWidth()),0);
        bossship = new BossShip((int)(MainActivity.getsX()),0);
        bossship2 = new Boss2(MainActivity.getsX(),0);
        pepeboss.init();
        bossship.init();
        bossship2.init();
        turretGun = new Turret(0,0);
        pepeProjectiles = pepeboss.getPepeProjectiles();
        bossProjectiles = bossship.getBossProjectiles();
        boss2Projectiles = bossship2.getBoss2Projectiles();
        turretProjectiles = turretGun.getTurretProjectiles();
        d = new Enemy(0,0);
        s = new Projectiles(0,0);
        enemyTimer = new Timer();
    }
}