package com.vientamthuong.chronotrigger.myHome;

public class GameThreadMyHome extends Thread {

    private GameWorldMyHome gameWorldMyHome;
    private boolean isRunning;

    public GameThreadMyHome(MyHomeActivity myHomeActivity, boolean isStartIntro) {
        gameWorldMyHome = new GameWorldMyHome(myHomeActivity, GameThreadMyHome.this, isStartIntro);
    }

    @Override
    public void run() {
        int FPS = 160;
        long beginTime = System.nanoTime();
        long timeForOneFrame = 1000000000 / FPS;
        while (isRunning) {
            long timeSleep = timeForOneFrame - (System.nanoTime() - beginTime);
            gameWorldMyHome.update();
            gameWorldMyHome.draw();
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

    // Getter and setter
    public void setRunning(boolean running) {
        isRunning = running;
    }
}
