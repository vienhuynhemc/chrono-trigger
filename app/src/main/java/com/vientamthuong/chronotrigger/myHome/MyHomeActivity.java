package com.vientamthuong.chronotrigger.myHome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.R;
import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.data.SourceSound;

public class MyHomeActivity extends AppCompatActivity {

    // Khai báo các thuộc tính
    // 1. view
    private ImageView ivFullScreen;
    private ImageView ivBackgroundMap;
    private TextView tvShowTextTren;
    private TextView tvShowTextDuoi;
    private AbsoluteLayout absoluteLayout;
    // 2. Luồng game
    private GameThreadMyHome gameThreadMyHome;
    ///************** Xủ lý vuốt 2 lần thoát game *****************////////////
    private long lastTimeTouchBack;
    ///*************************************************************///////////

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Xóa stastus bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.actitvity_my_home);
        // Ánh xạ view
        getView();
        // Khởi tạo
        init();
        // Chạy
        run();
    }

    private void init() {
//        // -----------------Đoạn này là đọan ráp vô code gốc -------------------
//        // Lấy thuộc tính xem thử có chạy intro
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        boolean isStartIntro = bundle.getBoolean("isStartIntro");
        boolean isLoad = bundle.getBoolean("isLoad");
        gameThreadMyHome = new GameThreadMyHome(MyHomeActivity.this, isStartIntro, isLoad);
        gameThreadMyHome.setRunning(true);
//        //----------------------------------------------------------------------
        // load Sound
//        SourceSound.getInstance().loadSound(MyHomeActivity.this);
//        // Load animation
//        SourceAnimation.getInstance().loadAnimation(MyHomeActivity.this);
//        gameThreadMyHome = new GameThreadMyHome(MyHomeActivity.this, false);
//        gameThreadMyHome.setRunning(true);
    }

    private void run() {
        gameThreadMyHome.start();
    }

    @Override
    public void onBackPressed() {
        if (lastTimeTouchBack == 0 || System.currentTimeMillis() - lastTimeTouchBack > 2000) {
            lastTimeTouchBack = System.currentTimeMillis();
            Toast.makeText(MyHomeActivity.this, "Nhấn một lần nữa để thoát", Toast.LENGTH_SHORT).show();
        } else {
            gameThreadMyHome.getGameWorldMyHome().saveData();
            finishAffinity();
        }
    }

    private void getView() {
        ivFullScreen = findViewById(R.id.activity_my_home_imageView_fullScreen);
        ivBackgroundMap = findViewById(R.id.activity_my_home_backgroundMap);
        tvShowTextTren = findViewById(R.id.activity_my_home_noi_dung_doan_chat_tren);
        tvShowTextDuoi = findViewById(R.id.activity_my_home_noi_dung_doan_chat_duoi);
        absoluteLayout = findViewById(R.id.activity_my_home_layout);
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

    public TextView getTvShowTextTren() {
        return tvShowTextTren;
    }

    public TextView getTvShowTextDuoi() {
        return tvShowTextDuoi;
    }
}
