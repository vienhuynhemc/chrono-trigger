package com.vientamthuong.chronotrigger.myHome;

import com.vientamthuong.chronotrigger.camera.Camera;

public class CameraMyHomeGround extends Camera {
    private GameWorldMyHomeGround gameWorldMyHomeGround;

    public CameraMyHomeGround(int x, int y, GameWorldMyHomeGround gameWorldMyHomeGround) {
        super(x,y);
        this.gameWorldMyHomeGround = gameWorldMyHomeGround;
    }
}
