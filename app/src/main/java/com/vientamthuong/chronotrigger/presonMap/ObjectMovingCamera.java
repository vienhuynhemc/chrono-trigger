package com.vientamthuong.chronotrigger.presonMap;

public class ObjectMovingCamera {

    private final int x;
    private final int y;
    private long duration;

    public ObjectMovingCamera(int x, int y, long duration) {
        this.x = x;
        this.y = y;
        this.duration = duration;
    }

    public int getXCameraNeedAdd(int x) {
        return Integer.compare(this.x, x);
    }

    public int getYCameraNeedAdd(int y) {
        return Integer.compare(this.y, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
