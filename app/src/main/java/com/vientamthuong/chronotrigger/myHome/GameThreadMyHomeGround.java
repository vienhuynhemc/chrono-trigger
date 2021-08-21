package com.vientamthuong.chronotrigger.myHome;

import com.vientamthuong.chronotrigger.presonMap.GameWorldPresonMap;

public class GameThreadMyHomeGround extends  Thread{
    private final GameWorldMyHomeGround gameWorldMyHomeGround;
    private boolean isRunning;

    public GameThreadMyHomeGround(MyHomeGroundActivity myHomeGroundActivity, boolean isStartIntro,boolean isLoad) {
        gameWorldMyHomeGround = new GameWorldMyHomeGround(myHomeGroundActivity, GameThreadMyHomeGround.this, isStartIntro,isLoad);
    }

    @Override
    public void run() {
        int FPS = 160;
        long beginTime = System.nanoTime();
        long timeForOneFrame = 1000000000 / FPS;
        while (isRunning) {
            long timeSleep = timeForOneFrame - (System.nanoTime() - beginTime);
            gameWorldMyHomeGround.update();
            gameWorldMyHomeGround.draw();
            try {
                if (timeSleep > 0) {
                    sleep(timeSleep / 1000000);
                } else
                    sleep(timeForOneFrame / 2000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            beginTime = System.nanoTime();
        }
    }

    public GameWorldMyHomeGround getGameWorldMyHomeGround(){
        return this.gameWorldMyHomeGround;
    }
    // Getter and setter
    public void setRunning(boolean running) {
        isRunning = running;
    }
}
