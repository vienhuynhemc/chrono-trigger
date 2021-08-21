package com.vientamthuong.chronotrigger.myHome;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import com.vientamthuong.chronotrigger.data.SourceMain;

public class GateToGround extends Gate {

    private GameWorldMyHome gameWorldMyHome;

    public GateToGround(int x, int y, int w, int h, GameWorldMyHome gameWorldMyHome) {
        super(x, y, w, h);
        this.gameWorldMyHome = gameWorldMyHome;
    }

    public void update() {
        if (gameWorldMyHome.getChrono() != null) {
            if (intersect()) {
                Intent intent = new Intent();
                intent.setClass(gameWorldMyHome.getAppCompatActivity(), MyHomeGroundActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("isStartIntro", SourceMain.getInstance().isStartIntroMyHomeDown());
                bundle.putBoolean("isLoad", false);
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
        r1.set(this.getX(), this.getY(), this.getW() + this.getX(), this.getY() + this.getH());
        r2.set(gameWorldMyHome.getChrono().getX(), gameWorldMyHome.getChrono().getY(),
                gameWorldMyHome.getChrono().getX() + gameWorldMyHome.getChrono().getWidth(),
                gameWorldMyHome.getChrono().getY() + gameWorldMyHome.getChrono().getHeight());
        return Rect.intersects(r1, r2);
    }


    public GameWorldMyHome getGameWorldMyHome() {
        return gameWorldMyHome;
    }

    public void setGameWorldMyHome(GameWorldMyHome gameWorldMyHome) {
        this.gameWorldMyHome = gameWorldMyHome;
    }
}
