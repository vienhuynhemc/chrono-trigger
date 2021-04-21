package com.vientamthuong.chronotrigger.presonMap;

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
import com.vientamthuong.chronotrigger.myHome.MyHomeActivity;

public class ObjectFullScreenPresonMap implements Observer {

    // Image view tương ứng
    private final ImageView imageView;
    // Thời gian cuối update
    private long lastTimeUpdate;
    // Animation ẩn
    private Animation hidden;
    private Animation show;
    private final AppCompatActivity appCompatActivity;
    // Nhận vô game world để lấy camera
    private final GameWorldPresonMap gameWorldPresonMap;
    // THời gian cuối xuất hiện
    private long timeToEnd;

    private boolean isHidden;

    public ObjectFullScreenPresonMap(ImageView imageView, AppCompatActivity appCompatActivity, GameWorldPresonMap gameWorldPresonMap) {
        this.imageView = imageView;
        this.appCompatActivity = appCompatActivity;
        this.gameWorldPresonMap = gameWorldPresonMap;
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
        // Lúc đầu thì chạy animation 1s
        if (lastTimeUpdate == 0) {
            lastTimeUpdate = System.currentTimeMillis();
            appCompatActivity.runOnUiThread(() -> imageView.startAnimation(hidden));
        }

        // Nếu dủ 1 dây thì ẩn
        if (!isHidden && !gameWorldPresonMap.getCamera().isComplete()) {
            long space = System.currentTimeMillis() - lastTimeUpdate;
            if (space > ConfigurationPresonMap.TIME_HIDDEN_SHOW) {
                // Ẩn đi
                // Đưa vào luồng UI chính
                appCompatActivity.runOnUiThread(() -> imageView.setVisibility(View.INVISIBLE));
                isHidden = true;
            }
        } else if (isHidden && gameWorldPresonMap.getCamera().isComplete()) {
            isHidden = false;
            // Hiện lên
            // Đưa vào luồng UI chính
            appCompatActivity.runOnUiThread(() -> {
                imageView.setVisibility(View.VISIBLE);
                imageView.startAnimation(show);
            });
            timeToEnd = System.currentTimeMillis();
        } else if (!isHidden && gameWorldPresonMap.getCamera().isComplete()) {
            // Nếu qua 3 giây từ lúc màn hình đen hiện lên thì qua acitivty khác
            // Dừng luồng này lại , tắt activity này luôn và chuyển tới acitvity my_home
            if (System.currentTimeMillis() - timeToEnd > 3000) {
                Intent intent = new Intent();
                intent.setClass(appCompatActivity, MyHomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("isStartIntro", true);
                intent.putExtra("data", bundle);
                appCompatActivity.startActivity(intent);
                gameWorldPresonMap.getGameThreadPresonMap().setRunning(false);
                appCompatActivity.finish();
            }
        }
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

}
