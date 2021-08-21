package com.vientamthuong.chronotrigger.myHome;

import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.data.SourceMain;
import com.vientamthuong.chronotrigger.gameEffect.Animation;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;

public class MotherCronoGround implements Observer {
    // Trạng thái
    private int state;
    public static final int DI = 0;
    public static final int DUNG_IM = 1;
    public static final int GIO_TAY = 2;
    // Hướng
    private int dir;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;
    public static final int BOTTOM = 3;
    private final ImageView imageView;
    // Tọa độ thông thường
    private int x;
    private int y;
    private final int z;
    // Tạo đô để vẽ
    private int xDraw;
    private int yDraw;
    private Animation hanhDongDungImTrai, hanhDongDungImPhai, hanhDongDungImLen, hanhDongDiXuong;
    private Animation hanhDongDiTrai, hanhDongDiPhai, hanhDongDiLen;
    private Animation hanhDongGioTayLen, hanhDongGioTayXuong;
    private final AppCompatActivity appCompatActivity;
    private final GameWorldMyHomeGround gameWorldMyHomeGround;
    // Thời gian update
    private long lastTimeUpdate;
    // Boolean các khung thời gian
    private boolean isStartMove1;
    private boolean isStartMove2;
    // end
    private boolean end;

    public MotherCronoGround(ImageView imageView, int x, int y, int z, AppCompatActivity appCompatActivity, GameWorldMyHomeGround gameWorldMyHomeGround) {
        this.imageView = imageView;
        this.x = x;
        this.y = y;
        this.z = z;
        this.gameWorldMyHomeGround = gameWorldMyHomeGround;
        this.appCompatActivity = appCompatActivity;
        init();
    }

    private void init() {
        // Khởi tạo ban đầu là đứng im và hướng là trên
        dir = TOP;
        state = DUNG_IM;
        hanhDongGioTayLen = SourceAnimation.getInstance().getAnimation("morther_crono_gio_tay_len");
        hanhDongGioTayXuong = SourceAnimation.getInstance().getAnimation("morther_crono_gio_tay_xuong");
        hanhDongDungImLen = SourceAnimation.getInstance().getAnimation("morther_crono_dung_im_len");
        hanhDongDungImTrai = SourceAnimation.getInstance().getAnimation("morther_crono_dung_im_trai");
        hanhDongDungImPhai = SourceAnimation.getInstance().getAnimation("morther_crono_dung_im_trai");
        hanhDongDungImPhai.flip();
        hanhDongDiLen = SourceAnimation.getInstance().getAnimation("morther_crono_di_len");
        hanhDongDiXuong = SourceAnimation.getInstance().getAnimation("morther_crono_di_xuong");
        hanhDongDiTrai = SourceAnimation.getInstance().getAnimation("morther_crono_di_trai");
        hanhDongDiPhai = SourceAnimation.getInstance().getAnimation("morther_crono_di_trai");
        hanhDongDiPhai.flip();
        // Set lại tọa độ theo camera
        xDraw = x - gameWorldMyHomeGround.getCameraMyHomeGround().getX();
        yDraw = y - gameWorldMyHomeGround.getCameraMyHomeGround().getY();
        // Cập nhật lại tọa độ của background theo camera
        imageView.setX(xDraw);
        imageView.setY(yDraw);
        // Cập nhật lại tạo độ của z
        imageView.setTranslationZ(z);
    }


    @Override
    public void update() {
        // Set lại tọa độ theo camera
        xDraw = x - gameWorldMyHomeGround.getCameraMyHomeGround().getX();
        yDraw = y - gameWorldMyHomeGround.getCameraMyHomeGround().getY();
        // Cập nhật lại tọa độ của background theo camera
        appCompatActivity.runOnUiThread(() -> {
            imageView.setX(xDraw);
            imageView.setY(yDraw);
        });
        // Update ban đầu
        if (lastTimeUpdate == 0) {
            lastTimeUpdate = System.currentTimeMillis();
        }
        if (isStartMove1) {
            if (y < 900) {
                y++;
                state = DI;
                dir = BOTTOM;
            } else if (x > 900) {
                x--;
                state = DI;
                dir = LEFT;
            } else {
                isStartMove1 = false;
                isStartMove2 = true;
            }
        }
        if (isStartMove2) {
            if (y > 400) {
                y--;
                state = DI;
                dir = TOP;
            } else {
                state = DUNG_IM;
                isStartMove2 = false;
                if (!end) {
                    end = true;
                    // end
                    gameWorldMyHomeGround.setState(GameWorldMyHomeGround.CREATE_CHRONO_PLAY);
                    SourceMain.getInstance().setStartIntroMyHomeDown(false);
                    gameWorldMyHomeGround.getCronoMyHomeGround().setOut(true);
                }
            }
        }
    }

    @Override
    public void draw() {
        switch (state) {
            case DUNG_IM:
                if (dir == TOP) {
                    hanhDongDungImLen.update();
                    hanhDongDungImLen.draw(imageView, appCompatActivity);
                }
                break;
            case DI:
                if (dir == BOTTOM) {
                    hanhDongDiXuong.update();
                    hanhDongDiXuong.draw(imageView, appCompatActivity);
                }
                if (dir == LEFT) {
                    hanhDongDiTrai.update();
                    hanhDongDiTrai.draw(imageView, appCompatActivity);
                }
                if (dir == TOP) {
                    hanhDongDiLen.update();
                    hanhDongDiLen.draw(imageView, appCompatActivity);
                }
                break;

        }
    }

    @Override
    public boolean isOutCamera() {
        return false;
    }

    @Override
    public void outToLayout() {
    }
    //getter setter

    public boolean isStartMove1() {
        return isStartMove1;
    }

    public void setStartMove1(boolean startMove1) {
        isStartMove1 = startMove1;
    }
}
