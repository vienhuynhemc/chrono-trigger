package com.vientamthuong.chronotrigger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView ivClock;
    ImageView ivChrono;
    ImageView ivTrigger;
    TextView tvTouch;
    ConstraintLayout screenLayout;
    Button btnNewGame, btnLoadGame;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Xóa status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ivClock = findViewById(R.id.imageViewClock);
        ivChrono = findViewById(R.id.imageViewChrono);
        ivChrono.setVisibility(View.INVISIBLE);
        ivTrigger = findViewById(R.id.imageViewTrigger);
        ivTrigger.setVisibility(View.INVISIBLE);
        tvTouch = findViewById(R.id.textViewTouch);
        tvTouch.setVisibility(View.INVISIBLE);
        btnNewGame = findViewById(R.id.buttonNewGame);
        btnNewGame.setVisibility(View.INVISIBLE);
        btnLoadGame=findViewById(R.id.buttonLoadGame);
        btnLoadGame.setVisibility(View.INVISIBLE);
        screenLayout=findViewById(R.id.screenLayout);

        Animation clock1 = AnimationUtils.loadAnimation(this,R.anim.clockanimation1);
        Animation clock2  = AnimationUtils.loadAnimation(this,R.anim.clockanimation2);
        Animation clock3  = AnimationUtils.loadAnimation(this,R.anim.clockanimation3);
        Animation clock4  = AnimationUtils.loadAnimation(this,R.anim.clockanimation4);
        Animation clock5  = AnimationUtils.loadAnimation(this,R.anim.clockanimation5);



        CountDownTimer timer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                count++;
                switch (count){
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

    public void eventAfterTimer(){
        Animation fade_in_chrono  = AnimationUtils.loadAnimation(this,R.anim.fade_in_chrono);
        Animation move_left_trigger  = AnimationUtils.loadAnimation(this,R.anim.moveleft);
        Animation blink_touch  = AnimationUtils.loadAnimation(this,R.anim.blink_touch);
        ivChrono.startAnimation(fade_in_chrono);
        ivChrono.setVisibility(View.VISIBLE);
        ivTrigger.startAnimation(move_left_trigger);
        ivTrigger.setVisibility(View.VISIBLE);
        tvTouch.startAnimation(blink_touch);
        screenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTouch.clearAnimation();
                btnNewGame.setVisibility(View.VISIBLE);
                btnLoadGame.setVisibility(View.VISIBLE);
            }
        });
    }
}
