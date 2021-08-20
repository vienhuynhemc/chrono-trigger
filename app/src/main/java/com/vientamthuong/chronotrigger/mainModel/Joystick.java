package com.vientamthuong.chronotrigger.mainModel;

import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;

import com.vientamthuong.chronotrigger.R;

public class Joystick {

    private int banKinhVongTronLon;
    private int x_vongTronLon;
    private int y_vongTronLon;
    private int x_vongTronNho;
    private int y_vongTronNho;
    private int z;

    // Trạng thái chạm
    private boolean isPressed;

    // Actuator
    private double actuatorX;
    private double actuatorY;

    private ImageView imageViewOuter;
    private ImageView imageViewInner;

    private GameWorld gameWorld;

    public Joystick(int z, GameWorld gameWorld) {
        this.x_vongTronLon = 0;
        this.y_vongTronLon = 0;
        this.x_vongTronNho = 0;
        this.y_vongTronNho = 0;
        this.banKinhVongTronLon = 140;
        this.gameWorld = gameWorld;
        this.z = z;
        // Tạo joystick
        create();
    }

    public void update() {
        // update lại tọa độ vòng tròn nhỏ theo actuator
        updateXYInner();
    }

    private void updateXYInner() {
        int x = (int) (262 + this.getActuatorX() * 140);
        int y = (int) (830 + this.getActuatorY() * 140);
        this.setX_vongTronNho(x-110);
        this.setY_vongTronNho(y-110);
    }

    public void draw() {
        // Luôn vẽ 2 dòng tròn
        // Ở đây là set tọa dộ cho chúng
        // Thằng vòng tòn lớn mặc định ko vẽ vì nó không di chuyển
        this.getGameWorld().getAppCompatActivity().runOnUiThread(() -> {
            imageViewInner.setX(this.getX_vongTronNho());
            imageViewInner.setY(this.getY_vongTronNho());
        });
    }

    private void create() {
        // outer
        imageViewOuter = new ImageView(gameWorld.getAppCompatActivity());
        imageViewOuter.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(280, 280)));
        imageViewOuter.setX(122);
        imageViewOuter.setY(690);
        imageViewOuter.setTranslationZ(z);
        imageViewOuter.setImageResource(R.drawable.circle_joystick);
        gameWorld.getAppCompatActivity().runOnUiThread(() -> {
            gameWorld.getAbsoluteLayout().addView(imageViewOuter);
        });

        // inner
        imageViewInner = new ImageView(gameWorld.getAbsoluteLayout().getContext());
        imageViewInner.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(220, 220)));
        imageViewInner.setX(152);
        imageViewInner.setY(720);
        imageViewInner.setTranslationZ(z);
        imageViewInner.setImageResource(R.drawable.circle_joystick_inner);
        gameWorld.getAppCompatActivity().runOnUiThread(() -> {
            gameWorld.getAbsoluteLayout().addView(imageViewInner);
        });

        // tham số
        this.x_vongTronLon = 122;
        this.y_vongTronLon = 690;
        this.x_vongTronNho = 152;
        this.y_vongTronNho = 720;
    }

    public void resetActuator() {
        this.setActuatorX(0.0);
        this.setActuatorY(0.0);
    }

    public boolean isPressed(double x, double y) {
        // Chạm khi nào ? khi tính khoảng cách từ bán kính vòng tròn lớn
        // tới điểm chạm phải nhỏ hơn bán kính  vòng tròn lớn
        double banKinh = Math.sqrt(
                Math.pow(262 - x, 2) + Math.pow(830 - y, 2)
        );
        return banKinh < this.getBanKinhVongTronLon();
    }

    public void setActuator(double x, double y) {
        double detalX = x-262 ;
        double detalY = y-830 ;
        double detalDistance = Math.sqrt(
                Math.pow(detalX, 2) + Math.pow(detalY, 2)
        );
        if (detalDistance < 140) {
            this.setActuatorX(detalX / 140);
            this.setActuatorY(detalY / 140);
        } else {
            this.setActuatorX(detalX / detalDistance);
            this.setActuatorY(detalY / detalDistance);
        }
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public int getBanKinhVongTronLon() {
        return banKinhVongTronLon;
    }

    public void setBanKinhVongTronLon(int banKinhVongTronLon) {
        this.banKinhVongTronLon = banKinhVongTronLon;
    }

    public int getX_vongTronLon() {
        return x_vongTronLon;
    }

    public void setX_vongTronLon(int x_vongTronLon) {
        this.x_vongTronLon = x_vongTronLon;
    }

    public int getY_vongTronLon() {
        return y_vongTronLon;
    }

    public void setY_vongTronLon(int y_vongTronLonY) {
        this.y_vongTronLon = y_vongTronLonY;
    }

    public int getX_vongTronNho() {
        return x_vongTronNho;
    }

    public void setX_vongTronNho(int x_vongTronNho) {
        this.x_vongTronNho = x_vongTronNho;
    }

    public int getY_vongTronNho() {
        return y_vongTronNho;
    }

    public void setY_vongTronNho(int y_vongTronNho) {
        this.y_vongTronNho = y_vongTronNho;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public ImageView getImageViewOuter() {
        return imageViewOuter;
    }

    public void setImageViewOuter(ImageView imageViewOuter) {
        this.imageViewOuter = imageViewOuter;
    }

    public ImageView getImageViewInner() {
        return imageViewInner;
    }

    public void setImageViewInner(ImageView imageViewInner) {
        this.imageViewInner = imageViewInner;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public double getActuatorX() {
        return actuatorX;
    }

    public void setActuatorX(double actuatorX) {
        this.actuatorX = actuatorX;
    }

    public double getActuatorY() {
        return actuatorY;
    }

    public void setActuatorY(double actuatorY) {
        this.actuatorY = actuatorY;
    }
}
