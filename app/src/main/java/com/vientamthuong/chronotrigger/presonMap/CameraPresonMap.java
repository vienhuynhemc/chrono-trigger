package com.vientamthuong.chronotrigger.presonMap;

import com.vientamthuong.chronotrigger.camera.Camera;

import java.util.ArrayList;
import java.util.List;

public class CameraPresonMap extends Camera {

    // Sở hữu 1 list deley camera
    private List<ObjectMovingCamera> movings;
    // Thời gian cuối cùng chờ đợi
    private long lastTimeWait;
    // boolean xem thử có bắt đầu đợi hay chưa
    private boolean isWait;
    // boolean xác nhận camera đã oke hết
    private boolean isComplete;
    private final GameWorldPresonMap gameWorldPresonMap;

    public CameraPresonMap(int x, int y, GameWorldPresonMap gameWorldPresonMap) {
        super(x, y);
        this.gameWorldPresonMap = gameWorldPresonMap;
        init();
    }

    private void init() {
        movings = new ArrayList<>();
        movings.add(new ObjectMovingCamera(ConfigurationPresonMap.CAMRERA_START_X, ConfigurationPresonMap.CAMRERA_START_Y, ConfigurationPresonMap.CAMERA_START_DURATION));
        movings.add(new ObjectMovingCamera(ConfigurationPresonMap.CAMRERA_X_2, ConfigurationPresonMap.CAMRERA_Y_2, ConfigurationPresonMap.CAMERA_2));
        movings.add(new ObjectMovingCamera(ConfigurationPresonMap.CAMRERA_X_3, ConfigurationPresonMap.CAMRERA_Y_3, ConfigurationPresonMap.CAMERA_3));
        movings.add(new ObjectMovingCamera(ConfigurationPresonMap.CAMRERA_X_4, ConfigurationPresonMap.CAMRERA_Y_4, ConfigurationPresonMap.CAMERA_4));
        movings.add(new ObjectMovingCamera(ConfigurationPresonMap.CAMRERA_X_5, ConfigurationPresonMap.CAMRERA_Y_5, ConfigurationPresonMap.CAMERA_5));
    }

    @Override
    public void update() {
        if (!isComplete) {
            // movings còn thở thì còn gỡ
            if (movings.size() != 0) {
                // Lấy first
                ObjectMovingCamera objectMovingCamera = movings.get(0);
                if (getX() == objectMovingCamera.getX() && getY() == objectMovingCamera.getY()) {
                    if (isWait) {
                        long space = System.currentTimeMillis() - lastTimeWait;
                        if (space > objectMovingCamera.getDuration()) {
                            isWait = false;
                            movings.remove(0);
                            // Khi chạy đc 2 khung hình camera thì game world bắt đầu tạo bong bóng
                            if (movings.size() == 3) {
                                gameWorldPresonMap.setStartCreateBubble(true);
                                gameWorldPresonMap.setLastTimeCreateBubble(System.currentTimeMillis());
                            }
                            // Lúc này camera đi xuống nên cho mấy đi ngang
                            if (movings.size() == 2) {
                                gameWorldPresonMap.setLeftClound(true);
                            }
                        }
                    } else {
                        isWait = true;
                        lastTimeWait = System.currentTimeMillis();
                    }
                } else {
                    int needX = objectMovingCamera.getXCameraNeedAdd(getX());
                    int needY = objectMovingCamera.getYCameraNeedAdd(getY());
                    setX(getX() + needX);
                    setY(getY() + needY);
                }
            } else {
                isComplete = true;
            }
        }
    }

    public boolean isComplete() {
        return isComplete;
    }

}
