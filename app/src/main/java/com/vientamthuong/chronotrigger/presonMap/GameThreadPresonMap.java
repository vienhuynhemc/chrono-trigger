package com.vientamthuong.chronotrigger.presonMap;

public class GameThreadPresonMap extends Thread {

    private final GameWorldPresonMap gameWorldPresonMap;
    private boolean isRunning;

    public GameThreadPresonMap(PresonMapActivity presonMapActivity) {
        gameWorldPresonMap = new GameWorldPresonMap(presonMapActivity);
    }

    @Override
    public void run() {
        int FPS = 160;
        long beginTime = System.nanoTime();
        long timeForOneFrame = 1000000000 / FPS;
        while (isRunning) {
            long timeSleep = timeForOneFrame - (System.nanoTime() - beginTime);
            gameWorldPresonMap.update();
            gameWorldPresonMap.draw();
            try {
                if (timeSleep > 0) {
                    Thread.sleep(timeSleep / 1000000);
                } else
                    Thread.sleep(timeForOneFrame / 2000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            beginTime = System.nanoTime();
        }
    }

    // Getter and setter
    public void setRunning(boolean running) {
        isRunning = running;
    }
}
