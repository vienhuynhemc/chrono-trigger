package com.vientamthuong.chronotrigger.myHome;

import android.app.Dialog;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.gameEffect.Animation;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;

public class CatInGround implements Observer {

    // Trạng thái
    private int state;
    public static final int NAM = 0;
    public static final int NGOI = 1;
    public static final int DI = 2;
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
    private int z;
    // Tạo đô để vẽ
    private int xDraw;
    private int yDraw;
    private Animation hanhDongNamPhai, hanhDongNamTrai;
    private Animation hanhDongNgoiPhai, hanhDongNgoiTrai, hanhDongNgoiLen, hanhDongNgoiXuong;
    private Animation hanhDongDiTrai, hanhDongDiPhai;
    private Animation hanhDongDiXuong, hanhDongDiLen;
    private final AppCompatActivity appCompatActivity;
    private final GameWorldMyHomeGround gameWorldMyHomeGround;
    // Thời gian update
    private long lastTimeUpdate;
    // Biến để update mèo di chuyển
    private boolean isSitting = true;
    private boolean isStartMove1;
    private boolean isStartMove2;

    public CatInGround(ImageView imageView, int x, int y, int z, AppCompatActivity appCompatActivity, GameWorldMyHomeGround gameWorldMyHomeGround) {
        this.imageView = imageView;
        this.x = x;
        this.y = y;
        this.z = z;
        this.gameWorldMyHomeGround = gameWorldMyHomeGround;
        this.appCompatActivity = appCompatActivity;
        init();
    }

    private void init() {
        // Khởi tạo ban đầu là năm và hướng là phải
        dir = RIGHT;
        state = NAM;
        hanhDongNamTrai = SourceAnimation.getInstance().getAnimation("yellow_cat_nam_trai");
        hanhDongNamPhai = SourceAnimation.getInstance().getAnimation("yellow_cat_nam_trai");
        hanhDongNamPhai.flip();
        hanhDongNgoiTrai = SourceAnimation.getInstance().getAnimation("yellow_cat_ngoi_trai");
        hanhDongNgoiPhai = SourceAnimation.getInstance().getAnimation("yellow_cat_ngoi_trai");
        hanhDongNgoiPhai.flip();
        hanhDongNgoiLen = SourceAnimation.getInstance().getAnimation("yellow_cat_ngoi_len");
        hanhDongNgoiXuong = SourceAnimation.getInstance().getAnimation("yellow_cat_ngoi_xuong");
        hanhDongDiTrai = SourceAnimation.getInstance().getAnimation("yellow_cat_di_trai");
        hanhDongDiPhai = SourceAnimation.getInstance().getAnimation("yellow_cat_di_trai");
        hanhDongDiPhai.flip();
        hanhDongDiXuong = SourceAnimation.getInstance().getAnimation("yellow_cat_di_xuong");
        hanhDongDiLen = SourceAnimation.getInstance().getAnimation("yellow_cat_di_len");
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
        //ngồi 1 s rồi di chuyển
        if (System.currentTimeMillis() - lastTimeUpdate <= 1000) {
            state = DI;
            dir = RIGHT;
            appCompatActivity.runOnUiThread(() -> imageView.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(96, 96))));
            isSitting = false;
        }
        if (!isSitting) {
            if (isStartMove1) {
                if (x < 1400)
                    x++;
                else {
                    dir = TOP;
                    isStartMove2 = true;
                    isStartMove1 = false;
                    appCompatActivity.runOnUiThread(() -> imageView.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(72, 96))));
                }
            }
            if (isStartMove2&&isStartMove1 == false) {
                if (y > 700) {
                    y--;
                }
                else {
                    state = NGOI;
                    dir = TOP;
                    // Điều chỉnh lại width height
                    appCompatActivity.runOnUiThread(() -> imageView.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(48, 96))));
                    // Điều chỉnh lại tọa độ
                    y = y - 30;
                    isStartMove2 = false;
                    isStartMove1= false;
                }
            }
        }
    }

    @Override
    public void draw() {
        switch (state) {
            case NAM:
                if (dir == RIGHT) {
                    hanhDongNamPhai.update();
                    hanhDongNamPhai.draw(imageView, appCompatActivity);
                }
                break;
            case DI:
                if (dir == RIGHT) {
                    hanhDongDiPhai.update();
                    hanhDongDiPhai.draw(imageView, appCompatActivity);
                }
                if (dir == TOP) {
                    hanhDongDiLen.update();
                    hanhDongDiLen.draw(imageView, appCompatActivity);
                }
                break;
            case NGOI:
                if (dir == TOP) {
                    hanhDongNgoiLen.update();
                    hanhDongNgoiLen.draw(imageView, appCompatActivity);
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

    }
    //getter setter

    public boolean isStartMove1() {
        return isStartMove1;
    }

    public void setStartMove1(boolean startMove1) {
        isStartMove1 = startMove1;
    }

    public boolean isStartMove2() {
        return isStartMove2;
    }

    public void setStartMove2(boolean startMove2) {
        isStartMove2 = startMove2;
    }
}
