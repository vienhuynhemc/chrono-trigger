package com.vientamthuong.chronotrigger.myHome;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.gameEffect.Animation;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;

import java.util.ArrayList;
import java.util.List;

public class CronoMyHomeGround implements Observer {
    // Trạng thái
    private int state;
    public static final int DI = 0;
    public static final int DUNG_IM = 1;
    public static final int GIO_TAY = 2;
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
    private Animation hanhDongDungImTrai, hanhDongDungImPhai, hanhDongDungImLen, hanhDongDungImXuong;
    private Animation hanhDongDiTrai, hanhDongDiPhai, hanhDongDiLen, hanhDongDiXuong;
    private final AppCompatActivity appCompatActivity;
    private final GameWorldMyHomeGround gameWorldMyHomeGround;
    // Thời gian update
    private long lastTimeUpdate;
    // Boolean các khung thời gian
    private boolean isStartMove1;

    // out camera
    private boolean out;

    public CronoMyHomeGround(ImageView imageView, int x, int y, int z, AppCompatActivity appCompatActivity, GameWorldMyHomeGround gameWorldMyHomeGround) {
        this.imageView = imageView;
        this.x = x;
        this.y = y;
        this.z = z;
        this.appCompatActivity = appCompatActivity;
        this.gameWorldMyHomeGround = gameWorldMyHomeGround;
        init();
    }

    private void init() {
        dir = RIGHT;
        state = DI;
        hanhDongDungImPhai = SourceAnimation.getInstance().getAnimation("crono_dung_im_phai");
        hanhDongDungImTrai = SourceAnimation.getInstance().getAnimation("crono_dung_im_phai");
        hanhDongDungImTrai.flip();
        hanhDongDungImLen = SourceAnimation.getInstance().getAnimation("crono_dung_im_len");
        hanhDongDungImXuong = SourceAnimation.getInstance().getAnimation("crono_dung_im_xuong");
        hanhDongDiPhai = SourceAnimation.getInstance().getAnimation("crono_di_phai");
        hanhDongDiTrai = SourceAnimation.getInstance().getAnimation("crono_di_phai");
        hanhDongDiTrai.flip();
        hanhDongDiLen = SourceAnimation.getInstance().getAnimation("crono_di_len");
        hanhDongDiXuong = SourceAnimation.getInstance().getAnimation("crono_di_xuong");
        // Set lại tọa độ theo camera
        xDraw = x - gameWorldMyHomeGround.getCameraMyHomeGround().getX();
        yDraw = y - gameWorldMyHomeGround.getCameraMyHomeGround().getY();
        // Cập nhật lại tọa độ của background theo camera
        imageView.setX(xDraw);
        imageView.setY(yDraw);
        // Cập nhật lại tạo độ của z
        imageView.setTranslationZ(z);
    }

    @Override
    public void update() {
        // Set lại tọa độ theo camera
        xDraw = x - gameWorldMyHomeGround.getCameraMyHomeGround().getX();
        yDraw = y - gameWorldMyHomeGround.getCameraMyHomeGround().getY();
        // Cập nhật lại tọa độ của background theo camera
        appCompatActivity.runOnUiThread(() -> {
            imageView.setX(xDraw);
            imageView.setY(yDraw);
        });
        // Update ban đầu
        if (lastTimeUpdate == 0) {
            lastTimeUpdate = System.currentTimeMillis();
        }
        if (isStartMove1) {
            if (x < 1500) {
                x += 1;
                y += 1;
            } else {
                state = DUNG_IM;
                dir = BOTTOM;
                appCompatActivity.runOnUiThread(() -> imageView.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(120, 222))));
                gameWorldMyHomeGround.setState(GameWorldMyHomeGround.CHAT_FIRST);
                isStartMove1 = false;
            }
        }
    }

    @Override
    public void draw() {
        switch (state) {
            case DI:
                if (dir == RIGHT) {
                    hanhDongDiPhai.update();
                    hanhDongDiPhai.draw(imageView, appCompatActivity);
                }
                break;
            case DUNG_IM:
                if (dir == BOTTOM) {
                    hanhDongDungImXuong.update();
                    hanhDongDungImXuong.draw(imageView, appCompatActivity);
                }
        }
    }

    @Override
    public boolean isOutCamera() {
        return out;
    }

    @Override
    public void outToLayout() {
        appCompatActivity.runOnUiThread(() -> gameWorldMyHomeGround.getMyHomeGroundActivity().getAbsoluteLayout().removeView(imageView));
    }

    //getter setter

    public boolean isStartMove1() {
        return isStartMove1;
    }

    public void setStartMove1(boolean startMove1) {
        isStartMove1 = startMove1;
    }

    public boolean isOut() {
        return out;
    }

    public void setOut(boolean out) {
        this.out = out;
    }
}
