package com.vientamthuong.chronotrigger.camera;

public class Camera {

    // Khai báo các thuộc tính
    // Tọa độ của camera
    private int x;
    private int y;
    // Kích thước của camera
    private int width;
    private int height;

    public Camera(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = ConfigurationCamera.WIDTH;
        this.height = ConfigurationCamera.HEIGHT;
    }

    public void update() {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
