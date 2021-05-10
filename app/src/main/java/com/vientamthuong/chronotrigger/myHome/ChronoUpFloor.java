package com.vientamthuong.chronotrigger.myHome;

import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.gameEffect.Animation;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;
import com.vientamthuong.chronotrigger.mainCharacter.Chrono;

public class ChronoUpFloor implements Observer {

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
    private Animation hanhDongDungImLen;
    private final AppCompatActivity appCompatActivity;
    private final GameWorldMyHome gameWorldMyHome;
    // Thời gian update
    private long lastTimeUpdate;

    public ChronoUpFloor(ImageView imageView, int x, int y, int z, AppCompatActivity appCompatActivity, GameWorldMyHome gameWorldMyHome) {
        this.imageView = imageView;
        this.x = x;
        this.y = y;
        this.z = z;
        this.gameWorldMyHome = gameWorldMyHome;
        this.appCompatActivity = appCompatActivity;
        init();
    }

    private void init() {
        // Khởi tạo ban đầu là đứng im và hướng là trên
        dir = TOP;
        state = DUNG_IM;
        // create animation
        hanhDongDungImLen = SourceAnimation.getInstance().getAnimation("crono_dung_im_len");
        // Set lại tọa độ theo camera
        xDraw = x - gameWorldMyHome.getCameraMyHome().getX();
        yDraw = y - gameWorldMyHome.getCameraMyHome().getY();
        // Cập nhật lại tọa độ của background theo camera
        imageView.setX(xDraw);
        imageView.setY(yDraw);
        // Cập nhật lại tạo độ của z
        imageView.setTranslationZ(z);
    }

    @Override
    public void update() {
        // Set lại tọa độ theo camera
        xDraw = x - gameWorldMyHome.getCameraMyHome().getX();
        yDraw = y - gameWorldMyHome.getCameraMyHome().getY();
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
                if (dir == RIGHT) {
                } else if (dir == LEFT) {
                } else if (dir == TOP) {
                    hanhDongDungImLen.update();
                    hanhDongDungImLen.draw(imageView, appCompatActivity);
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
        appCompatActivity.runOnUiThread(() -> gameWorldMyHome.getMyHomeActivity().getAbsoluteLayout().removeView(imageView));
    }
}
