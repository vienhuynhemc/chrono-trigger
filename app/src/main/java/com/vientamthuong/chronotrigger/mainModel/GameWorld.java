package com.vientamthuong.chronotrigger.mainModel;

import android.widget.AbsoluteLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.myHome.GameWorldMyHome;

public interface GameWorld {

    int getXCamera();

    int getYCamera();

    AbsoluteLayout getAbsoluteLayout();

    AppCompatActivity getAppCompatActivity();

    Joystick getJoystick();

    void setJoystick(Joystick joystick);

}
