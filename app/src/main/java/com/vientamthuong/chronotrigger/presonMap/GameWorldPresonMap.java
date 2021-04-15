package com.vientamthuong.chronotrigger.presonMap;

import com.vientamthuong.chronotrigger.interfaceGameThread.UpdateAndDraw;

import java.util.ArrayList;
import java.util.List;

public class GameWorldPresonMap {

    // List các object
    private final List<UpdateAndDraw> listObject;
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
    }

    public void update() {
        // camera
        camera.update();
        // list Object
        for (UpdateAndDraw updateAndDraw : listObject) {
            updateAndDraw.update();
        }
    }

    public void draw() {
        for (UpdateAndDraw updateAndDraw : listObject) {
            updateAndDraw.draw();
        }
    }

    public CameraPresonMap getCamera() {
        return camera;
    }

}
