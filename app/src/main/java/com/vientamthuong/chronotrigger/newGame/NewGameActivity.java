package com.vientamthuong.chronotrigger.newGame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vientamthuong.chronotrigger.MainActivity;
import com.vientamthuong.chronotrigger.R;
import com.vientamthuong.chronotrigger.data.SourceSound;
import com.vientamthuong.chronotrigger.loadData.ConfigurationSound;
import com.vientamthuong.chronotrigger.presonMap.PresonMapActivity;

public class NewGameActivity extends AppCompatActivity {
    EditText edtName;
    Button btnDefault;
    Button btnConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        init();
        //Tạo tên mắc định cho nhân vật
        btnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SourceSound.getInstance().play("cursor", ConfigurationSound.NOREPEAT);
                edtName.setText("Crono");
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SourceSound.getInstance().play("cursor", ConfigurationSound.NOREPEAT);
                if (edtName.getText().toString().isEmpty()){
                    Toast.makeText(NewGameActivity.this,"Vui lòng nhập tên nhân vật",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(NewGameActivity.this, PresonMapActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    //Ánh xạ các view trên activity_new_game layout
    public void init(){
        edtName = findViewById(R.id.editTextNameNewGame);
        btnDefault = findViewById(R.id.buttonDefaultNewGame);
        btnConfirm = findViewById(R.id.buttonConfirmNewGame);
    }
}