package com.vientamthuong.chronotrigger.gameEffect;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Animation {

    // Khai báo các thuộc tính
    // 1. List ảnh của animation
    private List<Bitmap> bitmaps;
    // 2. List thời gian tương ứng của animation
    private List<Long> durations;
    // 3. List xem thử có hình ảnh nào bỏ qua không
    // List này có tác dụng cho các animation như hành động chạy
    // Vì khi chạy rồi không cần lập lại động tác giơ cahan
    private List<Boolean> listIgnore;
    // 4. Xem thử animation có lặp lại khi tới hình cuối cùng không
    private boolean isRepeat;
    // 5. Hình ảnh hiện tại của animation
    private int currentBitmap;
    // 6. Thời gian cuối cùng update của animation
    private long lastTimeUpdate;

    // Constructor nhận vô các tham số thông thường
    // Constructor này được dùng khi tạo các animation đầu tiền và lưu vô danh sách lưu trữ
    public Animation(List<Bitmap> bitmaps, List<Long> durations) {
        this.bitmaps = bitmaps;
        this.durations = durations;
        // Mặc định
        // 1. Không có hình nào bị bỏ qua
        listIgnore = new ArrayList<>();
        for (int i = 0; i < bitmaps.size(); i++) {
            listIgnore.add(false);
        }
        // 2. Hình bắt đầu là 0
        currentBitmap = 0;
        // 3. Animation có lặp lại
        isRepeat = true;
        // 4. Thời gian cuối update là 0 ~ là nó mới được tạo
        lastTimeUpdate = 0;
    }

    // Constructor nhận vô một Animation để clone ra một Animation khác
    // Constructor này được dùng khi gán animation cho 1 object
    public Animation(Animation animation) {
        // clone list bitmap
        List<Bitmap> bitmaps = new ArrayList<>();
        for (Bitmap bitmap : animation.getbitmaps()) {
            // clone 1 bitmap
            Bitmap bitmapClone = bitmap.copy(bitmap.getConfig(), true);
            bitmaps.add(bitmapClone);
        }
        setbitmaps(bitmaps);
        // clone list duration
        List<Long> durations = new ArrayList<>(animation.getDurations());
        setDurations(durations);
        // clone list ignore
        List<Boolean> listIgnore = new ArrayList<>(animation.getListIgnore());
        setListIgnore(listIgnore);
        // clone bitmap hiện tịa
        setCurrentBitmap(animation.getCurrentBitmap());
        // clone thời gian update cuối cùng
        setLastTimeUpdate(animation.getLastTimeUpdate());
        // clone trạng thái có lặp lại animation hay không
        setRepeat(animation.isRepeat());
    }

    // Phương thức draw
    public void draw(View view, AppCompatActivity appCompatActivity) {
        appCompatActivity.runOnUiThread(() -> view.setBackground(new BitmapDrawable(appCompatActivity.getResources(), bitmaps.get(currentBitmap))));
    }

    // Phương thức update
    public void update() {
        // Animation mới khởi tạo thì cho thời gian update cuối cùng là hiện tại
        if (lastTimeUpdate == 0) {
            lastTimeUpdate = System.currentTimeMillis();
        }
        // Tính đã qua bao lâu kể từ thòi gian cuối update
        long timeNow = System.currentTimeMillis();
        long space = timeNow - lastTimeUpdate;
        // Nếu như đã qua thời gian cho phép của hình này thì chuyển qua hình tiếp theo
        if (space > durations.get(currentBitmap)) {
            // Chuyển hình
            nextBitmap();
            // Cập nhật lại thời gian cuối cập nhật của animation
            lastTimeUpdate = timeNow;
        }
    }

    private void nextBitmap() {
        if (currentBitmap == bitmaps.size() - 1) {
            if (isRepeat) {
                currentBitmap = 0;
            }
        } else {
            currentBitmap++;
        }
        // Check thử có bỏ qua hay không
        if (listIgnore.get(currentBitmap)) nextBitmap();
    }

    // Phương thức đảo ngược bitmap
    public void flip() {
        List<Bitmap> bitmapFlips = new ArrayList<>();
        for (Bitmap bitmap : bitmaps) {
            Matrix m = new Matrix();
            m.preScale(-1, 1);
            Bitmap dst = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);
            dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
            bitmapFlips.add(dst);
        }
        // Cập nhật lại list
        setbitmaps(bitmapFlips);
    }

    // Phuơng thức kiểm tra xem thử có phải là đang bitmap cuối cùng
    public boolean isLastBitmap() {
        return currentBitmap == (bitmaps.size() - 1);
    }

    // GETTER AND SETTER
    public List<Bitmap> getbitmaps() {
        return bitmaps;
    }

    public void setbitmaps(List<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    public List<Long> getDurations() {
        return durations;
    }

    public void setDurations(List<Long> durations) {
        this.durations = durations;
    }

    public List<Boolean> getListIgnore() {
        return listIgnore;
    }

    public void setListIgnore(List<Boolean> listIgnore) {
        this.listIgnore = listIgnore;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }

    public int getCurrentBitmap() {
        return currentBitmap;
    }

    public void setCurrentBitmap(int currentBitmap) {
        this.currentBitmap = currentBitmap;
    }

    public long getLastTimeUpdate() {
        return lastTimeUpdate;
    }

    public void setLastTimeUpdate(long lastTimeUpdate) {
        this.lastTimeUpdate = lastTimeUpdate;
    }

}
