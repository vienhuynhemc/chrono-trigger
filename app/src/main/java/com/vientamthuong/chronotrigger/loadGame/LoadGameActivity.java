package com.vientamthuong.chronotrigger.loadGame;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.chronotrigger.R;
import com.vientamthuong.chronotrigger.data.SourceMain;
import com.vientamthuong.chronotrigger.data.SourceSound;
import com.vientamthuong.chronotrigger.loadData.ConfigurationSound;
import com.vientamthuong.chronotrigger.myHome.MyHomeActivity;
import com.vientamthuong.chronotrigger.myHome.MyHomeGroundActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LoadGameActivity extends AppCompatActivity {
    RecyclerView recyclerViewLoadGame;
    ArrayList<LoadGameInfo> listLoadGameInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);

        //ánh xạ
        getView();
        //Khởi tạo
        init();
    }

    public void getView() {
        recyclerViewLoadGame = findViewById(R.id.recyclerViewLoadGameActivityLoadGame);
    }

    public void init() {
        listLoadGameInfo = new ArrayList<>();
        addList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewLoadGame.setLayoutManager(linearLayoutManager);
        LoadGameAdapter loadGameAdapter = new LoadGameAdapter(listLoadGameInfo, this);
        recyclerViewLoadGame.setAdapter(loadGameAdapter);
        loadGameAdapter.setOnItemClickListener(new LoadGameAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                SourceSound.getInstance().play("cursor", ConfigurationSound.NOREPEAT);
                showDialog();
            }
        });
    }

    public void addList() {
        if (SourceMain.getInstance().getNgaySave() != 0) {
            String pattern = "HH:mm MM/dd/yyyy";
            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat(pattern);
            long space = SourceMain.getInstance().getNgaySave() - SourceMain.getInstance().getNgayBatDau();
            space=space/1000;
            int minute = (int) space / 60;
            space -= minute * 60;
            String nameMap = SourceMain.getInstance().getNameMap().equals("up") ? "My Home Up Floor" : "My Home Ground";
            listLoadGameInfo.add(new LoadGameInfo(
                    nameMap,
                    df.format(new Date(SourceMain.getInstance().getNgaySave())),
                    2000,
                    "Lv 1",
                    (minute<10?"0"+minute:minute) + ":" + (space<10?"0"+space:space),
                    true
            ));
        }
    }

    public void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Thông báo");
        dialog.setMessage("Bạn có chắc là muốn chơi tiếp từ mục lưu game này không ?");
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (SourceMain.getInstance().getNameMap().equals("up")) {
                    Intent intent = new Intent();
                    intent.setClass(LoadGameActivity.this, MyHomeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isStartIntro", SourceMain.getInstance().isStartIntroMyHomeUp());
                    bundle.putBoolean("isLoad", true);
                    intent.putExtra("data", bundle);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent();
                    intent.setClass(LoadGameActivity.this, MyHomeGroundActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isStartIntro", SourceMain.getInstance().isStartIntroMyHomeDown());
                    bundle.putBoolean("isLoad", true);
                    intent.putExtra("data", bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });
        dialog.show();
    }
}