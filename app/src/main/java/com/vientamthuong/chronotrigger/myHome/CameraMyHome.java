package com.vientamthuong.chronotrigger.myHome;

import com.vientamthuong.chronotrigger.camera.Camera;
import com.vientamthuong.chronotrigger.camera.ConfigurationCamera;

public class CameraMyHome extends Camera {

    private final GameWorldMyHome gameWorldMyHome;

    public CameraMyHome(int x, int y, GameWorldMyHome gameWorldMyHome) {
        super(x, y);
        this.gameWorldMyHome = gameWorldMyHome;
    }

    @Override
    public void update() {
        if (gameWorldMyHome.getChrono() != null) {
            int x = gameWorldMyHome.getChrono().getX();
            int y = gameWorldMyHome.getChrono().getY();
            int w = (ConfigurationCamera.WIDTH - gameWorldMyHome.getChrono().getWidth()) / 2;
            int h = (ConfigurationCamera.HEIGHT - gameWorldMyHome.getChrono().getHeight()) / 2;
            if (x - getX() < w) {
                if (gameWorldMyHome.getBackgroundMapMyHome().getX() + gameWorldMyHome.getBackgroundMapMyHome().getWidth() < 2160) {
                    setX(getX() - 1);
                }
            } else if (getX() + ConfigurationCamera.WIDTH - (x + gameWorldMyHome.getChrono().getWidth()) < w) {
                if (gameWorldMyHome.getBackgroundMapMyHome().getX() > 0) {
                    setX(getX() + 1);
                }
            }
            if (y - getY() < h) {
                if (gameWorldMyHome.getBackgroundMapMyHome().getY() < 0) {
                    setY(getY() - 1);
                }
            } else if (getY() + ConfigurationCamera.HEIGHT - (y + gameWorldMyHome.getChrono().getHeight()) < h) {
                if (gameWorldMyHome.getBackgroundMapMyHome().getY() + gameWorldMyHome.getBackgroundMapMyHome().getHeight() > 1080) {
                    setY(getY() + 1);
                }
            }
        }
    }

}
