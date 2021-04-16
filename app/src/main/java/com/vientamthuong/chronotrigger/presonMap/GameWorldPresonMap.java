package com.vientamthuong.chronotrigger.presonMap;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.vientamthuong.chronotrigger.camera.ConfigurationCamera;
import com.vientamthuong.chronotrigger.data.SourceSound;
import com.vientamthuong.chronotrigger.interfaceGameThread.Observer;
import com.vientamthuong.chronotrigger.loadData.ConfigurationSound;
import com.vientamthuong.chronotrigger.random.RandomSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameWorldPresonMap {

    // List các object
    private final List<Observer> listObject;
    // Camera
    private CameraPresonMap camera;
    private final PresonMapActivity presonMapActivity;
    // Thời gian cuối cập nhật để tạo chim
    private boolean isOverCreateBird;
    private long lastTimeCreateBird;
    private long lastTimePlayMusicBird;
    // Tạo khói
    private boolean isStartCreateSmoke;
    private long lastTimeCreateSmoke;
    // Tạo bong bóng
    private boolean isStartCreateBubble;
    private long lastTimeCreateBubble;
    // Tạo mây
    private long lastTimeCreateClound;
    private boolean isLeftClound;

    public GameWorldPresonMap(PresonMapActivity presonMapActivity) {
        this.presonMapActivity = presonMapActivity;
        listObject = new ArrayList<>();
        // Khởi tạo
        init();
    }

    private void init() {
        // chạy âm thanh
        lastTimePlayMusicBird = System.currentTimeMillis();
        SourceSound.getInstance().play("gulls", ConfigurationSound.NOREPEAT);
        // Camera
        camera = new CameraPresonMap(ConfigurationPresonMap.CAMRERA_START_X, ConfigurationPresonMap.CAMRERA_START_Y, GameWorldPresonMap.this);
        // Màn hình đen
        ObjectFullScreenPresonMap objectFullScreenPresonMap = new ObjectFullScreenPresonMap(presonMapActivity.getIvFullScreen(), presonMapActivity, GameWorldPresonMap.this);
        listObject.add(objectFullScreenPresonMap);
        // background
        BackgroundMapPresonMap backgroundMapPresonMap = new BackgroundMapPresonMap(presonMapActivity.getIvBackgroundMap(), presonMapActivity, GameWorldPresonMap.this);
        listObject.add(backgroundMapPresonMap);
        // Thuyền
        createBoat();
        // Tạo một số mây nhất định
        createClound();
    }

    private void createClound() {
        ImageView imageViewCloud1 = new ImageView(presonMapActivity);
        ImageView imageViewCloud2 = new ImageView(presonMapActivity);
        ImageView imageViewCloud3 = new ImageView(presonMapActivity);
        ImageView imageViewCloud4 = new ImageView(presonMapActivity);
        ImageView imageViewCloud5 = new ImageView(presonMapActivity);
        imageViewCloud1.setAlpha(0.5f);
        imageViewCloud2.setAlpha(0.5f);
        imageViewCloud3.setAlpha(0.5f);
        imageViewCloud4.setAlpha(0.5f);
        imageViewCloud5.setAlpha(0.5f);
        imageViewCloud1.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationPresonMap.WIDTH_SMOKE_BIG, ConfigurationPresonMap.HEIGHT_SMOKE_BIG));
        imageViewCloud2.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationPresonMap.WIDTH_SMOKE_BIG, ConfigurationPresonMap.HEIGHT_SMOKE_BIG));
        imageViewCloud3.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationPresonMap.WIDTH_SMOKE_BIG, ConfigurationPresonMap.HEIGHT_SMOKE_BIG));
        imageViewCloud4.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationPresonMap.WIDTH_SMOKE_BIG, ConfigurationPresonMap.HEIGHT_SMOKE_BIG));
        imageViewCloud5.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationPresonMap.WIDTH_SMOKE_SMALL, ConfigurationPresonMap.HEIGHT_SMOKE_SMALL));
        presonMapActivity.runOnUiThread(() -> {
            presonMapActivity.getAbsoluteLayout().addView(imageViewCloud1, presonMapActivity.getAbsoluteLayout().getChildCount() - 1);
            presonMapActivity.getAbsoluteLayout().addView(imageViewCloud2, presonMapActivity.getAbsoluteLayout().getChildCount() - 1);
            presonMapActivity.getAbsoluteLayout().addView(imageViewCloud3, presonMapActivity.getAbsoluteLayout().getChildCount() - 1);
            presonMapActivity.getAbsoluteLayout().addView(imageViewCloud4, presonMapActivity.getAbsoluteLayout().getChildCount() - 1);
            presonMapActivity.getAbsoluteLayout().addView(imageViewCloud5, presonMapActivity.getAbsoluteLayout().getChildCount() - 1);
        });
        CloudPresonMap cloudPresonMap1 = new CloudPresonMap(imageViewCloud1, 2496, 2184, 1, -1, ConfigurationPresonMap.WIDTH_SMOKE_BIG, ConfigurationPresonMap.HEIGHT_SMOKE_BIG, presonMapActivity, GameWorldPresonMap.this, 0);
        listObject.add(cloudPresonMap1);
        CloudPresonMap cloudPresonMap2 = new CloudPresonMap(imageViewCloud2, 3366, 2142, 1, -1, ConfigurationPresonMap.WIDTH_SMOKE_BIG, ConfigurationPresonMap.HEIGHT_SMOKE_BIG, presonMapActivity, GameWorldPresonMap.this, 0);
        listObject.add(cloudPresonMap2);
        CloudPresonMap cloudPresonMap3 = new CloudPresonMap(imageViewCloud3, 2550, 2526, 1, -1, ConfigurationPresonMap.WIDTH_SMOKE_BIG, ConfigurationPresonMap.HEIGHT_SMOKE_BIG, presonMapActivity, GameWorldPresonMap.this, 0);
        listObject.add(cloudPresonMap3);
        CloudPresonMap cloudPresonMap4 = new CloudPresonMap(imageViewCloud4, 3108, 2700, 1, -1, ConfigurationPresonMap.WIDTH_SMOKE_BIG, ConfigurationPresonMap.HEIGHT_SMOKE_BIG, presonMapActivity, GameWorldPresonMap.this, 0);
        listObject.add(cloudPresonMap4);
        CloudPresonMap cloudPresonMap5 = new CloudPresonMap(imageViewCloud5, 2988, 2412, 1, -1, ConfigurationPresonMap.WIDTH_SMOKE_SMALL, ConfigurationPresonMap.HEIGHT_SMOKE_SMALL, presonMapActivity, GameWorldPresonMap.this, 1);
        listObject.add(cloudPresonMap5);
    }

    private void createCloundUp() {
        ImageView imageViewCloud = new ImageView(presonMapActivity);
        imageViewCloud.setAlpha(0.5f);
        presonMapActivity.runOnUiThread(() -> presonMapActivity.getAbsoluteLayout().addView(imageViewCloud, presonMapActivity.getAbsoluteLayout().getChildCount() - 1));
        // Lấy random
        Random random = RandomSingleton.getInstance().getRandom();
        int startY = getCamera().getY() + random.nextInt(ConfigurationCamera.HEIGHT / 2);
        int type = random.nextInt(2);
        if (type == 0) {
            imageViewCloud.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationPresonMap.WIDTH_SMOKE_BIG, ConfigurationPresonMap.HEIGHT_SMOKE_BIG));
        } else {
            imageViewCloud.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationPresonMap.WIDTH_SMOKE_SMALL, ConfigurationPresonMap.HEIGHT_SMOKE_SMALL));
        }
        int startX = type == 1 ? getCamera().getX() - 306 : getCamera().getX() - 384;
        int speedY = -1;
        if (isLeftClound) {
            speedY = 0;
        }
        int speedX = 1;
        int width = type == 1 ? ConfigurationPresonMap.WIDTH_SMOKE_SMALL : ConfigurationPresonMap.WIDTH_SMOKE_BIG;
        int height = type == 1 ? ConfigurationPresonMap.HEIGHT_SMOKE_SMALL : ConfigurationPresonMap.HEIGHT_SMOKE_BIG;
        CloudPresonMap cloudPresonMap = new CloudPresonMap(imageViewCloud, startX, startY, speedX, speedY, width, height, presonMapActivity, GameWorldPresonMap.this, type);
        listObject.add(cloudPresonMap);
    }

    private void createCloundDown() {
        ImageView imageViewCloud = new ImageView(presonMapActivity);
        imageViewCloud.setAlpha(0.5f);
        presonMapActivity.runOnUiThread(() -> presonMapActivity.getAbsoluteLayout().addView(imageViewCloud, presonMapActivity.getAbsoluteLayout().getChildCount() - 1));
        // Lấy random
        Random random = RandomSingleton.getInstance().getRandom();
        int startY = getCamera().getY() + ConfigurationCamera.HEIGHT / 2 + random.nextInt(ConfigurationCamera.HEIGHT / 2);
        int type = random.nextInt(2);
        if (type == 0) {
            imageViewCloud.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationPresonMap.WIDTH_SMOKE_BIG, ConfigurationPresonMap.HEIGHT_SMOKE_BIG));
        } else {
            imageViewCloud.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationPresonMap.WIDTH_SMOKE_SMALL, ConfigurationPresonMap.HEIGHT_SMOKE_SMALL));
        }
        int startX = type == 1 ? getCamera().getX() - 306 : getCamera().getX() - 384;
        int speedY = -1;
        if (isLeftClound) {
            speedY = 0;
        }
        int speedX = 1;
        int width = type == 1 ? ConfigurationPresonMap.WIDTH_SMOKE_SMALL : ConfigurationPresonMap.WIDTH_SMOKE_BIG;
        int height = type == 1 ? ConfigurationPresonMap.HEIGHT_SMOKE_SMALL : ConfigurationPresonMap.HEIGHT_SMOKE_BIG;
        CloudPresonMap cloudPresonMap = new CloudPresonMap(imageViewCloud, startX, startY, speedX, speedY, width, height, presonMapActivity, GameWorldPresonMap.this, type);
        listObject.add(cloudPresonMap);
    }

    private void createBoat() {
        ImageView imageViewBoat = new ImageView(presonMapActivity);
        imageViewBoat.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationPresonMap.WIDTH_BOAT, ConfigurationPresonMap.HEIGHT_BOAT));
        presonMapActivity.runOnUiThread(() -> presonMapActivity.getAbsoluteLayout().addView(imageViewBoat, presonMapActivity.getAbsoluteLayout().getChildCount() - 1));
        BoatPresonMap boatPresonMap = new BoatPresonMap(imageViewBoat, ConfigurationPresonMap.START_BOAT_X, ConfigurationPresonMap.START_BOAT_Y, ConfigurationPresonMap.WIDTH_BOAT, ConfigurationPresonMap.HEIGHT_BOAT, presonMapActivity, GameWorldPresonMap.this);
        listObject.add(boatPresonMap);
    }

    public void update() {
        // camera
        camera.update();
        // Tạo chim
        if (!isOverCreateBird) {
            // Chạy âm thanh tiếng chim
            if (System.currentTimeMillis() - lastTimePlayMusicBird > 5000) {
                lastTimePlayMusicBird = System.currentTimeMillis();
                SourceSound.getInstance().play("gulls", ConfigurationSound.NOREPEAT);
            }
            // tạo chim
            if (lastTimeCreateBird == 0) {
                lastTimeCreateBird = System.currentTimeMillis();
            }
            // lên tới đây không tạo chim nữa
            if (camera.getY() < 1308) {
                isOverCreateBird = true;
                // Lúc này sẽ chuyển sang tạo khỏi
                isStartCreateSmoke = true;
                lastTimeCreateSmoke = System.currentTimeMillis();
            }
            // 0.15s Tạo 1 chim
            if (System.currentTimeMillis() - lastTimeCreateBird > 150) {
                lastTimeCreateBird = System.currentTimeMillis();
                ImageView imageViewBird = new ImageView(presonMapActivity);
                imageViewBird.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationPresonMap.WIDTH_BIRD, ConfigurationPresonMap.HEIGHT_BIRD));
                presonMapActivity.runOnUiThread(() -> presonMapActivity.getAbsoluteLayout().addView(imageViewBird, presonMapActivity.getAbsoluteLayout().getChildCount() - 1));
                // Lấy random
                Random random = RandomSingleton.getInstance().getRandom();
                int startY = getCamera().getY() + ConfigurationCamera.HEIGHT + 100;
                int startX = random.nextInt(1580) + 300 + getCamera().getX();
                int speedY = (random.nextInt(3) + 1) * -1;
                int speedX = random.nextInt(7) - 3;
                BirdPresonMap birdPresonMap = new BirdPresonMap(imageViewBird, startX, startY, speedX, speedY, ConfigurationPresonMap.WIDTH_BIRD, ConfigurationPresonMap.HEIGHT_BIRD, presonMapActivity, GameWorldPresonMap.this);
                listObject.add(birdPresonMap);
            }
        }
        // Tạo khói
        if (isStartCreateSmoke) {
            Random random = RandomSingleton.getInstance().getRandom();
            if (System.currentTimeMillis() - lastTimeCreateSmoke > 400 + random.nextInt(1000)) {
                lastTimeCreateSmoke = System.currentTimeMillis();
                ImageView imageViewSmoke = new ImageView(presonMapActivity);
                imageViewSmoke.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationPresonMap.WIDTH_SMOKE, ConfigurationPresonMap.HEIGHT_SMOKE));
                presonMapActivity.runOnUiThread(() -> presonMapActivity.getAbsoluteLayout().addView(imageViewSmoke, presonMapActivity.getAbsoluteLayout().getChildCount() - 1));
                // Lấy random
                int startY = random.nextInt(150) + 1146;
                int startX = random.nextInt(270) + 2442;
                SmokePresonMap smokePresonMap = new SmokePresonMap(imageViewSmoke, startX, startY, presonMapActivity, GameWorldPresonMap.this);
                listObject.add(smokePresonMap);
            }
        }
        // Tạo bong bóng
        if (isStartCreateBubble) {
            if (System.currentTimeMillis() - lastTimeCreateBubble > 1200) {
                lastTimeCreateBubble = System.currentTimeMillis();
                ImageView red = new ImageView(presonMapActivity);
                ImageView blue = new ImageView(presonMapActivity);
                ImageView yellow = new ImageView(presonMapActivity);
                red.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationPresonMap.WIDTH_BUBBLE, ConfigurationPresonMap.HEIGHT_BUBBLE));
                blue.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationPresonMap.WIDTH_BUBBLE, ConfigurationPresonMap.HEIGHT_BUBBLE));
                yellow.setLayoutParams(new ViewGroup.LayoutParams(ConfigurationPresonMap.WIDTH_BUBBLE, ConfigurationPresonMap.HEIGHT_BUBBLE));
                presonMapActivity.runOnUiThread(() -> {
                    presonMapActivity.getAbsoluteLayout().addView(red, presonMapActivity.getAbsoluteLayout().getChildCount() - 1);
                    presonMapActivity.getAbsoluteLayout().addView(blue, presonMapActivity.getAbsoluteLayout().getChildCount() - 1);
                    presonMapActivity.getAbsoluteLayout().addView(yellow, presonMapActivity.getAbsoluteLayout().getChildCount() - 1);
                });
                // Lấy random
                Random random = RandomSingleton.getInstance().getRandom();
                int startY = random.nextInt(150) + 1146;
                int startX = random.nextInt(180) + 2400;
                BubblePresonMap bRed = new BubblePresonMap(red, startX, startY, 0, -1, ConfigurationPresonMap.WIDTH_BUBBLE, ConfigurationPresonMap.HEIGHT_SMOKE, presonMapActivity, GameWorldPresonMap.this, 0);
                BubblePresonMap bBlue = new BubblePresonMap(blue, startX, startY, -1, -2, ConfigurationPresonMap.WIDTH_BUBBLE, ConfigurationPresonMap.HEIGHT_SMOKE, presonMapActivity, GameWorldPresonMap.this, 1);
                BubblePresonMap bYellow = new BubblePresonMap(yellow, startX, startY, 1, -3, ConfigurationPresonMap.WIDTH_BUBBLE, ConfigurationPresonMap.HEIGHT_SMOKE, presonMapActivity, GameWorldPresonMap.this, 2);
                listObject.add(bYellow);
                listObject.add(bBlue);
                listObject.add(bRed);
            }
        }
        // Tạo mây
        if (System.currentTimeMillis() - lastTimeCreateClound > 2000) {
            lastTimeCreateClound = System.currentTimeMillis();
            createCloundUp();
            createCloundDown();
        }
        // list Object
        int count = 0;
        while (count < listObject.size()) {
            // Xóa các chim đã xong công việc của nó
            if (isOverCreateBird && listObject.get(count).isOutCamera() && listObject.get(count) instanceof BirdPresonMap) {
                // Xóa khỏi view luôn
                listObject.get(count).outToLayout();
                listObject.remove(count);
            }
            // Xóa các khói đã hết công dụng
            else if (isStartCreateSmoke && listObject.get(count).isOutCamera() && listObject.get(count) instanceof SmokePresonMap) {
                // Xóa khỏi view luôn
                listObject.get(count).outToLayout();
                listObject.remove(count);
            }
            // Xóa các bong bóng đã hết cong dụng
            else if (isStartCreateBubble && listObject.get(count).isOutCamera() && listObject.get(count) instanceof BubblePresonMap) {
                // Xóa khỏi view luôn
                listObject.get(count).outToLayout();
                listObject.remove(count);
            }
            // Xóa các mấy đã hết công đụng
            else if (listObject.get(count).isOutCamera() && listObject.get(count) instanceof CloudPresonMap) {
                // Xóa khỏi view luôn
                listObject.get(count).outToLayout();
                listObject.remove(count);
            } else {
                listObject.get(count).update();
                count++;
            }
        }
    }

    public void draw() {
        for (Observer observer : listObject) {
            if (!observer.isOutCamera()) {
                observer.draw();
            }
        }
    }

    public CameraPresonMap getCamera() {
        return camera;
    }

    public void setStartCreateBubble(boolean startCreateBubble) {
        isStartCreateBubble = startCreateBubble;
    }

    public void setLastTimeCreateBubble(long lastTimeCreateBubble) {
        this.lastTimeCreateBubble = lastTimeCreateBubble;
    }

    public void setLeftClound(boolean leftClound) {
        isLeftClound = leftClound;
    }
}
