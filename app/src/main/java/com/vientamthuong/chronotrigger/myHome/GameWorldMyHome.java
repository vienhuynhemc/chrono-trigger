package com.vientamthuong.chronotrigger.myHome;

import android.view.ViewGroup;
import android.widget.ImageView;

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
    public static final int CHAT_THIRD = 3;
    public static final int CHAT_FOUR = 4;
    public static final int CHAT_FIVE = 5;
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
    // Đoạn chat
    private boolean isStartFirstChat;
    private boolean isStartSecondChat;
    // Background map
    private BackgroundMapMyHome backgroundMapMyHome;
    // Camera
    private CameraMyHome cameraMyHome;
    // Các object của intro
    private MotherCronoUpFloor motherCronoUpFloor;
    private CatUpFloor catUpFloor;
    private ChronoUpFloor chronoUpFloor;

    public GameWorldMyHome(MyHomeActivity myHomeActivity, GameThreadMyHome gameThreadMyHome, boolean isStartIntro) {
        this.myHomeActivity = myHomeActivity;
        this.gameThreadMyHome = gameThreadMyHome;
        listObject = new ArrayList<>();
        this.isStartIntro = isStartIntro;
        // Khởi tạo
        init();
    }

    private void init() {
        showTextMyHome = new ShowTextMyHome(myHomeActivity.getTvShowTextTren(), myHomeActivity);
        // xét xem có chạy intro hay không
        if (isStartIntro) {
            lastTimeWaitIntro = System.currentTimeMillis();
            state = START_INTRO;
        } else {
            state = START_NO_INTRO;
        }
        // Camera
        cameraMyHome = new CameraMyHome(0, 105, GameWorldMyHome.this);
        // Object full screen
        objectFullScreenMyHome = new ObjectFullScreenMyHome(myHomeActivity.getIvFullScreen(), myHomeActivity, GameWorldMyHome.this);
        // Back ground map
        backgroundMapMyHome = new BackgroundMapMyHome(myHomeActivity.getIvBackgroundMap(), myHomeActivity, GameWorldMyHome.this);
        listObject.add(backgroundMapMyHome);
        // Ảnh trước cầu thang
        ImageView imageViewFrontEnd = new ImageView(myHomeActivity);
        imageViewFrontEnd.setScaleType(ImageView.ScaleType.MATRIX);
        imageViewFrontEnd.setLayoutParams(new ViewGroup.LayoutParams(420, 240));
        myHomeActivity.runOnUiThread(() -> myHomeActivity.getAbsoluteLayout().addView(imageViewFrontEnd, myHomeActivity.getAbsoluteLayout().getChildCount() - 2));
        FrontEndStairsFloorMyHome frontEndStairsFloorMyHome = new FrontEndStairsFloorMyHome(imageViewFrontEnd, 754, 990, 12, myHomeActivity, GameWorldMyHome.this);
        listObject.add(frontEndStairsFloorMyHome);
        // Ảnh trước mền
        ImageView imageViewBlanketFrontEnd = new ImageView(myHomeActivity);
        imageViewBlanketFrontEnd.setScaleType(ImageView.ScaleType.MATRIX);
        imageViewBlanketFrontEnd.setLayoutParams(new ViewGroup.LayoutParams(192, 222));
        myHomeActivity.runOnUiThread(() -> myHomeActivity.getAbsoluteLayout().addView(imageViewBlanketFrontEnd, myHomeActivity.getAbsoluteLayout().getChildCount() - 2));
        FrontEndBlanketMyHome frontEndBlanketMyHome = new FrontEndBlanketMyHome(imageViewBlanketFrontEnd, 912 + ConfigurationMyHome.X_BACKGROUNMAP_UP, 630, 12, myHomeActivity, GameWorldMyHome.this);
        listObject.add(frontEndBlanketMyHome);
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
                            // Lúc màn hình bắt đầu mở đi thì tạo các object intro
                            // 1.Mèo
                            // 1.1 Tạo image view cho con mèo
                            ImageView imageViewCat = new ImageView(myHomeActivity);
                            imageViewCat.setScaleType(ImageView.ScaleType.MATRIX);
                            imageViewCat.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationMyHome.WIDTH_CAT, ConfigurationMyHome.HEIGHT_CAT));
                            myHomeActivity.runOnUiThread(() -> myHomeActivity.getAbsoluteLayout().addView(imageViewCat, myHomeActivity.getAbsoluteLayout().getChildCount() - 2));
                            // 1.2 Tạo mèo
                            catUpFloor = new CatUpFloor(imageViewCat, 670 + ConfigurationMyHome.X_BACKGROUNMAP_UP, 590, 10, myHomeActivity, GameWorldMyHome.this);
                            listObject.add(catUpFloor);
                            // 2.Mẹ
                            // 2.1 Taoj image view cho mẹ
                            ImageView imageViewMother = new ImageView(myHomeActivity);
                            imageViewMother.setScaleType(ImageView.ScaleType.MATRIX);
                            imageViewMother.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationMyHome.WIDTH_MOTHER, ConfigurationMyHome.HEIGHT_MOTHER));
                            myHomeActivity.runOnUiThread(() -> myHomeActivity.getAbsoluteLayout().addView(imageViewMother, myHomeActivity.getAbsoluteLayout().getChildCount() - 2));
                            // 2.2 Tạo mẹ
                            motherCronoUpFloor = new MotherCronoUpFloor(imageViewMother, 642 + ConfigurationMyHome.X_BACKGROUNMAP_UP, 558, 11, myHomeActivity, GameWorldMyHome.this);
                            listObject.add(motherCronoUpFloor);
                            // 3. Chrono up floor để chạy intro
                            // 3.1 Tạo image view
                            ImageView imageViewChronoUpfloor = new ImageView(myHomeActivity);
                            imageViewChronoUpfloor.setScaleType(ImageView.ScaleType.MATRIX);
                            // Vì ban đầu thằng này đứng hướng lên trên nên cho mặc định hướng lên trên
                            imageViewChronoUpfloor.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationMyHome.WIDTH_CHRONO_DIR_TOP, ConfigurationMyHome.HEIGHT_CHRONO_DIR_TOP));
                            myHomeActivity.runOnUiThread(() -> myHomeActivity.getAbsoluteLayout().addView(imageViewChronoUpfloor, myHomeActivity.getAbsoluteLayout().getChildCount() - 2));
                            // 3.2 Tạo chrono up floor
                            chronoUpFloor = new ChronoUpFloor(imageViewChronoUpfloor, 952 + ConfigurationMyHome.X_BACKGROUNMAP_UP, 548, 11, myHomeActivity, GameWorldMyHome.this);
                            listObject.add(chronoUpFloor);
                            // Đổi text trên thành text dưới
                            myHomeActivity.runOnUiThread(() -> myHomeActivity.getAbsoluteLayout().removeView(myHomeActivity.getTvShowTextTren()));
                            showTextMyHome.setTextView(myHomeActivity.getTvShowTextDuoi());
                            // Chạy nhạc nền mới
                            SourceSound.getInstance().playSoundBackgroundOnce("morning_glow", myHomeActivity);
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
                } else {
                    // Nếu như mẹ đã dừng ở điểm thứ nhất thì chạy đoạn chat thứ 2
                    if (motherCronoUpFloor.isStopMove1() && !isStartSecondChat) {
                        isStartSecondChat = true;
                        // Lấy đoạn chat thứ 2
                        List<String> contentChats = createSecondChat();
                        // Làm mới show text my home
                        showTextMyHome.setComplete(false);
                        showTextMyHome.setContent(contentChats);
                        // Hiện nó lên
                        showTextMyHome.show();
                    }
                    // Nếu như đoạn chat thứ 2 hoàn thành thì cho mẹ đi lên
                    if (showTextMyHome.isComplete() && motherCronoUpFloor.isStopMove1() && !motherCronoUpFloor.isStartMove2()) {
                        motherCronoUpFloor.setStartMove2(true);
                        motherCronoUpFloor.setDir(MotherCronoUpFloor.TOP);
                        motherCronoUpFloor.setState(MotherCronoUpFloor.DI);
                    }
                }
                break;
            case CHAT_THIRD:
                if (showTextMyHome.isComplete() && !motherCronoUpFloor.isStartMove3()) {
                    motherCronoUpFloor.setStartMove3(true);
                    motherCronoUpFloor.setDir(MotherCronoUpFloor.BOTTOM);
                    motherCronoUpFloor.setState(MotherCronoUpFloor.DI);
                }
                break;
            case CHAT_FOUR:
                if (showTextMyHome.isComplete() && !motherCronoUpFloor.isStartMove4()) {
                    motherCronoUpFloor.setStartMove4(true);
                    motherCronoUpFloor.setDir(MotherCronoUpFloor.LEFT);
                    motherCronoUpFloor.setState(MotherCronoUpFloor.DI);
                }
                break;
            case CHAT_FIVE:
                if (showTextMyHome.isComplete() && !motherCronoUpFloor.isStartMove5()) {
                    motherCronoUpFloor.setStartMove5(true);
                    motherCronoUpFloor.setDir(MotherCronoUpFloor.BOTTOM);
                    motherCronoUpFloor.setState(MotherCronoUpFloor.DI);
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
            if (listObject.get(count).isOutCamera()) {
                listObject.get(count).outToLayout();
                listObject.remove(count);
            } else {
                count++;
            }
        }
    }

    public void draw() {
        // Vẽ lần lượt các object
        int count = 0;
        while (count < listObject.size()) {
            listObject.get(count).draw();
            count++;
        }
    }

    public List<String> createFirstChat() {
        List<String> contentChats = new ArrayList<>();
        String name = SourceMain.getInstance().getNameCrono();
        contentChats.add(name + "...");
        contentChats.add(name + "!");
        contentChats.add(name + ", con vẫn còn đang ngủ ư?");
        return contentChats;
    }

    private List<String> createSecondChat() {
        List<String> contentChats = new ArrayList<>();
        String name = SourceMain.getInstance().getNameCrono();
        contentChats.add("Mẹ: Dậy nào, đồ ngủ nướng!\nĐã đến lúc thức dậy rồi!");
        return contentChats;
    }

    public void runChat(List<String> contentChats) {
        isStartFirstChat = true;
        // Làm mới show text my home
        showTextMyHome.setComplete(false);
        showTextMyHome.setContent(contentChats);
        // Hiện nó lên
        showTextMyHome.show();
    }

    // GETTER AND SETTER
    public CameraMyHome getCameraMyHome() {
        return cameraMyHome;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    public BackgroundMapMyHome getBackgroundMapMyHome() {
        return backgroundMapMyHome;
    }

    public MyHomeActivity getMyHomeActivity() {
        return myHomeActivity;
    }

    public CatUpFloor getCatUpFloor() {
        return catUpFloor;
    }

    public ChronoUpFloor getChronoUpFloor() {
        return chronoUpFloor;
    }
}
