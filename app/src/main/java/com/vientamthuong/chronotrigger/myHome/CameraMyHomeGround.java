package com.vientamthuong.chronotrigger.myHome;

import com.vientamthuong.chronotrigger.camera.Camera;
import com.vientamthuong.chronotrigger.camera.ConfigurationCamera;

public class CameraMyHomeGround extends Camera {
    private GameWorldMyHomeGround gameWorldMyHomeGround;

    public CameraMyHomeGround(int x, int y, GameWorldMyHomeGround gameWorldMyHomeGround) {
        super(x,y);
        this.gameWorldMyHomeGround = gameWorldMyHomeGround;
    }

    @Override
    public void update() {
        if (gameWorldMyHomeGround.getChrono() != null) {
            int x = gameWorldMyHomeGround.getChrono().getX();
            int y = gameWorldMyHomeGround.getChrono().getY();
            int w = (ConfigurationCamera.WIDTH - gameWorldMyHomeGround.getChrono().getWidth()) / 2;
            int h = (ConfigurationCamera.HEIGHT - gameWorldMyHomeGround.getChrono().getHeight()) / 2;
            if (x - getX() < w) {
                if (gameWorldMyHomeGround.getBackgroundMapMyHomeGround().getX() + gameWorldMyHomeGround.getBackgroundMapMyHomeGround().getWidth() < 2160) {
                    setX(getX() - 1);
                }
            } else if (getX() + ConfigurationCamera.WIDTH - (x + gameWorldMyHomeGround.getChrono().getWidth()) < w) {
                if (gameWorldMyHomeGround.getBackgroundMapMyHomeGround().getX() > 0) {
                    setX(getX() + 1);
                }
            }
            if (y - getY() < h) {
                if (gameWorldMyHomeGround.getBackgroundMapMyHomeGround().getY() < 0) {
                    setY(getY() - 1);
                }
            } else if (getY() + ConfigurationCamera.HEIGHT - (y + gameWorldMyHomeGround.getChrono().getHeight()) < h) {
                if (gameWorldMyHomeGround.getBackgroundMapMyHomeGround().getY() + gameWorldMyHomeGround.getBackgroundMapMyHomeGround().getHeight() > 1080) {
                    setY(getY() + 1);
                }
            }
        }
    }
}
