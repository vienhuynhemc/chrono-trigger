package com.vientamthuong.chronotrigger.mainCharacter;

import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.gameEffect.Animation;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;
import com.vientamthuong.chronotrigger.mainModel.GameWorld;
import com.vientamthuong.chronotrigger.mainModel.Joystick;
import com.vientamthuong.chronotrigger.myHome.GameWorldMyHome;

public class Chrono implements Observer {

    public static final int MAX_SPEED = 4;

    // Trạng thái
    private int state;
    public static final int DI = 0;
    public static final int DUNG_IM = 1;

    // Chạy nhanh
    private boolean isChayNhanh;

    // Hướng
    private int dir;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;
    public static final int BOTTOM = 3;
    private final ImageView imageView;
    // Tọa độ thông thường
    private int x;
    private int y;
    private final int z;
    // Tạo đô để vẽ
    private int xDraw;
    private int yDraw;
    private Animation hanhDongDungImXuong;
    private Animation hanhDongDungImLen;
    private Animation hanhDongDungImPhai;
    private Animation hanhDongDungImTrai;
    private Animation hanhDongDiTrai;
    private Animation hanhDongDiPhai;
    private Animation hanhDongDiLen;
    private Animation hanhDongDiXuong;
    private Animation hanhDongChayTrai;
    private Animation hanhDongChayPhai;
    private Animation hanhDongChayLen;
    private Animation hanhDongChayXuong;

    private final AppCompatActivity appCompatActivity;
    private final GameWorld gameWorld;
    // Thời gian update
    private long lastTimeUpdate;

    public Chrono(ImageView imageView, int x, int y, int z, AppCompatActivity appCompatActivity, GameWorld gameWorld, int dir, int state) {
        this.imageView = imageView;
        this.x = x;
        this.y = y;
        this.z = z;
        this.dir = dir;
        this.state = state;
        this.gameWorld = gameWorld;
        this.appCompatActivity = appCompatActivity;
        init();
    }

    private void init() {
        // create animation
        hanhDongDungImLen = SourceAnimation.getInstance().getAnimation("crono_dung_im_len");
        hanhDongDungImXuong = SourceAnimation.getInstance().getAnimation("crono_dung_im_xuong");
        hanhDongDungImPhai = SourceAnimation.getInstance().getAnimation("crono_dung_im_phai");
        hanhDongDungImTrai = SourceAnimation.getInstance().getAnimation("crono_dung_im_phai");
        hanhDongDungImTrai.flip();
        hanhDongDiTrai = SourceAnimation.getInstance().getAnimation("crono_di_phai");
        hanhDongDiTrai.flip();
        hanhDongDiPhai = SourceAnimation.getInstance().getAnimation("crono_di_phai");
        hanhDongDiLen = SourceAnimation.getInstance().getAnimation("crono_di_len");
        hanhDongDiXuong = SourceAnimation.getInstance().getAnimation("crono_di_xuong");
        hanhDongChayTrai = SourceAnimation.getInstance().getAnimation("crono_chay_phai");
        hanhDongChayTrai.flip();
        hanhDongChayPhai = SourceAnimation.getInstance().getAnimation("crono_chay_phai");
        hanhDongChayLen = SourceAnimation.getInstance().getAnimation("crono_chay_len");
        hanhDongChayXuong = SourceAnimation.getInstance().getAnimation("crono_chay_xuong");
        // Set lại tọa độ theo camera
        xDraw = x - gameWorld.getXCamera();
        yDraw = y - gameWorld.getYCamera();
        // Cập nhật lại tọa độ của background theo camera
        imageView.setX(xDraw);
        imageView.setY(yDraw);
        // Cập nhật lại tạo độ của z
        imageView.setTranslationZ(z);
        // Tạo bộ điều kiển
        gameWorld.setJoystick(new Joystick(1000, gameWorld));
    }

    @Override
    public void update() {
        if (state == DI) {
            int spaceX = (int) (gameWorld.getJoystick().getActuatorX() * MAX_SPEED);
            int spaceY = (int) (gameWorld.getJoystick().getActuatorY() * MAX_SPEED);
            x += spaceX;
            y += spaceY;
            isChayNhanh = spaceX == 3 || spaceY == 3 || spaceX == -3 || spaceY == -3;
            // Cập nhật lại hướng của crono
            updateDir();
        }
        // Set lại tọa độ theo camera
        xDraw = x - gameWorld.getXCamera();
        yDraw = y - gameWorld.getYCamera();
        // Cập nhật lại tọa độ của background theo camera
        appCompatActivity.runOnUiThread(() -> {
            imageView.setX(xDraw);
            imageView.setY(yDraw);
        });
        // Update ban đầu
        if (lastTimeUpdate == 0) {
            lastTimeUpdate = System.currentTimeMillis();
        }
    }

    private void updateDir() {
        if (this.gameWorld.getJoystick() != null) {
            if (gameWorld.getJoystick().getActuatorY() <= 0) {
                double x = Math.abs(gameWorld.getJoystick().getActuatorX());
                double y = Math.abs(gameWorld.getJoystick().getActuatorY());
                if (y >= x) {
                    appCompatActivity.runOnUiThread(() -> imageView.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(120, 222))));
                    dir = TOP;
                } else {
                    if (isChayNhanh) {
                        appCompatActivity.runOnUiThread(() -> imageView.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(168, 216))));
                    } else {
                        appCompatActivity.runOnUiThread(() -> imageView.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(144, 216))));
                    }
                    if (gameWorld.getJoystick().getActuatorX() >= 0) {
                        dir = RIGHT;
                    } else {
                        dir = LEFT;
                    }
                }
            } else {
                double x = Math.abs(gameWorld.getJoystick().getActuatorX());
                double y = Math.abs(gameWorld.getJoystick().getActuatorY());
                if (y >= x) {
                    appCompatActivity.runOnUiThread(() -> imageView.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(120, 222))));
                    dir = BOTTOM;
                } else {
                    if (isChayNhanh) {
                        appCompatActivity.runOnUiThread(() -> imageView.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(168, 216))));
                    } else {
                        appCompatActivity.runOnUiThread(() -> imageView.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(144, 216))));
                    }
                    if (gameWorld.getJoystick().getActuatorX() >= 0) {
                        dir = RIGHT;
                    } else {
                        dir = LEFT;
                    }
                }
            }
        }
    }

    @Override
    public void draw() {
        // update and draw animation
        switch (state) {
            case DUNG_IM:
                if (dir == BOTTOM) {
                    hanhDongDungImXuong.update();
                    hanhDongDungImXuong.draw(imageView, appCompatActivity);
                } else if (dir == TOP) {
                    hanhDongDungImLen.update();
                    hanhDongDungImLen.draw(imageView, appCompatActivity);
                } else if (dir == LEFT) {
                    hanhDongDungImTrai.update();
                    hanhDongDungImTrai.draw(imageView, appCompatActivity);
                } else if (dir == RIGHT) {
                    hanhDongDungImPhai.update();
                    hanhDongDungImPhai.draw(imageView, appCompatActivity);
                }
                break;
            case DI:
                if (dir == BOTTOM) {
                    if (isChayNhanh) {
                        hanhDongChayXuong.update();
                        hanhDongChayXuong.draw(imageView, appCompatActivity);
                    } else {
                        hanhDongDiXuong.update();
                        hanhDongDiXuong.draw(imageView, appCompatActivity);
                    }
                } else if (dir == TOP) {
                    if (isChayNhanh) {
                        hanhDongChayLen.update();
                        hanhDongChayLen.draw(imageView, appCompatActivity);
                    } else {
                        hanhDongDiLen.update();
                        hanhDongDiLen.draw(imageView, appCompatActivity);
                    }
                } else if (dir == LEFT) {
                    if (isChayNhanh) {
                        hanhDongChayTrai.update();
                        hanhDongChayTrai.draw(imageView, appCompatActivity);
                    } else {
                        hanhDongDiTrai.update();
                        hanhDongDiTrai.draw(imageView, appCompatActivity);
                    }
                } else if (dir == RIGHT) {
                    if (isChayNhanh) {
                        hanhDongChayPhai.update();
                        hanhDongChayPhai.draw(imageView, appCompatActivity);
                    } else {
                        hanhDongDiPhai.update();
                        hanhDongDiPhai.draw(imageView, appCompatActivity);
                    }
                }
                break;
        }
    }

    @Override
    public boolean isOutCamera() {
        return false;
    }

    @Override
    public void outToLayout() {
        appCompatActivity.runOnUiThread(() -> gameWorld.getAbsoluteLayout().removeView(imageView));
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
