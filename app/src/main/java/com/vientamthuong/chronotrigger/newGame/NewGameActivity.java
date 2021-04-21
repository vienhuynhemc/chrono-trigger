package com.vientamthuong.chronotrigger.newGame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.R;
import com.vientamthuong.chronotrigger.data.SourceMain;
import com.vientamthuong.chronotrigger.data.SourceSound;
import com.vientamthuong.chronotrigger.loadData.ConfigurationSound;
import com.vientamthuong.chronotrigger.presonMap.PresonMapActivity;

public class NewGameActivity extends AppCompatActivity {
    private EditText edtName;
    private Button btnDefault;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        init();
        //Tạo tên mắc định cho nhân vật
        btnDefault.setOnClickListener(v -> {
            SourceSound.getInstance().play("cursor", ConfigurationSound.NOREPEAT);
            edtName.setText(ConfigurationNewGame.NAME_DEFAULT);
        });
        btnConfirm.setOnClickListener(v -> {
            SourceSound.getInstance().play("cursor", ConfigurationSound.NOREPEAT);
            if (edtName.getText().toString().isEmpty()) {
                Toast.makeText(NewGameActivity.this, "Vui lòng nhập tên nhân vật", Toast.LENGTH_SHORT).show();
            } else if (edtName.getText().toString().trim().length() < 5) {
                Toast.makeText(NewGameActivity.this, "Tên nhân vật phải đạt 5 ký tự trở lên", Toast.LENGTH_SHORT).show();
            } else {
                // Lấy tên
                String name = edtName.getText().toString().trim();
                // Gán cho source main
                SourceMain.getInstance().setName(name);
                Intent intent = new Intent(NewGameActivity.this, PresonMapActivity.class);
                startActivity(intent);
                // Kết thúc luôn activity của cha mẹ
                finishAffinity();
                // Kết thúc hoạt động của bản thân
                finish();
            }
        });
    }

    //Ánh xạ các view trên activity_new_game layout
    public void init() {
        edtName = findViewById(R.id.editTextNameNewGame);
        btnDefault = findViewById(R.id.buttonDefaultNewGame);
        btnConfirm = findViewById(R.id.buttonConfirmNewGame);
    }
}