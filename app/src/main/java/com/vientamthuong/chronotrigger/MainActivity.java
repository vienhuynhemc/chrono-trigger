package com.vientamthuong.chronotrigger;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.vientamthuong.chronotrigger.data.SourceAnimation;
import com.vientamthuong.chronotrigger.data.SourceSound;
import com.vientamthuong.chronotrigger.loadData.ConfigurationSound;
import com.vientamthuong.chronotrigger.newGame.NewGameActivity;
import com.vientamthuong.chronotrigger.presonMap.PresonMapActivity;

public class MainActivity extends AppCompatActivity {

    // Khai báo các thuộc tính
    // 1. View
    private ImageView ivClock;
    private ImageView ivChrono;
    private ImageView ivTrigger;
    private TextView tvTouch;
    private ConstraintLayout screenLayout;
    private Button btnNewGame;
    private Button btnLoadGame;
    // 2. Tham số để chạy lần lượt các Animation của đồng hồ
    private int count;
    ///************** Xủ lý vuốt 2 lần thoát game *****************////////////
    private long lastTimeTouchBack;
    ///*************************************************************///////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Xóa status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        // Ánh xạ view
        getView();
        // Khởi tạo
        init();
        // action
        action();
    }

    @Override
    public void onBackPressed() {
        if (lastTimeTouchBack == 0 || System.currentTimeMillis() - lastTimeTouchBack > 2000) {
            lastTimeTouchBack = System.currentTimeMillis();
            Toast.makeText(MainActivity.this, "Nhấn một lần nữa để thoát", Toast.LENGTH_SHORT).show();
        } else {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    private void action() {
        // action new game
        btnNewGame.setOnClickListener(v -> {
            // âm thành cursor
            SourceSound.getInstance().play("cursor", ConfigurationSound.NOREPEAT);
            // Chuyển qua activity preson map
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, NewGameActivity.class);
            startActivity(intent);
            SourceSound.getInstance().stopBackgroundSound();
            finish();
        });
    }

    private void init() {
        // load Sound
        SourceSound.getInstance().loadSound(MainActivity.this);
        // Chạy nhạc nền mặc định
        SourceSound.getInstance().runDefaultBackgroundSound(MainActivity.this);
        // Load animation
        SourceAnimation.getInstance().loadAnimation(MainActivity.this);
        // Khởi tạo đồng hồ
        clock();
    }

    private void clock() {
        // Khởi tạo animation
        Animation clock1 = AnimationUtils.loadAnimation(this, R.anim.clockanimation1);
        Animation clock2 = AnimationUtils.loadAnimation(this, R.anim.clockanimation2);
        Animation clock3 = AnimationUtils.loadAnimation(this, R.anim.clockanimation3);
        Animation clock4 = AnimationUtils.loadAnimation(this, R.anim.clockanimation4);
        Animation clock5 = AnimationUtils.loadAnimation(this, R.anim.clockanimation5);
        // Chạy quả lắc
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                count++;
                switch (count) {
                    case 1:
                        ivClock.startAnimation(clock1);
                        break;
                    case 2:
                        ivClock.startAnimation(clock2);
                        break;
                    case 3:
                        ivClock.startAnimation(clock3);
                        break;
                    case 4:
                        ivClock.startAnimation(clock4);
                        break;
                    case 5:
                        ivClock.startAnimation(clock5);
                        break;
                }
            }

            @Override
            public void onFinish() {
                eventAfterTimer();
            }
        }.start();
    }

    private void getView() {
        ivClock = findViewById(R.id.imageViewClock);
        ivChrono = findViewById(R.id.imageViewChrono);
        ivChrono.setVisibility(View.INVISIBLE);
        ivTrigger = findViewById(R.id.imageViewTrigger);
        ivTrigger.setVisibility(View.INVISIBLE);
        tvTouch = findViewById(R.id.textViewTouch);
        tvTouch.setVisibility(View.INVISIBLE);
        btnNewGame = findViewById(R.id.buttonNewGame);
        btnNewGame.setVisibility(View.INVISIBLE);
        btnLoadGame = findViewById(R.id.buttonLoadGame);
        btnLoadGame.setVisibility(View.INVISIBLE);
        screenLayout = findViewById(R.id.screenLayout);
    }

    public void eventAfterTimer() {
        // Tạo animation
        Animation fade_in_chrono = AnimationUtils.loadAnimation(this, R.anim.fade_in_chrono);
        Animation move_left_trigger = AnimationUtils.loadAnimation(this, R.anim.moveleft);
        Animation blink_touch = AnimationUtils.loadAnimation(this, R.anim.blink_touch);
        // Chạy hiệu ứng
        ivChrono.startAnimation(fade_in_chrono);
        ivChrono.setVisibility(View.VISIBLE);
        ivTrigger.startAnimation(move_left_trigger);
        ivTrigger.setVisibility(View.VISIBLE);
        tvTouch.startAnimation(blink_touch);
        // Action khi chạm để băt đầu
        screenLayout.setOnClickListener(v -> {
            tvTouch.clearAnimation();
            btnNewGame.setVisibility(View.VISIBLE);
            btnLoadGame.setVisibility(View.VISIBLE);
        });
    }
}
