package com.vientamthuong.chronotrigger.myHome;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import com.vientamthuong.chronotrigger.data.SourceMain;

public class GateToUp{

    private int x;
    private int y;
    private int w;
    private int h;
    private GameWorldMyHomeGround gameWorldMyHomeGround;

    public GateToUp(int x, int y, int w, int h, GameWorldMyHomeGround gameWorldMyHomeGround) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.gameWorldMyHomeGround = gameWorldMyHomeGround;
    }

    public void update() {
        if (gameWorldMyHomeGround.getChrono() != null) {
            if (intersect()) {
                Intent intent = new Intent();
                intent.setClass(gameWorldMyHomeGround.getAppCompatActivity(), MyHomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("isStartIntro", SourceMain.getInstance().isStartIntroMyHomeUp());
                bundle.putBoolean("isLoad",false);
                intent.putExtra("data", bundle);
                gameWorldMyHomeGround.getAppCompatActivity().startActivity(intent);
                gameWorldMyHomeGround.getGameThreadMyHomeGround().setRunning(false);
                gameWorldMyHomeGround.getAppCompatActivity().finish();
            }
        }
    }

    public boolean intersect() {
        Rect r1 = new Rect();
        Rect r2 = new Rect();
        r1.set(this.x, this.y, this.w + this.x, this.y + this.h);
        r2.set(gameWorldMyHomeGround.getChrono().getX(), gameWorldMyHomeGround.getChrono().getY(),
                gameWorldMyHomeGround.getChrono().getX() + gameWorldMyHomeGround.getChrono().getWidth(),
                gameWorldMyHomeGround.getChrono().getY() + gameWorldMyHomeGround.getChrono().getHeight());
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

}
