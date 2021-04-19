package com.vientamthuong.chronotrigger.myHome;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.R;
import com.vientamthuong.chronotrigger.presonMap.PresonMapActivity;

public class MyHomeActivity extends AppCompatActivity {

    ///************** Xủ lý vuốt 2 lần thoát game *****************////////////
    private long lastTimeTouchBack;
    ///*************************************************************///////////

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Xóa stastus bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.actitvity_my_home);
    }

    @Override
    public void onBackPressed() {
        if (lastTimeTouchBack == 0 || System.currentTimeMillis() - lastTimeTouchBack > 2000) {
            lastTimeTouchBack = System.currentTimeMillis();
            Toast.makeText(MyHomeActivity.this, "Nhấn một lần nữa để thoát", Toast.LENGTH_SHORT).show();
        } else {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

}
