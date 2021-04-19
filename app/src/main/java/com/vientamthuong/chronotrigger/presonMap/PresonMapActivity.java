package com.vientamthuong.chronotrigger.presonMap;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.R;

public class PresonMapActivity extends AppCompatActivity {

    // Khai báo các thuộc tính
    // 1. view
    private ImageView ivFullScreen;
    private ImageView ivBackgroundMap;
    private AbsoluteLayout absoluteLayout;
    // 2. Luồng game
    private GameThreadPresonMap gameThreadPresonMap;
    ///************** Xủ lý vuốt 2 lần thoát game *****************////////////
    private long lastTimeTouchBack;
    ///*************************************************************///////////

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
        // Chạy
        run();
    }

    @Override
    public void onBackPressed() {
        if (lastTimeTouchBack == 0 || System.currentTimeMillis() - lastTimeTouchBack > 2000) {
            lastTimeTouchBack = System.currentTimeMillis();
            Toast.makeText(PresonMapActivity.this, "Nhấn một lần nữa để thoát", Toast.LENGTH_SHORT).show();
        } else {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
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
