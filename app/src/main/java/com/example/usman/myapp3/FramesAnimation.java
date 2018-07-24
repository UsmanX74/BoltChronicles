package com.example.usman.myapp3;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.ArrayList;

public class FramesAnimation {

    int totalFrames;
    int currentFrameIndex;
    private boolean movement;
    private long frameDurationInMilliS;
    private long lastFrameChangeTime;
    private ArrayList<Bitmap> frames;
    int animationCounter = 0;
    int animationCycles; // -1 means infinite animation cycles until stop method is called.
    private boolean firstTime = true;


    public FramesAnimation(long frameDurationInMilliS, int totalFrames, boolean movement){
        frames = new ArrayList<>();
        this.frameDurationInMilliS = frameDurationInMilliS;
        this.movement = movement;
        this.totalFrames = totalFrames;
    }

    public void addFrame(Bitmap frame){
        this.frames.add(frame);
    }

    public void drawAnimation(Canvas canvas, int x, int y, int animationCycles){
        this.animationCycles = animationCycles;
        if(!movement){
            if(!firstTime){
                if(animationCounter < (this.animationCycles*totalFrames)) {
                canvas.drawBitmap(frames.get(currentFrameIndex), x, y, null);
                manageFrames();
                }
            }
        }
    }
    public void animate(){
        firstTime = false;
        animationCounter = 0;
        currentFrameIndex = 0;
    }

    private void manageFrames() {
        long time = System.currentTimeMillis();
            if (time > lastFrameChangeTime + frameDurationInMilliS) {
                lastFrameChangeTime = time;
                currentFrameIndex++;
                animationCounter++;
                if (currentFrameIndex >= totalFrames) {
                    currentFrameIndex = 0;
                }
            }

    }
}
