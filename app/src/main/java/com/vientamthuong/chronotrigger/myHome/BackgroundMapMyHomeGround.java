package com.vientamthuong.chronotrigger.myHome;

import android.view.ViewManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.gameEffect.Animation;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;

public class BackgroundMapMyHomeGround implements Observer {
    // Khai báo các thuộc tính
    // Image view tương ứng
    private final ImageView imageView;
    private Animation currentAnimation;
    // Appcompat activit để lấy resource
    private final AppCompatActivity appCompatActivity;
    // Nhận vô game word chứa nó để lấy camera
    private final GameWorldMyHomeGround gameWorldMyHomeGround;
    // Tọa độ
    private final int x;
    private final int y;

    public BackgroundMapMyHomeGround(ImageView imageView, AppCompatActivity appCompatActivity, GameWorldMyHomeGround gameWorldMyHomeGround) {
        this.imageView = imageView;
        this.appCompatActivity = appCompatActivity;
        this.gameWorldMyHomeGround = gameWorldMyHomeGround;
        // Mặc định tạo độ 0 0
        x = ConfigurationMyHome.X_BACKGROUNMAP_UP;
        y = 0;
        // Khởi tạo
        init();
    }

    private void init() {
        // Mặc định là tối
        currentAnimation = SourceAnimation.getInstance().getAnimation("my_home_background_ground");
        currentAnimation.setRepeat(false);
    }


    @Override
    public void update() {
        // Cập nhật lại tọa độ của background theo camera
        appCompatActivity.runOnUiThread(() -> {
            imageView.setX(x - gameWorldMyHomeGround.getCameraMyHomeGround().getX());
            imageView.setY(y - gameWorldMyHomeGround.getCameraMyHomeGround().getY());
        });
    }

    @Override
    public void draw() {
        // update and draw Animation
        currentAnimation.update();
        currentAnimation.draw(imageView, appCompatActivity);
    }

    @Override
    public void outToLayout() {
        appCompatActivity.runOnUiThread(() -> ((ViewManager) imageView.getParent()).removeView(imageView));
    }

    @Override
    public boolean isOutCamera() {
        return false;
    }

    public double getX() {
        return imageView.getX();
    }

    public double getY() {
        return imageView.getY();
    }

    public int getWidth() {
        return imageView.getWidth();
    }

    public int getHeight() {
        return imageView.getHeight();
    }

}
