package com.vientamthuong.chronotrigger.mainModel;

import android.media.Image;
import android.widget.ImageView;

public class Joystick {

    private int duongKinhVongTronLon;
    private int duongKinhVongtronNho;
    private int x_vongTronLon;
    private int y_vongTronLonY;
    private int x_vongTronNho;
    private int y_vongTronNho;
    private ImageView imageViewVongTronLon;
    private ImageView imageViewVongTronNho;

    public Joystick(int duongKinhVongTronLon, int duongKinhVongtronNho, int x_vongTronLon, int y_vongTronLonY, int x_vongTronNho, int y_vongTronNho, ImageView imageViewVongTronLon, ImageView imageViewVongTronNho) {
        this.duongKinhVongTronLon = duongKinhVongTronLon;
        this.duongKinhVongtronNho = duongKinhVongtronNho;
        this.x_vongTronLon = x_vongTronLon;
        this.y_vongTronLonY = y_vongTronLonY;
        this.x_vongTronNho = x_vongTronNho;
        this.y_vongTronNho = y_vongTronNho;
        this.imageViewVongTronLon = imageViewVongTronLon;
        this.imageViewVongTronNho = imageViewVongTronNho;
    }


    public int getDuongKinhVongTronLon() {
        return duongKinhVongTronLon;
    }

    public void setDuongKinhVongTronLon(int duongKinhVongTronLon) {
        this.duongKinhVongTronLon = duongKinhVongTronLon;
    }

    public int getDuongKinhVongtronNho() {
        return duongKinhVongtronNho;
    }

    public void setDuongKinhVongtronNho(int duongKinhVongtronNho) {
        this.duongKinhVongtronNho = duongKinhVongtronNho;
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

    public ImageView getImageViewVongTronLon() {
        return imageViewVongTronLon;
    }

    public void setImageViewVongTronLon(ImageView imageViewVongTronLon) {
        this.imageViewVongTronLon = imageViewVongTronLon;
    }

    public ImageView getImageViewVongTronNho() {
        return imageViewVongTronNho;
    }

    public void setImageViewVongTronNho(ImageView imageViewVongTronNho) {
        this.imageViewVongTronNho = imageViewVongTronNho;
    }
}
