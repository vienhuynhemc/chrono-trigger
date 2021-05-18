package com.vientamthuong.chronotrigger.mainCharacter;

import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.gameEffect.Animation;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;
import com.vientamthuong.chronotrigger.mainModel.GameWorld;
import com.vientamthuong.chronotrigger.myHome.GameWorldMyHome;

public class Chrono implements Observer {

    // Trạng thái
    private int state;
    public static final int DI = 0;
    public static final int DUNG_IM = 1;

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
        hanhDongDungImXuong = SourceAnimation.getInstance().getAnimation("crono_dung_im_xuong");
        // Set lại tọa độ theo camera
        xDraw = x - gameWorld.getXCamera();
        yDraw = y - gameWorld.getYCamera();
        // Cập nhật lại tọa độ của background theo camera
        imageView.setX(xDraw);
        imageView.setY(yDraw);
        // Cập nhật lại tạo độ của z
        imageView.setTranslationZ(z);
    }

    @Override
    public void update() {
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

    @Override
    public void draw() {
        // update and draw animation
        switch (state) {
            case DUNG_IM:
                if (dir == BOTTOM) {
                    hanhDongDungImXuong.update();
                    hanhDongDungImXuong.draw(imageView, appCompatActivity);
                }
                break;
            case DI:
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
}
