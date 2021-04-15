package com.vientamthuong.chronotrigger.presonMap;

import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.gameEffect.Animation;
import com.vientamthuong.chronotrigger.interfaceGameThread.UpdateAndDraw;

public class BackgroundMapPresonMap implements UpdateAndDraw {

    // Khai báo các thuộc tính
    // Image view tương ứng
    private final ImageView imageView;
    // Animation của backgroundmap
    private Animation animation;
    // Appcompat activit để lấy resource
    private final AppCompatActivity appCompatActivity;
    // Nhận vô game word chứa nó để lấy camera
    private final GameWorldPresonMap gameWorldPresonMap;

    public BackgroundMapPresonMap(ImageView imageView, AppCompatActivity appCompatActivity, GameWorldPresonMap gameWorldPresonMap) {
        this.imageView = imageView;
        this.appCompatActivity = appCompatActivity;
        this.gameWorldPresonMap = gameWorldPresonMap;
        // Khởi tạo
        init();
    }

    private void init() {
        animation = SourceAnimation.getInstance().getAnimation("preson_map_background");
    }

    @Override
    public void update() {
        // Cập nhật lại tọa độ của background theo camera
        imageView.setTranslationX(-gameWorldPresonMap.getCamera().getX());
        imageView.setTranslationY(-gameWorldPresonMap.getCamera().getY());
    }

    @Override
    public void draw() {
        // update and draw Animation
        animation.update();
        animation.draw(imageView, appCompatActivity);
    }
}
