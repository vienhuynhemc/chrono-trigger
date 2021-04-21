package com.vientamthuong.chronotrigger.myHome;

import com.vientamthuong.chronotrigger.camera.Camera;
import com.vientamthuong.chronotrigger.presonMap.GameWorldPresonMap;

public class CameraMyHome extends Camera {

    private final GameWorldMyHome gameWorldMyHome;

    public CameraMyHome(int x, int y, GameWorldMyHome gameWorldMyHome) {
        super(x, y);
        this.gameWorldMyHome = gameWorldMyHome;
    }

    @Override
    public void update() {

    }

}
