package com.vientamthuong.chronotrigger.myHome;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import com.vientamthuong.chronotrigger.data.SourceMain;

public class GateToGround {

    private int x;
    private int y;
    private int w;
    private int h;
    private GameWorldMyHome gameWorldMyHome;

    public GateToGround(int x, int y, int w, int h, GameWorldMyHome gameWorldMyHome) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.gameWorldMyHome = gameWorldMyHome;
    }

    public void update() {
        if (gameWorldMyHome.getChrono() != null) {
            if (intersect()) {
                Intent intent = new Intent();
                intent.setClass(gameWorldMyHome.getAppCompatActivity(), MyHomeGroundActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("isStartIntro", SourceMain.getInstance().isStartIntroMyHomeDown());
                bundle.putBoolean("isLoad",false);
                intent.putExtra("data", bundle);
                gameWorldMyHome.getAppCompatActivity().startActivity(intent);
                gameWorldMyHome.getGameThreadMyHome().setRunning(false);
                gameWorldMyHome.getAppCompatActivity().finish();
            }
        }
    }

    public boolean intersect() {
        Rect r1 = new Rect();
        Rect r2 = new Rect();
        r1.set(this.x, this.y, this.w + this.x, this.y + this.h);
        r2.set(gameWorldMyHome.getChrono().getX(), gameWorldMyHome.getChrono().getY(),
                gameWorldMyHome.getChrono().getX() + gameWorldMyHome.getChrono().getWidth(),
                gameWorldMyHome.getChrono().getY() + gameWorldMyHome.getChrono().getHeight());
        return Rect.intersects(r1, r2);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public GameWorldMyHome getGameWorldMyHome() {
        return gameWorldMyHome;
    }

    public void setGameWorldMyHome(GameWorldMyHome gameWorldMyHome) {
        this.gameWorldMyHome = gameWorldMyHome;
    }
}
