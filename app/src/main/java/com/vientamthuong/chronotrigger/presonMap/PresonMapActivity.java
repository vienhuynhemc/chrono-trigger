package com.vientamthuong.chronotrigger.presonMap;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.R;

public class PresonMapActivity extends AppCompatActivity {

    // Khai báo các thuộc tính
    // 1. view
    private ImageView ivFullScreen;
    // 2. Luồng game
    private GameThreadPresonMap gameThreadPresonMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Xóa status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_preson_map);
        // Ánh xạ view
        getView();
        // Khởi tạo
        init();
        // action
        action();
        // Chạy
        run();
    }

    private void action() {
        ivFullScreen.setOnClickListener(v -> {
            if (gameThreadPresonMap.isRunning()) {
                gameThreadPresonMap.setRunning(false);
            } else if (!gameThreadPresonMap.isRunning()) {
                gameThreadPresonMap.setRunning(true);
            }
        });
    }

    private void run() {
        gameThreadPresonMap.start();
    }

    private void init() {
        gameThreadPresonMap = new GameThreadPresonMap(PresonMapActivity.this);
        gameThreadPresonMap.setRunning(true);
    }

    private void getView() {
        ivFullScreen = findViewById(R.id.activity_preson_map_imageView_fullScreen);
    }

    // Getter and setter
    public ImageView getIvFullScreen() {
        return ivFullScreen;
    }

    public void setIvFullScreen(ImageView ivFullScreen) {
        this.ivFullScreen = ivFullScreen;
    }
}
