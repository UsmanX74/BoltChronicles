package com.example.usman.myapp3;

public class Timer {
    long startTime,startTime1;

    public Timer(){
        startTime = System.currentTimeMillis();
        startTime1 = System.currentTimeMillis();
    }

    public boolean isTime(int delayTime){//task happens after each interval that is specified by delay time...
        if(System.currentTimeMillis() - startTime >= delayTime){
            startTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean waitFor(int waitTime){//task happens as long as the waitTime is not reached.
        //this method returns true as long as the waitTime is not reached.
        if(System.currentTimeMillis() - startTime1 <= waitTime){
            return true;
        }
        return false;
    }
    public void resetWaitFor(){
        startTime1 = System.currentTimeMillis();
    }
    public void resetIsTime(){
        startTime = System.currentTimeMillis();
    }
}