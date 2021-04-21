package com.vientamthuong.chronotrigger.myHome;

import com.vientamthuong.chronotrigger.data.SourceMain;
import com.vientamthuong.chronotrigger.data.SourceSound;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;
import com.vientamthuong.chronotrigger.loadData.ConfigurationSound;

import java.util.ArrayList;
import java.util.List;

public class GameWorldMyHome {
    // State
    private int state;
    public static final int START_INTRO = 0;
    public static final int START_NO_INTRO = 1;
    public static final int CHAT_SECOND = 2;
    // List các object
    private final List<Observer> listObject;
    private MyHomeActivity myHomeActivity;
    private GameThreadMyHome gameThreadMyHome;
    // Object full screen
    private ObjectFullScreenMyHome objectFullScreenMyHome;
    // Khung chat
    private ShowTextMyHome showTextMyHome;
    // Các biến sử lý theo khung thời gian
    private long lastTimeWaitIntro;
    // Start intro
    private final boolean isStartIntro;
    private int countStartSoundBell;
    private long lastTimeStartSoundBell;
    // Đoạn chat đầu tiên trước khi mở map
    private boolean isStartFirstChat;
    // Background map
    private BackgroundMapMyHome backgroundMapMyHome;
    // Camera
    private CameraMyHome cameraMyHome;

    public GameWorldMyHome(MyHomeActivity myHomeActivity, GameThreadMyHome gameThreadMyHome, boolean isStartIntro) {
        this.myHomeActivity = myHomeActivity;
        this.gameThreadMyHome = gameThreadMyHome;
        listObject = new ArrayList<>();
        this.isStartIntro = isStartIntro;
        // Khởi tạo
        init();
    }

    private void init() {
        showTextMyHome = new ShowTextMyHome(myHomeActivity.getTvShowText(), myHomeActivity);
        // xét xem có chạy intro hay không
        if (isStartIntro) {
            lastTimeWaitIntro = System.currentTimeMillis();
            state = START_INTRO;
        } else {
            state = START_NO_INTRO;
        }
        // Camera
        cameraMyHome = new CameraMyHome(0,0,GameWorldMyHome.this);
        // Object full screen
        objectFullScreenMyHome = new ObjectFullScreenMyHome(myHomeActivity.getIvFullScreen(), myHomeActivity, GameWorldMyHome.this);
        // Back ground map
        backgroundMapMyHome = new BackgroundMapMyHome(myHomeActivity.getIvBackgroundMap(), myHomeActivity, GameWorldMyHome.this);
        listObject.add(backgroundMapMyHome);
    }

    public void update() {
        switch (state) {
            case START_INTRO:
                // Chạy intro
                // Đợi 3 giây mới bắt đầu chạy intro
                if (System.currentTimeMillis() - lastTimeWaitIntro > 3000) {
                    // Chạy âm thanh chuông 3 lần
                    if (countStartSoundBell < 3) {
                        if (System.currentTimeMillis() - lastTimeStartSoundBell > 3000) {
                            countStartSoundBell++;
                            lastTimeStartSoundBell = System.currentTimeMillis();
                            SourceSound.getInstance().play("ieene_bell", ConfigurationSound.NOREPEAT);
                        }
                    }
                    // Chạy âm thanh chuông 1 lần thì hiện
                    if (countStartSoundBell > 0 && !isStartFirstChat) {
                        isStartFirstChat = true;
                        // Lấy đoạn chat đầu tiên
                        List<String> contentChats = createFirstChat();
                        // Làm mới show text my home
                        showTextMyHome.setComplete(false);
                        showTextMyHome.setContent(contentChats);
                        // Hiện nó lên
                        showTextMyHome.show();
                    }
                    // Chạy đủ 3 lần nhạc ban đầu thì chờ 2s rồi chuyển qua state
                    if (countStartSoundBell == 3) {
                        // thêm 2s
                        if (System.currentTimeMillis() - lastTimeStartSoundBell > 2000) {
                            objectFullScreenMyHome.hidden();
                            // Chuyển qua state chat second
                            state = CHAT_SECOND;
                            // Cho thời gian last time wait là hiện tại và object chưa ẩn hoàn toàn
                            objectFullScreenMyHome.setHidden(false);
                            lastTimeWaitIntro = System.currentTimeMillis();
                        }
                    }
                }
                break;
            case CHAT_SECOND:
                // Nếu object full screen chưa ẩn thì chờ 3s rồi ẩn
                if (!objectFullScreenMyHome.isHidden()) {
                    if (System.currentTimeMillis() - lastTimeWaitIntro > 3000) {
                        objectFullScreenMyHome.hiddenView();
                        objectFullScreenMyHome.setHidden(true);
                    }
                }
                break;
            case START_NO_INTRO:
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
            count++;
        }
    }

    public void draw() {
        // Vẽ lần lượt các object
        int count = 0;
        while (count < listObject.size()) {
            listObject.get(count).update();
            count++;
        }
    }

    public List<String> createFirstChat() {
        List<String> contentChats = new ArrayList<>();
        String name = SourceMain.getInstance().getName();
        contentChats.add(name + "...");
        contentChats.add(name + "!");
        contentChats.add(name + ", con vẫn còn đang ngủ ư?");
        return contentChats;
    }

    // GETTER AND SETTER
    public CameraMyHome getCameraMyHome() {
        return cameraMyHome;
    }

}
