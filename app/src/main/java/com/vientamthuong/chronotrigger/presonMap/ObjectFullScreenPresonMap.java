package com.vientamthuong.chronotrigger.presonMap;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.R;
import com.vientamthuong.chronotrigger.interfaceGameThread.UpdateAndDraw;

public class ObjectFullScreenPresonMap implements UpdateAndDraw {

    private ImageView imageView;
    private long lastTimeUpdate;
    private Animation hidden;
    private AppCompatActivity appCompatActivity;

    private boolean isHidden;

    public ObjectFullScreenPresonMap(ImageView imageView, AppCompatActivity appCompatActivity) {
        this.imageView = imageView;
        this.appCompatActivity = appCompatActivity;
        lastTimeUpdate = 0;
        // Khởi tạo
        init();
    }

    private void init() {
        hidden = AnimationUtils.loadAnimation(appCompatActivity, R.anim.activity_preson_map_image_view_full_screen_hidden);
    }

    @Override
    public void update() {
        // Lúc đầu thì chạy animation 1s
        if (lastTimeUpdate == 0) {
            lastTimeUpdate = System.currentTimeMillis();
            imageView.startAnimation(hidden);
        }

        // Nếu dủ 1 dây thì ẩn
        if (!isHidden) {
            long space = System.currentTimeMillis() - lastTimeUpdate;
            if (space > 1000) {
                // Ẩn đi
                imageView.setVisibility(View.INVISIBLE);
                isHidden = true;
            }
        }
    }

    @Override
    public void draw() {
    }

}
