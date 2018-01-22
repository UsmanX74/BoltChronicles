package com.example.usman.myapp3;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;


public class Assets {

    public static Bitmap boltShip,bossShip,bossShip2,bossShip3,pepeBoss,turret,coin,enemy1,enemy2,enemy3,enemy4,enemy5,turretButton,
    explosion1,explosion2,explosion3,explosion4,explosion5,explosion6,explosion7,explosion8,explosion9,explosion10,explosion11,
            explosion12,pepeHurt,healthBar,pepeHealthBar,bossHealthBar,tempPepe,enShip,shield,shieldButton,playButton,title,creditsButton,
            highscoresButton,optionsButton,storeButton,newTitle,right,pause,logo;
    //public static Bitmap [] asteroids = new Bitmap[60];
    public static int playerShoot,invaderExplode,coinPickup,playerShoot2;
    public static SoundPool sp;
    public static MediaPlayer mp;
    //145 boxes NICORIL = 20,000 Rs
    //public static MediaPlayer mp;


    public static void loadBitmaps(Resources res){
        logo = BitmapFactory.decodeResource(res,R.drawable.logo_white_small);
        boltShip = BitmapFactory.decodeResource(res,R.drawable.player1);
        bossShip = BitmapFactory.decodeResource(res,R.drawable.bossship);
        bossShip2 = BitmapFactory.decodeResource(res,R.drawable.bossship2);
        bossShip3 = BitmapFactory.decodeResource(res,R.drawable.bossship3);
        pepeBoss = BitmapFactory.decodeResource(res,R.drawable.pepe);
        pepeHurt = BitmapFactory.decodeResource(res,R.drawable.pepehurt);
        enemy1 = BitmapFactory.decodeResource(res,R.drawable.enemy1);
        enemy2 = BitmapFactory.decodeResource(res,R.drawable.enemy2);
        enemy3 = BitmapFactory.decodeResource(res,R.drawable.enemy3);
        enemy4 = BitmapFactory.decodeResource(res,R.drawable.enemy4);
        enemy5 = BitmapFactory.decodeResource(res,R.drawable.enemy5);
        turret = BitmapFactory.decodeResource(res,R.drawable.turret);
        turretButton = BitmapFactory.decodeResource(res,R.drawable.turretbutton);
        coin = BitmapFactory.decodeResource(res,R.drawable.coin);
        shield = BitmapFactory.decodeResource(res,R.drawable.shield);
        shieldButton = BitmapFactory.decodeResource(res,R.drawable.shieldbutton);
        healthBar = BitmapFactory.decodeResource(res,R.drawable.healthbar);
        pepeHealthBar = BitmapFactory.decodeResource(res,R.drawable.pepe_healthbar);
        bossHealthBar = BitmapFactory.decodeResource(res,R.drawable.bossship_healthbar);
        explosion1 = BitmapFactory.decodeResource(res,R.drawable.explosion1);
        explosion2 = BitmapFactory.decodeResource(res,R.drawable.explosion2);
        explosion3 = BitmapFactory.decodeResource(res,R.drawable.explosion3);
        explosion4 = BitmapFactory.decodeResource(res,R.drawable.explosion4);
        explosion5 = BitmapFactory.decodeResource(res,R.drawable.explosion5);
        explosion6 = BitmapFactory.decodeResource(res,R.drawable.explosion6);
        explosion7 = BitmapFactory.decodeResource(res,R.drawable.explosion7);
        explosion8 = BitmapFactory.decodeResource(res,R.drawable.explosion8);
        explosion9 = BitmapFactory.decodeResource(res,R.drawable.explosion9);
        explosion10 = BitmapFactory.decodeResource(res,R.drawable.explosion10);
        explosion11 = BitmapFactory.decodeResource(res,R.drawable.explosion11);
        explosion12 = BitmapFactory.decodeResource(res,R.drawable.explosion12);
        playButton = BitmapFactory.decodeResource(res,R.drawable.play_button);
        highscoresButton = BitmapFactory.decodeResource(res,R.drawable.highscores_button);
        creditsButton = BitmapFactory.decodeResource(res,R.drawable.credits_button);
        optionsButton = BitmapFactory.decodeResource(res,R.drawable.options_button);
        newTitle = BitmapFactory.decodeResource(res,R.drawable.title_new);
        storeButton = BitmapFactory.decodeResource(res,R.drawable.store_button);
        //title = BitmapFactory.decodeResource(res,R.drawable.title);
        right = BitmapFactory.decodeResource(res,R.drawable.right);
        pause = BitmapFactory.decodeResource(res,R.drawable.pause2);
        tempPepe = pepeBoss;
    }

    public static void loadSounds(Context context){
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        mp = new MediaPlayer();
        mp = MediaPlayer.create(context,R.raw.background_music);
        invaderExplode = sp.load(context,R.raw.invaderexplode,1);
        playerShoot = sp.load(context,R.raw.playershoot,2);
        coinPickup = sp.load(context,R.raw.coinpickup,3);
        playerShoot2 = sp.load(context,R.raw.playershoot2,4);
    }

    public static Bitmap getEnShip() {
        return enShip;
    }

    public static void setEnShip(Bitmap enShip) {
        Assets.enShip = enShip;
    }

    public static Bitmap getTempPepe() {
        return tempPepe;
    }

    public static void setTempPepe(Bitmap tempPepe) {
        Assets.tempPepe = tempPepe;
    }
}
