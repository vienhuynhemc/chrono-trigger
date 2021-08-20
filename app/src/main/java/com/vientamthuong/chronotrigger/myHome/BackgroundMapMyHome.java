package com.vientamthuong.chronotrigger.myHome;

import android.view.ViewManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.data.SourceMain;
import com.vientamthuong.chronotrigger.gameEffect.Animation;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;

public class BackgroundMapMyHome implements Observer {

    // Khai báo các thuộc tính
    // Image view tương ứng
    private final ImageView imageView;
    private Animation animationLight;
    private Animation animationDark;
    private Animation currentAnimation;
    // Appcompat activit để lấy resource
    private final AppCompatActivity appCompatActivity;
    // Nhận vô game word chứa nó để lấy camera
    private final GameWorldMyHome gameWorldMyHome;
    // Tọa độ
    private final int x;
    private final int y;

    public BackgroundMapMyHome(ImageView imageView, AppCompatActivity appCompatActivity, GameWorldMyHome gameWorldMyHome) {
        this.imageView = imageView;
        this.appCompatActivity = appCompatActivity;
        this.gameWorldMyHome = gameWorldMyHome;
        // Mặc định tạo độ 0 0
        x = ConfigurationMyHome.X_BACKGROUNMAP_UP;
        y = 0;
        // Khởi tạo
        init();
    }

    private void init() {
        // Animation của backgroundmap
        animationDark = SourceAnimation.getInstance().getAnimation("my_home_background_dark");
        animationLight = SourceAnimation.getInstance().getAnimation("my_home_background_light");
        // animation light không cho lập lại
        animationLight.setRepeat(false);
        // Mặc định là tối
        currentAnimation = SourceMain.getInstance().isOpenWindown()?animationLight:animationDark;
    }

    public void changeToLight() {
        currentAnimation = animationLight;
    }

    public void openCloseWindow() {
        if (SourceMain.getInstance().isOpenWindown()) {
            currentAnimation = animationLight;
        } else {
            currentAnimation = animationDark;
        }
    }

    @Override
    public void update() {
        // Cập nhật lại tọa độ của background theo camera
        appCompatActivity.runOnUiThread(() -> {
            imageView.setX(x - gameWorldMyHome.getCameraMyHome().getX());
            imageView.setY(y - gameWorldMyHome.getCameraMyHome().getY());
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
