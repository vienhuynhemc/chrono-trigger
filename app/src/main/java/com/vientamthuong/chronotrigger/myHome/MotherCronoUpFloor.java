package com.vientamthuong.chronotrigger.myHome;

import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.data.SourceSound;
import com.vientamthuong.chronotrigger.gameEffect.Animation;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;
import com.vientamthuong.chronotrigger.loadData.ConfigurationSound;

import java.util.ArrayList;
import java.util.List;

public class MotherCronoUpFloor implements Observer {

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
    private final GameWorldMyHome gameWorldMyHome;
    // Thời gian update
    private long lastTimeUpdate;
    // Boolean các khung thời gian
    private boolean isStopMove1;
    private boolean isStartMove2;
    private boolean isStartMove3;
    private boolean isStartMove4;
    private boolean isStartMove5;
    private int countStartMove5;
    private boolean isOpenDoor;

    public MotherCronoUpFloor(ImageView imageView, int x, int y, int z, AppCompatActivity appCompatActivity, GameWorldMyHome gameWorldMyHome) {
        this.imageView = imageView;
        this.x = x;
        this.y = y;
        this.z = z;
        this.gameWorldMyHome = gameWorldMyHome;
        this.appCompatActivity = appCompatActivity;
        init();
    }

    private void init() {
        // Khởi tạo ban đầu là đứng im và hướng là trái
        dir = RIGHT;
        state = DI;
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
        if (!isStopMove1) {
            if (x < 779 + 322) {
                x += 1;
            } else {
                state = DUNG_IM;
                isStopMove1 = true;
            }
        }
        // Di chuyển vị trí 2
        if (isStartMove2 && gameWorldMyHome.getState() == GameWorldMyHome.CHAT_SECOND) {
            if (y > 348) {
                y -= 1;
            } else {
                if (!isOpenDoor) {
                    isOpenDoor = true;
                    state = GIO_TAY;
                    lastTimeUpdate = System.currentTimeMillis();
                    SourceSound.getInstance().play("flap_once", ConfigurationSound.NOREPEAT);
                } else {
                    if (System.currentTimeMillis() - lastTimeUpdate > 500) {
                        state = DUNG_IM;
                        gameWorldMyHome.setState(GameWorldMyHome.CHAT_THIRD);
                        gameWorldMyHome.getBackgroundMapMyHome().changeToLight();
                        List<String> contentChats = new ArrayList<>();
                        contentChats.add("Mẹ: Gửi em!\nTôi đã quên tiếng chuông của Leene hay như thế nào! ~ ( Tiếng hát )");
                        gameWorldMyHome.runChat(contentChats);
                    }
                }
            }
        }
        // Di chuyển vị trí 3
        if (isStartMove3() && gameWorldMyHome.getState() == GameWorldMyHome.CHAT_THIRD) {
            if (y < 558) {
                y += 1;
            } else {
                dir = RIGHT;
                state = DUNG_IM;
                gameWorldMyHome.setState(GameWorldMyHome.CHAT_FOUR);
                List<String> contentChats = new ArrayList<>();
                contentChats.add("Mẹ :Chắc hẳn con đã rất hào hứng với Hội chợ Millennial đến nỗi đêm qua con không thể ngủ được phải không?");
                contentChats.add("Tốt hơn hết là con đừng để sự ham chơi đó khiến con gặp bất cứ rắc rối nào, mẹ muốn con hoàn thành tốt các công việc của con trong ngày hôm nay!");
                gameWorldMyHome.runChat(contentChats);
            }
        }
        // Di chuyển đến vị trí 4
        if (isStartMove4() && gameWorldMyHome.getState() == GameWorldMyHome.CHAT_FOUR) {
            if (x > 700) {
                x -= 1;
            } else {
                dir = RIGHT;
                state = DUNG_IM;
                gameWorldMyHome.setState(GameWorldMyHome.CHAT_FIVE);
                List<String> contentChats = new ArrayList<>();
                contentChats.add("Mẹ :Dậy nào! Hãy ra khỏi giường!");
                gameWorldMyHome.runChat(contentChats);
            }
        }
        if (isStartMove5 && gameWorldMyHome.getState() == GameWorldMyHome.CHAT_FIVE && countStartMove5 < 3) {
            if (countStartMove5 == 0) {
                if (y < 882) {
                    y += 1;
                } else {
                    dir = RIGHT;
                    countStartMove5++;
                }
            } else if (countStartMove5 == 1) {
                if (x < 766) {
                    x += 1;
                } else {
                    countStartMove5++;
                }
            } else if (countStartMove5 == 2) {
                if (x < 1108) {
                    x += 1;
                    y += 1;
                } else {
                    countStartMove5++;
                }
            }
        }
    }

    @Override
    public void draw() {
        // update and draw animation
        switch (state) {
            case DUNG_IM:
                if (dir == RIGHT) {
                    hanhDongDungImPhai.update();
                    hanhDongDungImPhai.draw(imageView, appCompatActivity);
                } else if (dir == LEFT) {
                    hanhDongDungImTrai.update();
                    hanhDongDungImTrai.draw(imageView, appCompatActivity);
                } else if (dir == TOP) {
                    hanhDongDungImLen.update();
                    hanhDongDungImLen.draw(imageView, appCompatActivity);
                }
                break;
            case DI:
                if (dir == RIGHT) {
                    hanhDongDiPhai.update();
                    hanhDongDiPhai.draw(imageView, appCompatActivity);
                } else if (dir == LEFT) {
                    hanhDongDiTrai.update();
                    hanhDongDiTrai.draw(imageView, appCompatActivity);
                } else if (dir == TOP) {
                    hanhDongDiLen.update();
                    hanhDongDiLen.draw(imageView, appCompatActivity);
                } else if (dir == BOTTOM) {
                    hanhDongDiXuong.update();
                    hanhDongDiXuong.draw(imageView, appCompatActivity);
                }
                break;
            case GIO_TAY:
                if (dir == BOTTOM) {
                    hanhDongGioTayXuong.update();
                    hanhDongGioTayXuong.draw(imageView, appCompatActivity);
                } else if (dir == TOP) {
                    hanhDongGioTayLen.update();
                    hanhDongGioTayLen.draw(imageView, appCompatActivity);
                }
                break;
        }
    }

    @Override
    public boolean isOutCamera() {
        return countStartMove5 == 3;
    }

    @Override
    public void outToLayout() {
        appCompatActivity.runOnUiThread(() -> gameWorldMyHome.getMyHomeActivity().getAbsoluteLayout().removeView(imageView));
        // Khi mẹ vừa biến mất thì mèo nhảy lên và cũng đi xuống cầu thang
        gameWorldMyHome.getCatUpFloor().readyGoDownHome();
        gameWorldMyHome.getCatUpFloor().setMoveDownHome(true);
    }

    // GETTER AND SETTEr
    public boolean isStopMove1() {
        return isStopMove1;
    }

    public void setStartMove2(boolean isStartMove2) {
        this.isStartMove2 = isStartMove2;
    }

    public boolean isStartMove2() {
        return isStartMove2;
    }

    public boolean isStartMove3() {
        return isStartMove3;
    }

    public void setStartMove3(boolean startMove3) {
        isStartMove3 = startMove3;
    }

    public boolean isStartMove4() {
        return isStartMove4;
    }

    public void setStartMove4(boolean startMove4) {
        isStartMove4 = startMove4;
    }

    public boolean isStartMove5() {
        return isStartMove5;
    }

    public void setStartMove5(boolean startMove5) {
        isStartMove5 = startMove5;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public void setState(int state) {
        this.state = state;
    }


}
