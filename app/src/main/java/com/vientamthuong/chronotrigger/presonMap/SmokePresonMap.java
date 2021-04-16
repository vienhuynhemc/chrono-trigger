package com.vientamthuong.chronotrigger.presonMap;

import android.view.ViewManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.data.SourceSound;
import com.vientamthuong.chronotrigger.gameEffect.Animation;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;
import com.vientamthuong.chronotrigger.loadData.ConfigurationSound;

public class SmokePresonMap implements Observer {

    private final ImageView imageView;
    // Tọa độ thông thường
    private final int x;
    private final int y;
    // Tạo đô để vẽ
    private int xDraw;
    private int yDraw;
    private Animation animation;
    private final AppCompatActivity appCompatActivity;
    private final GameWorldPresonMap gameWorldPresonMap;

    public SmokePresonMap(ImageView imageView, int x, int y, AppCompatActivity appCompatActivity, GameWorldPresonMap gameWorldPresonMap) {
        this.imageView = imageView;
        this.x = x;
        this.y = y;
        this.gameWorldPresonMap = gameWorldPresonMap;
        this.appCompatActivity = appCompatActivity;
        init();
    }

    private void init() {
        animation = SourceAnimation.getInstance().getAnimation("preson_map_smoke");
        animation.setRepeat(false);
        // Set lại tọa độ theo camera
        xDraw = x - gameWorldPresonMap.getCamera().getX();
        yDraw = y - gameWorldPresonMap.getCamera().getY();
        // Cập nhật lại tọa độ của background theo camera
        imageView.setX(xDraw);
        imageView.setY(yDraw);
        // Chạy âm thanh
        SourceSound.getInstance().play("attack", ConfigurationSound.NOREPEAT);
    }

    @Override
    public void update() {
        // Set lại tọa độ theo camera
        xDraw = x - gameWorldPresonMap.getCamera().getX();
        yDraw = y - gameWorldPresonMap.getCamera().getY();
        // Cập nhật lại tọa độ của background theo camera
        appCompatActivity.runOnUiThread(() -> {
            imageView.setX(xDraw);
            imageView.setY(yDraw);
        });
    }

    @Override
    public void draw() {
        // update and draw animation
        animation.update();
        animation.draw(imageView, appCompatActivity);
    }

    @Override
    public void outToLayout() {
        appCompatActivity.runOnUiThread(() -> ((ViewManager) imageView.getParent()).removeView(imageView));
    }

    @Override
    public boolean isOutCamera() {
        return getAnimation().isLastBitmap();
    }

    public Animation getAnimation() {
        return animation;
    }
}
