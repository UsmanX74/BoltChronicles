package com.example.usman.myapp3;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.jinatonic.confetti.ConfettiManager;
import com.github.jinatonic.confetti.ConfettiSource;
import com.github.jinatonic.confetti.ConfettoGenerator;
import com.github.jinatonic.confetti.confetto.Confetto;

import java.util.Random;

import static com.example.usman.myapp3.TitleView.SCALE_CONST;
import static com.example.usman.myapp3.TitleView.scale;

public class HighScoresView extends View{
    TextPaint tp,tp1;
    int a=0;
    Random generator;
    Paint paint;
    FramesAnimation blastAnimation;
    FramesAnimation saberAnimation;
    float[] values;

    //ANIMATION CODE
    long fps;

    // This is used to help calculate the fps
    private long timeThisFrame;

    // Declare an object of type Bitmap
    Bitmap bitmapBob;

    // Bob starts off not moving
    boolean isMoving = false;

    // He can walk at 150 pixels per second
    float walkSpeedPerSecond = 12;

    // He starts 10 pixels from the left
    float bobXPosition = 10;

    // New for the sprite sheet animation

    // These next two values can be anything you like
    // As long as the ratio doesn't distort the sprite too much
    private float frameWidth = 185;
    private float frameHeight = (float)186.2;

    // How many frames are there on the sprite sheet?
    private int frameCount = 4;

    // Start at the first frame - where else?
    private int currentFrame = 0;

    // What time was it when we last changed frames
    private long lastFrameChangeTime = 0;

    // How long should each frame last
    private int frameLengthInMilliseconds = 100;
    private int x=20;
    private int y =20;
    ConfettoGenerator confettoGenerator;


    // A rectangle to define an area of the
    // sprite sheet that represents 1 frame
    private Rect frameToDraw = new Rect(0, 0, (int)frameWidth, (int)frameHeight);

    // A rect that defines an area of the screen
    // on which to draw
    RectF whereToDraw = new RectF(bobXPosition,0, bobXPosition + frameWidth, frameHeight);



    public HighScoresView(Context context){
        super(context);
        blastAnimation = new FramesAnimation(50,12,false);
        blastAnimation.addFrame(Assets.explosion1);
        blastAnimation.addFrame(Assets.explosion2);
        blastAnimation.addFrame(Assets.explosion3);
        blastAnimation.addFrame(Assets.explosion4);
        blastAnimation.addFrame(Assets.explosion5);
        blastAnimation.addFrame(Assets.explosion6);
        blastAnimation.addFrame(Assets.explosion7);
        blastAnimation.addFrame(Assets.explosion8);
        blastAnimation.addFrame(Assets.explosion9);
        blastAnimation.addFrame(Assets.explosion10);
        blastAnimation.addFrame(Assets.explosion11);
        blastAnimation.addFrame(Assets.explosion12);
        saberAnimation = new FramesAnimation(25,8,false);
        saberAnimation.addFrame(Assets.saber1);
        saberAnimation.addFrame(Assets.saber2);
        saberAnimation.addFrame(Assets.saber3);
        saberAnimation.addFrame(Assets.saber4);
        saberAnimation.addFrame(Assets.saber5);
        saberAnimation.addFrame(Assets.saber6);
        saberAnimation.addFrame(Assets.saber7);
        saberAnimation.addFrame(Assets.saber8);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(scale);
        generator = new Random();
        tp = new TextPaint();
        tp1 = new TextPaint();
        tp.setTextAlign(Paint.Align.CENTER);
        tp.setColor(Color.WHITE);
        tp1.setColor(Color.WHITE);
        tp.setTextSize((54*SCALE_CONST));
        tp1.setTextSize((28*SCALE_CONST));

        //ANIMATION CODE
        // Choose the brush color for drawing
        paint.setColor(Color.argb(255,  249, 129, 0));

        // Make the text a bit bigger
        paint.setTextSize(45);
        bitmapBob = BitmapFactory.decodeResource(this.getResources(), R.drawable.player3);

        // Scale the bitmap to the correct size
        // We need to do this because Android automatically
        // scales bitmaps based on screen density
        bitmapBob = Bitmap.createScaledBitmap(bitmapBob, (int)(frameWidth * frameCount), (int)frameHeight, false);
        confettoGenerator = new ConfettoGenerator() {
            @Override
            public Confetto generateConfetto(Random random) {
                return new Particles(Assets.particle_red);
                //return new BitmapConfetto(particle_blue);
            }
        };

    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        long startFrameTime = System.currentTimeMillis();
        canvas.drawRGB(0,0,0);
        canvas.drawText("ANIMATION TESTS",getWidth()/2,(float)(getHeight()*0.15),tp);
        //canvas.drawText("tm 3 translate x: "+ values[2],)
        //ANIMATION CODE


        //update frames
        update();

        // Display the current fps on the screen
        //canvas.drawText("FPS:" + fps, 20, 40, paint);
        canvas.drawText("lastFrameChangeTime: "+(int)(lastFrameChangeTime/1000),200,250,paint);

        // Draw bob at bobXPosition, 200 pixels
        //canvas.drawBitmap(bitmapBob, bobXPosition, 200, paint);
        blastAnimation.drawAnimation(canvas,100,470,1);
        canvas.drawText("animation counter: "+blastAnimation.animationCounter,200,300,paint);
        canvas.drawText("all frames of all cycles:"+(blastAnimation.animationCycles*blastAnimation.totalFrames), 200,350,paint);
        //canvas.drawText("current frame index: "+blastAnimation.currentFrameIndex,200,400,paint);
        canvas.drawText("x: "+x,200,395,paint);
        canvas.drawText("y: "+y,200,440,paint);
        saberAnimation.drawAnimation(canvas,400,500,1);
        canvas.drawBitmap(Assets.boltShip,376,532,null); //offset for saber: 400-376=24 , 532-500=32
                                                                            //experimental offset for particles: 378, 546
        //blastAnimation.drawAnimation(canvas,450,450,1);

        whereToDraw.set((int)bobXPosition,0,(int)bobXPosition + frameWidth, frameHeight);
        getCurrentFrame();

        canvas.drawBitmap(bitmapBob, frameToDraw, whereToDraw, null);
        timeThisFrame = System.currentTimeMillis() - startFrameTime;
        if (timeThisFrame >= 1) {
            fps = 1000 / timeThisFrame;
        }
        invalidate();
    }

    public void update() {

        // If bob is moving (the player is touching the screen)
        // then move him to the right based on his target speed and the current fps.
        if(isMoving){
            bobXPosition = bobXPosition + (walkSpeedPerSecond);
            saberAnimation.animate();
            blastAnimation.animate();
        }
    }
    public ConfettiManager animateConfetti(Context context, ConfettoGenerator confettoGenerator, int confettiX, int confettiY, ViewGroup parentView){
        Log.d("viewgroup","ViewGroup in highscoresView: "+parentView);
        return new ConfettiManager(context,confettoGenerator,new ConfettiSource(confettiX,confettiY), parentView)
                .setEmissionRate(29)
                .setVelocityX(-80)
                .setVelocityY(0,38)
                .setEmissionDuration(600)
                .setTTL(800)
                .animate();
    }

    public void getCurrentFrame(){

        long time  = System.currentTimeMillis();
        if(isMoving) {// Only animate if bob is moving
            if ( time > lastFrameChangeTime + frameLengthInMilliseconds) {
                lastFrameChangeTime = time;
                currentFrame++;
                if (currentFrame >= frameCount) {
                    currentFrame = 0;
                }
            }
        }
        //update the left and right values of the source of
        //the next frame on the spritesheet
        frameToDraw.left = (int)(currentFrame * frameWidth);
        frameToDraw.right = (int)(frameToDraw.left + frameWidth);

    }



    public boolean onTouchEvent(MotionEvent event) {
        int eventaction = event.getAction();
        int X = (int) event.getX();
        int Y = (int) event.getY();
        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                isMoving = true;
                break;
            case MotionEvent.ACTION_UP:
                animateConfetti(getContext(),confettoGenerator,378,546,(ViewGroup) this.getParent().getParent());
                isMoving = false;
                break;
            case MotionEvent.ACTION_MOVE:
                x = X;
                y = Y;

                //.getParent() is
                //used twice since doing it first time only returns the ViewFlipper object, but doing it again returns the proper
                //parent activity class, this is required here and not in titleview because this Highscores view is inside a ViewFlipper
                //unlike title view, which is just a regular view.
        }
        invalidate();
        return true;
    }
}

/*
* public class Animation {
    Bitmap bitmapSheet;
    String bitmapName;
    private Rect sourceRect;
    private int frameCount;
    private int currentFrame;
    private long frameTicker;
    private int framePeriod;
    private int frameWidth;
    private int frameHeight;
    int pixelsPerMetre;

    Animation(Context context, String bitmapName, float frameHeight, float frameWidth,
              int animFps, int frameCount, int pixelsPerMetre){

        this.currentFrame = 0;
        this.frameCount = frameCount;

        this.frameWidth = (int)frameWidth * pixelsPerMetre;
        this.frameHeight = (int)frameHeight * pixelsPerMetre;
        sourceRect = new Rect(0, 0, this.frameWidth, this.frameHeight);
        framePeriod = 1000 / animFps;
        frameTicker = 0l;
        this.bitmapName = "" + bitmapName;
        this.pixelsPerMetre = pixelsPerMetre;
    }
    public Rect getCurrentFrame(long time, float xVelocity, boolean moves){

        if(xVelocity!=0 || moves == false) {// Only animate if the object is moving or it is an object which doesn't move
            if (time > frameTicker + framePeriod) {
                frameTicker = time;
                currentFrame++;
                if (currentFrame >= frameCount) {
                    //if (currentFrame >= frameCount) {
                    currentFrame = 0;
                }
            }
        }
        //update the left and right values of the source of
        //the next frame on the spritesheet
        this.sourceRect.left = currentFrame * frameWidth;
        this.sourceRect.right = this.sourceRect.left + frameWidth;

        return sourceRect;
    }

}
* */
