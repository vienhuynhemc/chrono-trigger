package com.vientamthuong.chronotrigger.myHome;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import com.vientamthuong.chronotrigger.data.SourceMain;

public class GateToUp extends  Gate{

    private GameWorldMyHomeGround gameWorldMyHomeGround;

    public GateToUp(int x, int y, int w, int h, GameWorldMyHomeGround gameWorldMyHomeGround) {
        super(x, y, w, h);
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
        r1.set(this.getX(), this.getY(), this.getW() + this.getX(), this.getY() + this.getH());
        r2.set(gameWorldMyHomeGround.getChrono().getX(), gameWorldMyHomeGround.getChrono().getY(),
                gameWorldMyHomeGround.getChrono().getX() + gameWorldMyHomeGround.getChrono().getWidth(),
                gameWorldMyHomeGround.getChrono().getY() + gameWorldMyHomeGround.getChrono().getHeight());
        return Rect.intersects(r1, r2);
    }
}
