package com.vientamthuong.chronotrigger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Xóa status bar
        setContentView(R.layout.activity_main);
    }
}