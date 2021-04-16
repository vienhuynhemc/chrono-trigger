package com.vientamthuong.chronotrigger.presonMap;

import android.view.ViewManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.gameEffect.Animation;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;

public class BubblePresonMap implements Observer {

    private final ImageView imageView;
    // Tọa độ thông thường
    private int x;
    private int y;
    // Tạo đô để vẽ
    private int xDraw;
    private int yDraw;
    // Vận tốc
    private final int speedX;
    private final int speedY;
    private final int width;
    private final int height;
    private Animation animation;
    private final AppCompatActivity appCompatActivity;
    private final GameWorldPresonMap gameWorldPresonMap;

    public BubblePresonMap(ImageView imageView, int x, int y, int speedX, int speedY, int width, int height, AppCompatActivity appCompatActivity, GameWorldPresonMap gameWorldPresonMap, int type) {
        this.imageView = imageView;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speedX = speedX;
        this.speedY = speedY;
        this.gameWorldPresonMap = gameWorldPresonMap;
        this.appCompatActivity = appCompatActivity;
        // Nhận vô type xem thử bóng có màu gì
        init(type);
    }

    private void init(int type) {
        switch (type) {
            case 0:
                animation = SourceAnimation.getInstance().getAnimation("preson_map_bubble_red");
                break;
            case 1:
                animation = SourceAnimation.getInstance().getAnimation("preson_map_bubble_blue");
                break;
            case 2:
                animation = SourceAnimation.getInstance().getAnimation("preson_map_bubble_yellow");
                break;
        }
        // Set lại tọa độ theo camera
        xDraw = x - gameWorldPresonMap.getCamera().getX();
        yDraw = y - gameWorldPresonMap.getCamera().getY();
        // Cập nhật lại tọa độ của background theo camera
        imageView.setX(xDraw);
        imageView.setY(yDraw);
    }

    @Override
    public void update() {
        // Cập nhật lại tọa độ theo vận tốc
        x = x + speedX;
        y = y + speedY;
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
    public boolean isOutCamera() {
        return gameWorldPresonMap.getCamera().isOutCamera(x, y, width, height);
    }

    @Override
    public void outToLayout() {
        appCompatActivity.runOnUiThread(() -> ((ViewManager) imageView.getParent()).removeView(imageView));
    }
}
