package com.vientamthuong.chronotrigger.myHome;

import android.graphics.Rect;

import androidx.constraintlayout.solver.widgets.Rectangle;

public class WindowMyHome {

    private int x;
    private int y;
    private int w;
    private int h;
    private GameWorldMyHome gameWorldMyHome;

    public WindowMyHome(int x, int y, int w, int h, GameWorldMyHome gameWorldMyHome) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.gameWorldMyHome = gameWorldMyHome;
    }

    public boolean isIntercert(double x, double y) {
        if (x < this.x - gameWorldMyHome.getCameraMyHome().getX()) return false;
        if (x > this.x + this.w - gameWorldMyHome.getCameraMyHome().getX()) return false;
        if (y < this.y - gameWorldMyHome.getCameraMyHome().getY()) return false;
        if (y > this.y + this.h - gameWorldMyHome.getCameraMyHome().getY()) return false;
        if (gameWorldMyHome.getChrono() == null) {
            return false;
        }
        Rect r1 = new Rect();
        Rect r2 = new Rect();
        r1.set(this.x, this.y, this.w + this.x, this.y + this.h);
        r2.set(gameWorldMyHome.getChrono().getX(), gameWorldMyHome.getChrono().getY(),
                gameWorldMyHome.getChrono().getX() + gameWorldMyHome.getChrono().getWidth(),
                gameWorldMyHome.getChrono().getY() + gameWorldMyHome.getChrono().getHeight());
        return Rect.intersects(r1,r2);
    }


}
