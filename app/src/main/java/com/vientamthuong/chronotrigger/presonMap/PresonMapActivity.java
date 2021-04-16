package com.vientamthuong.chronotrigger.presonMap;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.R;
import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.data.SourceSound;

public class PresonMapActivity extends AppCompatActivity {

    // Khai báo các thuộc tính
    // 1. view
    private ImageView ivFullScreen;
    private ImageView ivBackgroundMap;
    private AbsoluteLayout absoluteLayout;
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
        ivFullScreen.setOnClickListener(v -> System.out.println("Adasds"));
    }

    private void run() {
        gameThreadPresonMap.start();
    }

    private void init() {
        // load Sound
        SourceSound.getInstance().loadSound(PresonMapActivity.this);
        // Load animation
        SourceAnimation.getInstance().loadAnimation(PresonMapActivity.this);
        gameThreadPresonMap = new GameThreadPresonMap(PresonMapActivity.this);
        gameThreadPresonMap.setRunning(true);
    }

    private void getView() {
        ivFullScreen = findViewById(R.id.activity_preson_map_imageView_fullScreen);
        ivBackgroundMap = findViewById(R.id.activity_preson_map_imageView_background);
        absoluteLayout = findViewById(R.id.preson_map_layout);
    }

    // Getter and setter
    public ImageView getIvFullScreen() {
        return ivFullScreen;
    }

    public ImageView getIvBackgroundMap() {
        return ivBackgroundMap;
    }

    public AbsoluteLayout getAbsoluteLayout() {
        return absoluteLayout;
    }

}
