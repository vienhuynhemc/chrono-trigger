package com.vientamthuong.chronotrigger.presonMap;

import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.gameEffect.Animation;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;

public class BackgroundMapPresonMap implements Observer {

    // Khai báo các thuộc tính
    // Image view tương ứng
    private final ImageView imageView;
    // Animation của backgroundmap
    private Animation animation;
    // Appcompat activit để lấy resource
    private final AppCompatActivity appCompatActivity;
    // Nhận vô game word chứa nó để lấy camera
    private final GameWorldPresonMap gameWorldPresonMap;
    // Tọa độ
    private int x;
    private int y;

    public BackgroundMapPresonMap(ImageView imageView, AppCompatActivity appCompatActivity, GameWorldPresonMap gameWorldPresonMap) {
        this.imageView = imageView;
        this.appCompatActivity = appCompatActivity;
        this.gameWorldPresonMap = gameWorldPresonMap;
        // Mặc định tạo độ 0 0
        x = 0;
        y = 0;
        // Khởi tạo
        init();
    }

    private void init() {
        animation = SourceAnimation.getInstance().getAnimation("preson_map_background");
    }

    @Override
    public void update() {
        // Cập nhật lại tọa độ của background theo camera
        appCompatActivity.runOnUiThread(() -> {
            imageView.setX(x - gameWorldPresonMap.getCamera().getX());
            imageView.setY(y - gameWorldPresonMap.getCamera().getY());
        });
    }

    @Override
    public void draw() {
        // update and draw Animation
        animation.update();
        animation.draw(imageView, appCompatActivity);
    }

    @Override
    public boolean isOutCamera() {
        return false;
    }
}
