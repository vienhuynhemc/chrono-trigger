package com.vientamthuong.chronotrigger.presonMap;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;

import java.util.ArrayList;
import java.util.List;

public class GameWorldPresonMap {

    // List các object
    private final List<Observer> listObject;
    // Camera
    private CameraPresonMap camera;
    private final PresonMapActivity presonMapActivity;

    public GameWorldPresonMap(PresonMapActivity presonMapActivity) {
        this.presonMapActivity = presonMapActivity;
        listObject = new ArrayList<>();
        // Khởi tạo
        init();
    }

    private void init() {
        // Camera
        camera = new CameraPresonMap(ConfigurationPresonMap.CAMRERA_START_X, ConfigurationPresonMap.CAMRERA_START_Y);
        // Màn hình đen
        // Màn hình đen
        ObjectFullScreenPresonMap objectFullScreenPresonMap = new ObjectFullScreenPresonMap(presonMapActivity.getIvFullScreen(), presonMapActivity, GameWorldPresonMap.this);
        listObject.add(objectFullScreenPresonMap);
        // background
        BackgroundMapPresonMap backgroundMapPresonMap = new BackgroundMapPresonMap(presonMapActivity.getIvBackgroundMap(), presonMapActivity, GameWorldPresonMap.this);
        listObject.add(backgroundMapPresonMap);
        // Thuyền
        ImageView imageViewBoat = new ImageView(presonMapActivity);
        imageViewBoat.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationPresonMap.WIDTH_BOAT, ConfigurationPresonMap.HEIGHT_BOAT));
        presonMapActivity.getAbsoluteLayout().addView(imageViewBoat, presonMapActivity.getAbsoluteLayout().getChildCount() - 1);
        BoatPresonMap boatPresonMap = new BoatPresonMap(imageViewBoat, ConfigurationPresonMap.START_BOAT_X, ConfigurationPresonMap.START_BOAT_Y, ConfigurationPresonMap.WIDTH_BOAT, ConfigurationPresonMap.HEIGHT_BOAT, presonMapActivity, GameWorldPresonMap.this);
        listObject.add(boatPresonMap);
    }

    public void update() {
        // camera
        camera.update();
        // list Object
        for (Observer observer : listObject) {
            observer.update();
        }
    }

    public void draw() {
        for (Observer observer : listObject) {
            if (!observer.isOutCamera()) {
                observer.draw();
            }
        }
    }

    public CameraPresonMap getCamera() {
        return camera;
    }

}
