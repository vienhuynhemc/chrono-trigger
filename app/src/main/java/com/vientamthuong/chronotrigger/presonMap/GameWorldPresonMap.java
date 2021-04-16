package com.vientamthuong.chronotrigger.presonMap;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.vientamthuong.chronotrigger.camera.ConfigurationCamera;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;
import com.vientamthuong.chronotrigger.random.RandomSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameWorldPresonMap {

    // List các object
    private final List<Observer> listObject;
    // Camera
    private CameraPresonMap camera;
    private final PresonMapActivity presonMapActivity;
    // Thời gian cuối cập nhật để tạo chim
    private boolean isOverCreateBird;
    private long lastTimeCreateBird;

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
        ObjectFullScreenPresonMap objectFullScreenPresonMap = new ObjectFullScreenPresonMap(presonMapActivity.getIvFullScreen(), presonMapActivity, GameWorldPresonMap.this);
        listObject.add(objectFullScreenPresonMap);
        // background
        BackgroundMapPresonMap backgroundMapPresonMap = new BackgroundMapPresonMap(presonMapActivity.getIvBackgroundMap(), presonMapActivity, GameWorldPresonMap.this);
        listObject.add(backgroundMapPresonMap);
        // Thuyền
        createBoat();
    }

    private void createBoat() {
        ImageView imageViewBoat = new ImageView(presonMapActivity);
        imageViewBoat.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationPresonMap.WIDTH_BOAT, ConfigurationPresonMap.HEIGHT_BOAT));
        presonMapActivity.runOnUiThread(() -> presonMapActivity.getAbsoluteLayout().addView(imageViewBoat, presonMapActivity.getAbsoluteLayout().getChildCount() - 1));
        BoatPresonMap boatPresonMap = new BoatPresonMap(imageViewBoat, ConfigurationPresonMap.START_BOAT_X, ConfigurationPresonMap.START_BOAT_Y, ConfigurationPresonMap.WIDTH_BOAT, ConfigurationPresonMap.HEIGHT_BOAT, presonMapActivity, GameWorldPresonMap.this);
        listObject.add(boatPresonMap);
    }

    public void update() {
        // camera
        camera.update();
        // Tạo chim
        if (!isOverCreateBird) {
            if (lastTimeCreateBird == 0) {
                lastTimeCreateBird = System.currentTimeMillis();
            }
            // lên tới đây không tạo chim nữa
            if (camera.getY() < 1308) {
                isOverCreateBird = true;
            }
            // 0.15s Tạo 1 chim
            if (System.currentTimeMillis() - lastTimeCreateBird > 150) {
                lastTimeCreateBird = System.currentTimeMillis();
                ImageView imageViewBird = new ImageView(presonMapActivity);
                imageViewBird.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationPresonMap.WIDTH_BIRD, ConfigurationPresonMap.HEIGHT_BIRD));
                presonMapActivity.runOnUiThread(() -> presonMapActivity.getAbsoluteLayout().addView(imageViewBird, presonMapActivity.getAbsoluteLayout().getChildCount() - 1));
                // Lấy random
                Random random = RandomSingleton.getInstance().getRandom();
                int startY = getCamera().getY() + ConfigurationCamera.HEIGHT + 100;
                int startX = random.nextInt(1580) + 300 + getCamera().getX();
                int speedY = (random.nextInt(3) + 1) * -1;
                int speedX = random.nextInt(7) - 3;
                System.out.println(startX + " " + startY + " " + speedX + " " + speedY);
                BirdPresonMap birdPresonMap = new BirdPresonMap(imageViewBird, startX, startY, speedX, speedY, ConfigurationPresonMap.WIDTH_BIRD, ConfigurationPresonMap.HEIGHT_BIRD, presonMapActivity, GameWorldPresonMap.this);
                listObject.add(birdPresonMap);
            }
        }
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
