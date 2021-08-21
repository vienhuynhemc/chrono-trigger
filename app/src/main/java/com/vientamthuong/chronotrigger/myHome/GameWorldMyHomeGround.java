package com.vientamthuong.chronotrigger.myHome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.data.SourceMain;
import com.vientamthuong.chronotrigger.data.SourceSound;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;
import com.vientamthuong.chronotrigger.loadData.ConfigurationSound;
import com.vientamthuong.chronotrigger.mainCharacter.Chrono;
import com.vientamthuong.chronotrigger.mainModel.GameWorld;
import com.vientamthuong.chronotrigger.mainModel.Joystick;
import com.vientamthuong.chronotrigger.newGame.NewGameActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameWorldMyHomeGround implements GameWorld {
    // State
    private int state;
    public static final int START_INTRO = 0;
    public static final int START_NO_INTRO = 1;
    public static final int STATE_SLEEP = 2;
    public static final int CHAT_FIRST = 3;
    public static final int CHAT_SECOND = 4;
    public static final int CHAT_THREE = 5;
    public static final int CHAT_FOUR = 6;
    public static final int CREATE_CHRONO_PLAY = 7;
    public static final int NONE = 8;
    public static final int LOAD = 9;
    // List các object
    private final List<Observer> listObject;
    private MyHomeGroundActivity myHomeGroundActivity;
    private GameThreadMyHomeGround gameThreadMyHomeGround;
    // Object full screen
    private ObjectFullScreenMyHomeGround objectFullScreenMyHomeGround;
    // Khung chat
    private ShowTextMyHome showTextMyHome;
    // Các biến sử lý theo khung thời gian
    private long lastTimeWaitIntro;
    // Start intro
    private final boolean isStartIntro;
    private int countStartSoundBell;
    private long lastTimeStartSoundBell;
    // Đoạn chat
    private boolean isStartFirstChat;
    private boolean isStartSecondChat;
    // Background map
    private BackgroundMapMyHomeGround backgroundMapMyHomeGround;
    // Camera
    private CameraMyHomeGround cameraMyHomeGround;
    // Các object của intro
    private MotherCronoGround motherCronoGround;
    private CatInGround catInGround;
    private CronoMyHomeGround cronoMyHomeGround;
    private Chrono chrono;
    private Joystick joystick;
    private GateToUp gateToUp;
    private boolean isLoad;

    public GameWorldMyHomeGround(MyHomeGroundActivity myHomeGroundActivity, GameThreadMyHomeGround gameThreadMyHomeGround, boolean isStartIntro, boolean isLoad) {
        this.myHomeGroundActivity = myHomeGroundActivity;
        this.gameThreadMyHomeGround = gameThreadMyHomeGround;
        listObject = new ArrayList<>();
        this.isStartIntro = isStartIntro;
        this.isLoad = isLoad;
        //khởi tạo
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        showTextMyHome = new ShowTextMyHome(myHomeGroundActivity.getTvShowTextTren(), myHomeGroundActivity);
        // xét xem có chạy intro hay không
        if (isStartIntro) {
            lastTimeWaitIntro = System.currentTimeMillis();
            state = START_INTRO;
        } else {
            if (isLoad) {
                state = LOAD;
            } else {
                state = START_NO_INTRO;
            }
        }
        // Camera
        cameraMyHomeGround = new CameraMyHomeGround(0, 105, GameWorldMyHomeGround.this);
        // Object full screen
        objectFullScreenMyHomeGround = new ObjectFullScreenMyHomeGround(myHomeGroundActivity.getIvFullScreenGround(), myHomeGroundActivity, GameWorldMyHomeGround.this);
        // Back ground map
        backgroundMapMyHomeGround = new BackgroundMapMyHomeGround(myHomeGroundActivity.getIvBackgroundMapGround(), myHomeGroundActivity, GameWorldMyHomeGround.this);
        listObject.add(backgroundMapMyHomeGround);
        // gate
        gateToUp = new GateToUp(852 + ConfigurationMyHome.X_BACKGROUNMAP_UP, 288, 204, 192, GameWorldMyHomeGround.this);
        // aciton di chuyển bằng joustick
        myHomeGroundActivity.getAbsoluteLayout().setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (joystick != null) {
                        if (joystick.isPressed(event.getX(), event.getY())) {
                            joystick.setPressed(true);
                            if (chrono != null) {
                                chrono.setState(Chrono.DI);
                            }
                        }
                    }
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (joystick != null) {
                        if (joystick.isPressed()) {
                            joystick.setActuator(event.getX(), event.getY());
                        }
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    if (joystick != null) {
                        joystick.setPressed(false);
                        joystick.resetActuator();
                    }
                    if (chrono != null) {
                        chrono.setState(Chrono.DUNG_IM);
                    }
                    return true;
            }
            return true;
        });
    }

    public void update() {
        switch (state) {
            case START_INTRO:
                SourceSound.getInstance().playSoundBackground("peaceful_days", myHomeGroundActivity);
                objectFullScreenMyHomeGround.hiddenView();
                objectFullScreenMyHomeGround.setHidden(true);
                // Lúc màn hình bắt đầu mở đi thì tạo các object intro
                // 1.Mèo
                // 1.1 Tạo image view cho con mèo
                ImageView imageViewCat = new ImageView(myHomeGroundActivity);
                imageViewCat.setScaleType(ImageView.ScaleType.MATRIX);
                imageViewCat.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationMyHome.WIDTH_CAT, ConfigurationMyHome.HEIGHT_CAT));
                myHomeGroundActivity.runOnUiThread(() -> myHomeGroundActivity.getAbsoluteLayout().addView(imageViewCat, myHomeGroundActivity.getAbsoluteLayout().getChildCount() - 2));
                // 1.2 Tạo mèo
                catInGround = new CatInGround(imageViewCat, 700 + ConfigurationMyHome.X_BACKGROUNMAP_UP,
                        1000, 10,
                        myHomeGroundActivity, GameWorldMyHomeGround.this);
                catInGround.setStartMove1(true);
                listObject.add(catInGround);
                // 2.Mẹ
                // 2.1 Taoj image view cho mẹ
                ImageView imageViewMother = new ImageView(myHomeGroundActivity);
                imageViewMother.setScaleType(ImageView.ScaleType.MATRIX);
                imageViewMother.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationMyHome.WIDTH_MOTHER, ConfigurationMyHome.HEIGHT_MOTHER));
                myHomeGroundActivity.runOnUiThread(() -> myHomeGroundActivity.getAbsoluteLayout().addView(imageViewMother, myHomeGroundActivity.getAbsoluteLayout().getChildCount() - 2));
                // 2.2 Tạo mẹ
                motherCronoGround = new MotherCronoGround(imageViewMother, 1200 + ConfigurationMyHome.X_BACKGROUNMAP_UP, 600, 12, myHomeGroundActivity, GameWorldMyHomeGround.this);
                listObject.add(motherCronoGround);
                //tạo image view crono
                ImageView imageViewCrono = new ImageView(myHomeGroundActivity);
                imageViewCrono.setScaleType(ImageView.ScaleType.MATRIX);
                imageViewCrono.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(144, 216)));
                myHomeGroundActivity.runOnUiThread(() -> myHomeGroundActivity.getAbsoluteLayout().addView(imageViewCrono, myHomeGroundActivity.getAbsoluteLayout().getChildCount() - 2));

                cronoMyHomeGround = new CronoMyHomeGround(imageViewCrono, 800 + ConfigurationMyHome.X_BACKGROUNMAP_UP, 40, 11, myHomeGroundActivity, GameWorldMyHomeGround.this);
                cronoMyHomeGround.setStartMove1(true);
                listObject.add(cronoMyHomeGround);
                state = STATE_SLEEP;
                break;
            case CHAT_FIRST:
                // Lấy đoạn chat đầu tiên
                List<String> firstChat = createFirstChat();
                // Làm mới show text my home
                showTextMyHome.setComplete(false);
                showTextMyHome.setContent(firstChat);
                // Hiện nó lên
                showTextMyHome.show();
                state = CHAT_SECOND;
                break;
            case CHAT_SECOND:
                if (showTextMyHome.isComplete()) {
                    Intent intent = new Intent(myHomeGroundActivity, NewGameActivity.class);
                    intent.putExtra("name", "lucca");
                    intent.putExtra("isStartIntro", false);
                    myHomeGroundActivity.startActivity(intent);
                    state = CHAT_THREE;
                }
                break;
            case CHAT_THREE:
                if (showTextMyHome.isComplete() && NewGameActivity.isFinish()) {
                    List<String> threeChat = createThreeChat();
                    // Làm mới show text my home
                    showTextMyHome.setComplete(false);
                    showTextMyHome.setContent(threeChat);
                    // Hiện nó lên
                    showTextMyHome.show();
                    state = CHAT_FOUR;
                }
                break;
            case CHAT_FOUR:
                if (showTextMyHome.isComplete() && NewGameActivity.isFinish()) {
                    motherCronoGround.setStartMove1(true);
                    state = STATE_SLEEP;
                }
                break;
            case CREATE_CHRONO_PLAY:
                ImageView imageViewChronoUpfloor = new ImageView(myHomeGroundActivity);
                imageViewChronoUpfloor.setScaleType(ImageView.ScaleType.MATRIX);
                imageViewChronoUpfloor.setLayoutParams(new ViewGroup.LayoutParams(120, ConfigurationMyHome.HEIGHT_CHRONO_DIR_TOP));
                myHomeGroundActivity.runOnUiThread(() -> myHomeGroundActivity.getAbsoluteLayout().addView(imageViewChronoUpfloor, myHomeGroundActivity.getAbsoluteLayout().getChildCount() - 2));
                chrono = new Chrono(imageViewChronoUpfloor, 1500, 418, 999, myHomeGroundActivity, GameWorldMyHomeGround.this, Chrono.BOTTOM, Chrono.DUNG_IM);
                listObject.add(chrono);
                state = NONE;
                break;
            case NONE:
                break;
            case START_NO_INTRO:
                // Ẩn full screen
                objectFullScreenMyHomeGround.hiddenView();
                objectFullScreenMyHomeGround.setHidden(true);
                // 1.Mèo
                // 1.1 Tạo image view cho con mèo
                imageViewCat = new ImageView(myHomeGroundActivity);
                imageViewCat.setScaleType(ImageView.ScaleType.MATRIX);
                imageViewCat.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationMyHome.WIDTH_CAT, ConfigurationMyHome.HEIGHT_CAT));
                myHomeGroundActivity.runOnUiThread(() -> myHomeGroundActivity.getAbsoluteLayout().addView(imageViewCat, myHomeGroundActivity.getAbsoluteLayout().getChildCount() - 2));
                // 1.2 Tạo mèo
                catInGround = new CatInGround(imageViewCat, 700 + ConfigurationMyHome.X_BACKGROUNMAP_UP,
                        1000, 10,
                        myHomeGroundActivity, GameWorldMyHomeGround.this);
                catInGround.setStartMove1(true);
                listObject.add(catInGround);
                // 2.Mẹ
                // 2.1 Taoj image view cho mẹ
                imageViewMother = new ImageView(myHomeGroundActivity);
                imageViewMother.setScaleType(ImageView.ScaleType.MATRIX);
                imageViewMother.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationMyHome.WIDTH_MOTHER, ConfigurationMyHome.HEIGHT_MOTHER));
                myHomeGroundActivity.runOnUiThread(() -> myHomeGroundActivity.getAbsoluteLayout().addView(imageViewMother, myHomeGroundActivity.getAbsoluteLayout().getChildCount() - 2));
                // 2.2 Tạo mẹ
                motherCronoGround = new MotherCronoGround(imageViewMother, 900, 400, 12, myHomeGroundActivity, GameWorldMyHomeGround.this);
                listObject.add(motherCronoGround);
                // Tạo nhân vật
                imageViewChronoUpfloor = new ImageView(myHomeGroundActivity);
                imageViewChronoUpfloor.setScaleType(ImageView.ScaleType.MATRIX);
                imageViewChronoUpfloor.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationMyHome.WIDTH_CHRONO_DIR_TOP, ConfigurationMyHome.HEIGHT_CHRONO_DIR_TOP));
                myHomeGroundActivity.runOnUiThread(() -> myHomeGroundActivity.getAbsoluteLayout().addView(imageViewChronoUpfloor, myHomeGroundActivity.getAbsoluteLayout().getChildCount() - 2));
                chrono = new Chrono(imageViewChronoUpfloor, 1500, 418, 999, myHomeGroundActivity, GameWorldMyHomeGround.this, Chrono.BOTTOM, Chrono.DUNG_IM);
                listObject.add(chrono);
                state = NONE;
                break;
            case LOAD:
                // Ẩn full screen
                objectFullScreenMyHomeGround.hiddenView();
                objectFullScreenMyHomeGround.setHidden(true);
                // 1.Mèo
                // 1.1 Tạo image view cho con mèo
                imageViewCat = new ImageView(myHomeGroundActivity);
                imageViewCat.setScaleType(ImageView.ScaleType.MATRIX);
                imageViewCat.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationMyHome.WIDTH_CAT, ConfigurationMyHome.HEIGHT_CAT));
                myHomeGroundActivity.runOnUiThread(() -> myHomeGroundActivity.getAbsoluteLayout().addView(imageViewCat, myHomeGroundActivity.getAbsoluteLayout().getChildCount() - 2));
                // 1.2 Tạo mèo
                catInGround = new CatInGround(imageViewCat, 700 + ConfigurationMyHome.X_BACKGROUNMAP_UP,
                        1000, 10,
                        myHomeGroundActivity, GameWorldMyHomeGround.this);
                catInGround.setStartMove1(true);
                listObject.add(catInGround);
                // 2.Mẹ
                // 2.1 Taoj image view cho mẹ
                imageViewMother = new ImageView(myHomeGroundActivity);
                imageViewMother.setScaleType(ImageView.ScaleType.MATRIX);
                imageViewMother.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationMyHome.WIDTH_MOTHER, ConfigurationMyHome.HEIGHT_MOTHER));
                myHomeGroundActivity.runOnUiThread(() -> myHomeGroundActivity.getAbsoluteLayout().addView(imageViewMother, myHomeGroundActivity.getAbsoluteLayout().getChildCount() - 2));
                // 2.2 Tạo mẹ
                motherCronoGround = new MotherCronoGround(imageViewMother, 900, 400, 12, myHomeGroundActivity, GameWorldMyHomeGround.this);
                listObject.add(motherCronoGround);
                // Tạo nhân vật
                imageViewChronoUpfloor = new ImageView(myHomeGroundActivity);
                imageViewChronoUpfloor.setScaleType(ImageView.ScaleType.MATRIX);
                if (SourceMain.getInstance().getDir() == Chrono.TOP || SourceMain.getInstance().getDir() == Chrono.BOTTOM) {
                    imageViewChronoUpfloor.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationMyHome.WIDTH_CHRONO_DIR_TOP, ConfigurationMyHome.HEIGHT_CHRONO_DIR_TOP));
                } else {
                    imageViewChronoUpfloor.setLayoutParams(new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(144, 216)));
                }
                myHomeGroundActivity.runOnUiThread(() -> myHomeGroundActivity.getAbsoluteLayout().addView(imageViewChronoUpfloor, myHomeGroundActivity.getAbsoluteLayout().getChildCount() - 2));
                chrono = new Chrono(imageViewChronoUpfloor, SourceMain.getInstance().getX(), SourceMain.getInstance().getY(), 999, myHomeGroundActivity, GameWorldMyHomeGround.this, SourceMain.getInstance().getDir(), Chrono.DUNG_IM);
                listObject.add(chrono);
                state = NONE;
                break;
        }


        // Update dành cho show text my home
        // Nó chưa hoàn thành thì cập nhật
        if (!showTextMyHome.isComplete()) {
            showTextMyHome.update();
            // Mếu nó hoàn thành thì ẩn nó đi
            if (showTextMyHome.isComplete()) {
                showTextMyHome.hidden();
            }
        }
        // Cập nhật list object
        // list Object
        int count = 0;
        while (count < listObject.size()) {
            listObject.get(count).update();
            if (listObject.get(count).isOutCamera()) {
                listObject.get(count).outToLayout();
                listObject.remove(count);
            } else {
                count++;
            }
        }

        // update joystick
        if (this.joystick != null) {
            this.joystick.update();
        }

        // update camera
        for (int i = 0; i < 4; i++) {
            cameraMyHomeGround.update();
        }

        // update gate
        gateToUp.update();

    }

    public void draw() {
        // Vẽ lần lượt các object
        int count = 0;
        while (count < listObject.size()) {
            listObject.get(count).draw();
            count++;
        }

        // vẽ joystick
        // Không vẽ joystick khi đang chạy intro
        if (this.getState() == NONE && joystick != null) {
            joystick.draw();
        }
    }

    public void runChat(List<String> contentChats) {
        isStartFirstChat = true;
        // Làm mới show text my home
        showTextMyHome.setComplete(false);
        showTextMyHome.setContent(contentChats);
        // Hiện nó lên
        showTextMyHome.show();
    }

    public List<String> createFirstChat() {
        List<String> contentChats = new ArrayList<>();
        contentChats.add("Mẹ: Con dậy rồi đó à\nCon định đến gặp một người bạn phải không?");
        contentChats.add("Mẹ: Ôi trời! Mẹ lại quên mất\nCô ấy tên gì nhỉ?\nCô bạn nhà phát minh của con?");
        return contentChats;
    }

    public List<String> createThreeChat() {
        List<String> contentChats = new ArrayList<>();
        String name = SourceMain.getInstance().getNameLucca();
        contentChats.add("Mẹ: Đúng rồi! " + name + "\nCon định đến xem phát minh mới của cô ấy ở hội chợ phải không?");
        contentChats.add("Mẹ: Tốt thôi! Con cứ đi đi\nNhớ về nhà trước bữa ăn tối đấy!");
        return contentChats;
    }
    //getter setter

    public CameraMyHomeGround getCameraMyHomeGround() {
        return cameraMyHomeGround;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public BackgroundMapMyHomeGround getBackgroundMapMyHomeGround() {
        return backgroundMapMyHomeGround;
    }

    @Override
    public int getXCamera() {
        return cameraMyHomeGround.getX();
    }

    @Override
    public int getYCamera() {
        return cameraMyHomeGround.getY();
    }

    @Override
    public AbsoluteLayout getAbsoluteLayout() {
        return myHomeGroundActivity.getAbsoluteLayout();
    }

    @Override
    public AppCompatActivity getAppCompatActivity() {
        return myHomeGroundActivity;
    }

    @Override
    public Joystick getJoystick() {
        return joystick;
    }

    @Override
    public void setJoystick(Joystick joystick) {
        this.joystick = joystick;
    }

    public CronoMyHomeGround getCronoMyHomeGround() {
        return cronoMyHomeGround;
    }

    public MyHomeGroundActivity getMyHomeGroundActivity() {
        return myHomeGroundActivity;
    }

    public Chrono getChrono() {
        return chrono;
    }

    public GameThreadMyHomeGround getGameThreadMyHomeGround() {
        return gameThreadMyHomeGround;
    }

    public void saveData() {
        if (state == NONE) {
            SourceMain.getInstance().setX(chrono.getX());
            SourceMain.getInstance().setY(chrono.getY());
            SourceMain.getInstance().setDir(chrono.getDir());
            SourceMain.getInstance().setNgaySave(new Date().getTime());
            SourceMain.getInstance().setNameMap("down");
        }
    }
}
