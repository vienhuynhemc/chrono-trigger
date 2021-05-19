package com.vientamthuong.chronotrigger.mainModel;

import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;

import com.vientamthuong.chronotrigger.R;

public class Joystick {

    private int banKinhVongTronLon;
    private int x_vongTronLon;
    private int y_vongTronLonY;
    private int x_vongTronNho;
    private int y_vongTronNho;
    private int z;

    private ImageView imageViewOuter;
    private ImageView imageViewInner;

    private GameWorld gameWorld;

    public Joystick(int z, GameWorld gameWorld) {
        this.x_vongTronLon = 0;
        this.y_vongTronLonY = 0;
        this.x_vongTronNho = 0;
        this.y_vongTronNho = 0;
        this.banKinhVongTronLon = 140;
        this.gameWorld = gameWorld;
        this.z = z;
        // Tạo joystick
        create();
    }

    public void update(){

    }

    public void draw(){

    }

    private void create() {
        imageViewOuter = new ImageView(gameWorld.getAppCompatActivity());
        imageViewOuter.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(280, 280)));
        imageViewOuter.setX(122);
        imageViewOuter.setY(690);
        imageViewOuter.setTranslationZ(z);
        imageViewOuter.setImageResource(R.drawable.circle_joystick);
        gameWorld.getAppCompatActivity().runOnUiThread(() -> {
            gameWorld.getAbsoluteLayout().addView(imageViewOuter);
        });

        imageViewInner = new ImageView(gameWorld.getAbsoluteLayout().getContext());
        imageViewInner.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(220, 220)));
        imageViewInner.setX(152);
        imageViewInner.setY(720);
        imageViewInner.setTranslationZ(z);
        imageViewInner.setImageResource(R.drawable.circle_joystick_inner);
        gameWorld.getAppCompatActivity().runOnUiThread(() -> {
            gameWorld.getAbsoluteLayout().addView(imageViewInner);
        });
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

    public int getY_vongTronLonY() {
        return y_vongTronLonY;
    }

    public void setY_vongTronLonY(int y_vongTronLonY) {
        this.y_vongTronLonY = y_vongTronLonY;
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
}
