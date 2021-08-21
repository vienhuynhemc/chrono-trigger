package com.vientamthuong.chronotrigger.myHome;

import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.data.SourceMain;
import com.vientamthuong.chronotrigger.gameEffect.Animation;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;
import com.vientamthuong.chronotrigger.mainCharacter.Chrono;
import com.vientamthuong.chronotrigger.mainModel.GameWorld;

public class ChronoUpFloor implements Observer {

    // Trạng thái
    private int state;
    public static final int DI = 0;
    public static final int DUNG_IM = 1;
    public static final int LAC_DAU = 2;
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
    private Animation hanhDongDungImLen;
    private Animation hanhDongLacDauTren;
    private Animation hanhDongDiTrai;
    private Animation hanhDongDungImXuong;
    private final AppCompatActivity appCompatActivity;
    private final GameWorldMyHome gameWorldMyHome;
    // Thời gian update
    private long lastTimeUpdate;
    // Count đếm trạng thái
    private int count;

    public ChronoUpFloor(ImageView imageView, int x, int y, int z, AppCompatActivity appCompatActivity, GameWorldMyHome gameWorldMyHome) {
        this.imageView = imageView;
        this.x = x;
        this.y = y;
        this.z = z;
        this.gameWorldMyHome = gameWorldMyHome;
        this.appCompatActivity = appCompatActivity;
        init();
    }

    private void init() {
        // Khởi tạo ban đầu là đứng im và hướng là trên
        dir = TOP;
        state = DUNG_IM;
        // Không làm gì hết
        this.count = 0;
        // create animation
        hanhDongDungImLen = SourceAnimation.getInstance().getAnimation("crono_dung_im_len");
        hanhDongDungImXuong = SourceAnimation.getInstance().getAnimation("crono_dung_im_xuong");
        hanhDongDiTrai = SourceAnimation.getInstance().getAnimation("crono_di_phai");
        hanhDongDiTrai.flip();
        hanhDongLacDauTren = SourceAnimation.getInstance().getAnimation("crono_lac_dau_tren");
        // Set lại tọa độ theo camera
        xDraw = x - gameWorldMyHome.getCameraMyHome().getX();
        yDraw = y - gameWorldMyHome.getCameraMyHome().getY();
        // Cập nhật lại tọa độ của background theo camera
        imageView.setX(xDraw);
        imageView.setY(yDraw);
        // Cập nhật lại tạo độ của z
        imageView.setTranslationZ(z);
    }

    @Override
    public void update() {
        // Set lại tọa độ theo camera
        xDraw = x - gameWorldMyHome.getCameraMyHome().getX();
        yDraw = y - gameWorldMyHome.getCameraMyHome().getY();
        // Cập nhật lại tọa độ của background theo camera
        appCompatActivity.runOnUiThread(() -> {
            imageView.setX(xDraw);
            imageView.setY(yDraw);
        });
        // Update ban đầu
        if (lastTimeUpdate == 0) {
            lastTimeUpdate = System.currentTimeMillis();
        }
        // count = 1 thì bắt đầu chạy
        if (count == 1) {
            dir = TOP;
            state = LAC_DAU;
            lastTimeUpdate = System.currentTimeMillis();
            count++;
        } else if (count == 2) {
            if (System.currentTimeMillis() - lastTimeUpdate > 5000) {
                lastTimeUpdate = System.currentTimeMillis();
                count++;
                state = DUNG_IM;
            }
        } else if (count == 4) {
            if (dir == TOP) {
                dir = LEFT;
                state = DI;
                appCompatActivity.runOnUiThread(() -> imageView.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(144, 216))));
            }
            if (x > 1092) {
                x--;
            } else {
                count++;
                dir = BOTTOM;
                state = DUNG_IM;
                lastTimeUpdate = System.currentTimeMillis();
                appCompatActivity.runOnUiThread(() -> imageView.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(120, 222))));
            }
        } else if (count == 5) {
            if (System.currentTimeMillis() - lastTimeUpdate > 2000) {
                // xong chrono , tạo crono chơi
                gameWorldMyHome.setState(GameWorldMyHome.CREATE_CHRONO_PLAY);
                count++;
                // set state
                SourceMain.getInstance().setStartIntroMyHomeUp(false);
            }
        }
    }

    public void start() {
        count++;
    }

    @Override
    public void draw() {
        // update and draw animation
        switch (state) {
            case DUNG_IM:
                if (dir == RIGHT) {
                } else if (dir == LEFT) {
                } else if (dir == TOP) {
                    hanhDongDungImLen.update();
                    hanhDongDungImLen.draw(imageView, appCompatActivity);
                } else if (dir == BOTTOM) {
                    hanhDongDungImXuong.update();
                    hanhDongDungImXuong.draw(imageView, appCompatActivity);
                }
                break;
            case LAC_DAU:
                if (dir == TOP) {
                    hanhDongLacDauTren.update();
                    hanhDongLacDauTren.draw(imageView, appCompatActivity);
                }
                break;
            case DI:
                if (dir == LEFT) {
                    hanhDongDiTrai.update();
                    hanhDongDiTrai.draw(imageView, appCompatActivity);
                }
        }
    }

    @Override
    public boolean isOutCamera() {
        return count == 6;
    }

    @Override
    public void outToLayout() {
        appCompatActivity.runOnUiThread(() -> gameWorldMyHome.getMyHomeActivity().getAbsoluteLayout().removeView(imageView));
    }
}
