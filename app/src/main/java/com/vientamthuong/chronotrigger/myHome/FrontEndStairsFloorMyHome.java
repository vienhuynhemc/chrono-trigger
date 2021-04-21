package com.vientamthuong.chronotrigger.myHome;

import android.view.ViewManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.gameEffect.Animation;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;

public class FrontEndStairsFloorMyHome implements Observer {

    // Khai báo các thuộc tính
    // Image view tương ứng
    private final ImageView imageView;
    // Animation của backgroundmap
    private Animation animation;
    // Appcompat activit để lấy resource
    private final AppCompatActivity appCompatActivity;
    // Nhận vô game word chứa nó để lấy camera
    private final GameWorldMyHome gameWorldMyHome;
    // Tọa độ
    private final int x;
    private final int y;
    private final int z;
    // Tạo đô để vẽ
    private int xDraw;
    private int yDraw;

    public FrontEndStairsFloorMyHome(ImageView imageView, int x, int y, int z, AppCompatActivity appCompatActivity, GameWorldMyHome gameWorldMyHome) {
        this.imageView = imageView;
        this.appCompatActivity = appCompatActivity;
        this.gameWorldMyHome = gameWorldMyHome;
        this.x = x;
        this.y = y;
        this.z = z;
        // Khởi tạo
        init();
    }

    private void init() {
        // Set lại tọa độ theo camera
        xDraw = x - gameWorldMyHome.getCameraMyHome().getX();
        yDraw = y - gameWorldMyHome.getCameraMyHome().getY();
        // Cập nhật lại tọa độ của background theo camera
        imageView.setX(xDraw);
        imageView.setY(yDraw);
        // Cập nhật lại tạo độ của z
        imageView.setTranslationZ(z);
        animation = SourceAnimation.getInstance().getAnimation("tang_tren_cau_thang_my_home");
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
    }

    @Override
    public void draw() {
        // update and draw Animation
        animation.update();
        animation.draw(imageView, appCompatActivity);
    }

    @Override
    public void outToLayout() {
        appCompatActivity.runOnUiThread(() -> ((ViewManager) imageView.getParent()).removeView(imageView));
    }

    @Override
    public boolean isOutCamera() {
        return false;
    }
}
