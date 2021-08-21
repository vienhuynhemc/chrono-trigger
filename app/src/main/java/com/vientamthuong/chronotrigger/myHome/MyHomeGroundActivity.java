package com.vientamthuong.chronotrigger.myHome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vientamthuong.chronotrigger.R;
import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.data.SourceSound;

public class MyHomeGroundActivity extends AppCompatActivity {
    // Khai báo các thuộc tính
    // 1. view
    private ImageView ivFullScreenGround;
    private ImageView ivBackgroundMapGround;
    private TextView tvShowTextTren;
    private TextView tvShowTextDuoi;
    private AbsoluteLayout absoluteLayout;
    //Luồng game
    private GameThreadMyHomeGround gameThreadMyHomeGround;
    ///************** Xủ lý vuốt 2 lần thoát game *****************////////////
    private long lastTimeTouchBack;
    ///*************************************************************///////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_home_ground);

        getView();

        init();

        run();
    }

    //Ánh xạ các view
    public void getView() {
        ivFullScreenGround = findViewById(R.id.activity_my_home_ground_imageView_fullScreen);
        ivBackgroundMapGround = findViewById(R.id.activity_my_home_ground_backgroundMap);
        tvShowTextTren = findViewById(R.id.activity_my_home_ground_noi_dung_doan_chat_tren);
        tvShowTextDuoi = findViewById(R.id.activity_my_home_ground_noi_dung_doan_chat_duoi);
        absoluteLayout = findViewById(R.id.activity_my_home_ground_layout);
    }

    private void init() {
        // -----------------Đoạn này là đọan ráp vô code gốc -------------------
        // Lấy thuộc tính xem thử có chạy intro
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        boolean isStartIntro = bundle.getBoolean("isStartIntro");
        boolean isLoad = bundle.getBoolean("isLoad");
        gameThreadMyHomeGround = new GameThreadMyHomeGround(MyHomeGroundActivity.this, isStartIntro, isLoad);
        gameThreadMyHomeGround.setRunning(true);
        //----------------------------------------------------------------------
//         load Sound
//        SourceSound.getInstance().loadSound(MyHomeGroundActivity.this);
        // Load animation
//        SourceAnimation.getInstance().loadAnimation(MyHomeGroundActivity.this);
//        gameThreadMyHomeGround = new GameThreadMyHomeGround(MyHomeGroundActivity.this, false);
//        gameThreadMyHomeGround.setRunning(true);
    }

    private void run() {
        gameThreadMyHomeGround.start();
    }

    @Override
    public void onBackPressed() {
        if (lastTimeTouchBack == 0 || System.currentTimeMillis() - lastTimeTouchBack > 2000) {
            lastTimeTouchBack = System.currentTimeMillis();
            Toast.makeText(MyHomeGroundActivity.this, "Nhấn một lần nữa để thoát", Toast.LENGTH_SHORT).show();
        } else {
            gameThreadMyHomeGround.getGameWorldMyHomeGround().saveData();
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
    //getter

    public ImageView getIvFullScreenGround() {
        return ivFullScreenGround;
    }

    public ImageView getIvBackgroundMapGround() {
        return ivBackgroundMapGround;
    }

    public TextView getTvShowTextTren() {
        return tvShowTextTren;
    }

    public TextView getTvShowTextDuoi() {
        return tvShowTextDuoi;
    }

    public AbsoluteLayout getAbsoluteLayout() {
        return absoluteLayout;
    }
}