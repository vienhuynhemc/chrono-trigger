package com.vientamthuong.chronotrigger.myHome;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.R;

import java.util.List;

public class ShowTextMyHome {

    private TextView textView;
    private final AppCompatActivity appCompatActivity;
    // List đoạn text cần chạy hết
    private List<String> content;
    private String nowContent;
    private int nowLength;
    // Trạng thái sau khi đoạn text chạy xong
    private boolean isCompleteText;
    // Trạng thái hoàn thành tất cả
    private boolean isComplete;
    // Khung thời gian
    private long lastTimeShowCharacter;
    private long lastTimeCompleteText;
    // Animation
    private Animation hidden;
    private Animation show;

    public ShowTextMyHome(TextView textView, AppCompatActivity appCompatActivity) {
        this.textView = textView;
        this.appCompatActivity = appCompatActivity;
        isComplete = true;
        init();
    }

    public void show() {
        appCompatActivity.runOnUiThread(() -> {
            textView.clearAnimation();
            textView.setVisibility(View.VISIBLE);
            textView.startAnimation(show);
        });
    }

    public void hidden() {
        appCompatActivity.runOnUiThread(() -> {
            textView.clearAnimation();
            textView.setVisibility(View.GONE);
            textView.startAnimation(hidden);
        });
    }

    public void update() {
        // Show 1 ký tự
        if (!isComplete) {
            if (!isCompleteText) {
                if (System.currentTimeMillis() - lastTimeShowCharacter > 30) {
                    lastTimeShowCharacter = System.currentTimeMillis();
                    nowLength++;
                    // Show đủ thì bắt chờ 2s rồi mới tiếp tục làm việc gì đó
                    if (nowLength == content.get(0).length()) {
                        isCompleteText = true;
                        nowLength = 0;
                        lastTimeCompleteText = System.currentTimeMillis();
                        content.remove(0);
                    } else {
                        // Không thì tăng lên
                        nowContent = content.get(0).substring(0, nowLength + 1);
                        appCompatActivity.runOnUiThread(() -> textView.setText(nowContent));
                    }
                }
            } else {
                // Chờ 1 giây
                if (System.currentTimeMillis() - lastTimeCompleteText > 1000) {
                    // Hết rồi thì thông báo là hoàn thành
                    if (content.size() == 0) {
                        isComplete = true;
                    } else {
                        isCompleteText = false;
                    }
                }
            }
        }
    }

    public void draw() {
    }

    private void init() {
        hidden = AnimationUtils.loadAnimation(appCompatActivity, R.anim.activity_my_home_box_chat_hidden);
        show = AnimationUtils.loadAnimation(appCompatActivity, R.anim.activity_my_home_box_chat_show);
    }

    // GETTER AND SETTER
    public void setContent(List<String> content) {
        this.content = content;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

}
