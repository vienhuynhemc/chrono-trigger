package com.vientamthuong.chronotrigger.camera;

public class Camera {

    // Khai báo các thuộc tính
    // Tọa độ của camera
    private int x;
    private int y;
    // Kích thước của camera
    private final int width;
    private final int height;

    public Camera(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = ConfigurationCamera.WIDTH;
        this.height = ConfigurationCamera.HEIGHT;
    }

    public void update() {

    }

    public boolean isOutCamera(int x, int y, int width, int height) {
        // out bên trái
        if (x + width < this.x) {
            return true;
        }
        // out bên phải
        if (x > this.x + this.width) {
            return true;
        }
        // out bên trên
        if (y + height < this.y) {
            return true;
        }
        // out bên dưới
        if (y > this.y + this.height) {
            return true;
        }

        return false;
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
