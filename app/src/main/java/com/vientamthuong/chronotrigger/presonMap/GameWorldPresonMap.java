package com.vientamthuong.chronotrigger.presonMap;

public class GameWorldPresonMap {

    // Màn hình đen
    private ObjectFullScreenPresonMap objectFullScreenPresonMap;
    private PresonMapActivity presonMapActivity;

    public GameWorldPresonMap(PresonMapActivity presonMapActivity) {
        this.presonMapActivity = presonMapActivity;
        // Khởi tạo
        init();
    }

    private void init() {
        objectFullScreenPresonMap = new ObjectFullScreenPresonMap(presonMapActivity.getIvFullScreen(), presonMapActivity);
    }

    public void update() {
        objectFullScreenPresonMap.update();
    }

    public void draw() {
        objectFullScreenPresonMap.draw();
    }

}
