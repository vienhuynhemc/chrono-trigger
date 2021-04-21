package com.vientamthuong.chronotrigger.myHome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.R;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;
import com.vientamthuong.chronotrigger.presonMap.ConfigurationPresonMap;
import com.vientamthuong.chronotrigger.presonMap.GameWorldPresonMap;

public class ObjectFullScreenMyHome implements Observer {

    // Image view tương ứng
    private final ImageView imageView;
    // Thời gian cuối update
    private long lastTimeUpdate;
    // Animation ẩn
    private Animation hidden;
    private Animation show;
    private final AppCompatActivity appCompatActivity;
    // Nhận vô game world để lấy camera
    private final GameWorldMyHome gameWorldMyHome;
    // THời gian cuối xuất hiện
    private long timeToEnd;

    private boolean isHidden;

    public ObjectFullScreenMyHome(ImageView imageView, AppCompatActivity appCompatActivity, GameWorldMyHome gameWorldMyHome) {
        this.imageView = imageView;
        this.appCompatActivity = appCompatActivity;
        this.gameWorldMyHome = gameWorldMyHome;
        lastTimeUpdate = 0;
        // Khởi tạo
        init();
    }

    private void init() {
        hidden = AnimationUtils.loadAnimation(appCompatActivity, R.anim.activity_preson_map_image_view_full_screen_hidden);
        show = AnimationUtils.loadAnimation(appCompatActivity, R.anim.activity_preson_map_image_view_full_screen_show);
    }

    @Override
    public void update() {
    }

    public void hidden() {
        appCompatActivity.runOnUiThread(() -> imageView.startAnimation(hidden));
    }

    public void hiddenView() {
        appCompatActivity.runOnUiThread(() -> imageView.setVisibility(View.GONE));
    }

    public void show() {
        appCompatActivity.runOnUiThread(() -> {
            imageView.setVisibility(View.VISIBLE);
            imageView.startAnimation(show);
        });
    }

    @Override
    public void draw() {
    }

    @Override
    public boolean isOutCamera() {
        return false;
    }

    @Override
    public void outToLayout() {
        appCompatActivity.runOnUiThread(() -> ((ViewManager) imageView.getParent()).removeView(imageView));
    }

    // GETTER AND SETTER
    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
}
