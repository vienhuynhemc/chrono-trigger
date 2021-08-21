package com.vientamthuong.chronotrigger.newGame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.R;
import com.vientamthuong.chronotrigger.data.SourceMain;
import com.vientamthuong.chronotrigger.data.SourceSound;
import com.vientamthuong.chronotrigger.loadData.ConfigurationSound;
import com.vientamthuong.chronotrigger.myHome.MyHomeActivity;
import com.vientamthuong.chronotrigger.presonMap.PresonMapActivity;

public class NewGameActivity extends AppCompatActivity {
    private static boolean isFinish;
    private EditText edtName;
    private Button btnDefault;
    private Button btnConfirm;
    private ImageView ivAvartar;
    private String nameDefault;
    private boolean isStartIntro;
    ///************** Xủ lý vuốt 2 lần thoát game *****************////////////
    private long lastTimeTouchBack;
    ///*************************************************************///////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        init();
        //Tạo tên mắc định cho nhân vật
        btnDefault.setOnClickListener(v -> {
            SourceSound.getInstance().play("cursor", ConfigurationSound.NOREPEAT);
            edtName.setText(nameDefault);
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
                switch (nameDefault) {
                    case "Crono":
                        SourceMain.getInstance().setNameCrono(name);
                    case "Lucca":
                        SourceMain.getInstance().setNameLucca(name);
                }
                if (isStartIntro) {
                    // Mới chơi thì cho cửa sổ là false , chạy intro là true hết
                    SourceMain.getInstance().actionNewGame();
                    Intent intent = new Intent(NewGameActivity.this, PresonMapActivity.class);
                    startActivity(intent);
                    // Kết thúc luôn activity của cha mẹ
                    finishAffinity();
                }

                // Kết thúc hoạt động của bản thân
                isFinish = true;
                finish();
            }
        });
    }

    //Ánh xạ các view trên activity_new_game layout
    public void init() {
        isFinish=false;
        edtName = findViewById(R.id.editTextNameNewGame);
        btnDefault = findViewById(R.id.buttonDefaultNewGame);
        btnConfirm = findViewById(R.id.buttonConfirmNewGame);
        ivAvartar = findViewById(R.id.imageViewAvatarNewGame);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        isStartIntro = intent.getBooleanExtra("isStartIntro", false);
        switch (name) {
            case "crono":
                nameDefault = ConfigurationNewGame.NAME_DEFAULT_CRONO;
                ivAvartar.setImageResource(R.drawable.chrono_avatar);
                break;
            case "lucca":
                nameDefault = ConfigurationNewGame.NAME_DEFAULT_LUCCA;
                ivAvartar.setImageResource(R.drawable.lucca_avartar);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        if (lastTimeTouchBack == 0 || System.currentTimeMillis() - lastTimeTouchBack > 2000) {
            lastTimeTouchBack = System.currentTimeMillis();
            Toast.makeText(NewGameActivity.this, "Nhấn một lần nữa để thoát", Toast.LENGTH_SHORT).show();
        } else {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
    public boolean isStartIntro() {
        return isStartIntro;
    }

    public void setStartIntro(boolean startIntro) {
        isStartIntro = startIntro;
    }

    public static boolean isFinish() {
        return isFinish;
    }
}