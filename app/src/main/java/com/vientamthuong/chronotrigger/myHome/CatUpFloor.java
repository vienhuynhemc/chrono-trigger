package com.vientamthuong.chronotrigger.myHome;

import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.gameEffect.Animation;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;

public class CatUpFloor implements Observer {

    // Trạng thái
    private int state;
    public static final int NAM = 0;
    public static final int NGOI = 1;
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
    private Animation hanhDongNgoiPhai, hanhDongNgoiTrai;
    private final AppCompatActivity appCompatActivity;
    private final GameWorldMyHome gameWorldMyHome;
    // Thời gian update
    private long lastTimeUpdate;

    public CatUpFloor(ImageView imageView, int x, int y, int z, AppCompatActivity appCompatActivity, GameWorldMyHome gameWorldMyHome) {
        this.imageView = imageView;
        this.x = x;
        this.y = y;
        this.z = z;
        this.gameWorldMyHome = gameWorldMyHome;
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
        if (System.currentTimeMillis() - lastTimeUpdate > 4000 && state == NAM) {
            state = NGOI;
            // Điều chỉnh lại width height
            appCompatActivity.runOnUiThread(() -> imageView.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(78, 96))));
            // Điều chỉnh lại tọa độ
            y = y - 30;
        }
    }

    @Override
    public void draw() {
        // update and draw animation
        switch (state) {
            case NAM:
                if (dir == RIGHT) {
                    hanhDongNamPhai.update();
                    hanhDongNamPhai.draw(imageView, appCompatActivity);
                } else if (dir == LEFT) {
                    hanhDongNamTrai.update();
                    hanhDongNamTrai.draw(imageView, appCompatActivity);
                }
                break;
            case NGOI:
                if (dir == RIGHT) {
                    hanhDongNgoiPhai.update();
                    hanhDongNgoiPhai.draw(imageView, appCompatActivity);
                } else {
                    hanhDongNgoiTrai.update();
                    hanhDongNgoiTrai.draw(imageView, appCompatActivity);
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
        appCompatActivity.runOnUiThread(() -> ((ViewManager) imageView.getParent()).removeView(imageView));
    }

}
