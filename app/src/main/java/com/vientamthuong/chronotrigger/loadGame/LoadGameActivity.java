package com.vientamthuong.chronotrigger.loadGame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;

import com.vientamthuong.chronotrigger.R;
import com.vientamthuong.chronotrigger.data.SourceSound;
import com.vientamthuong.chronotrigger.loadData.ConfigurationSound;

import java.util.ArrayList;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewLoadGame.setLayoutManager(linearLayoutManager);
        LoadGameAdapter loadGameAdapter = new LoadGameAdapter(listLoadGameInfo,this);
        recyclerViewLoadGame.setAdapter(loadGameAdapter);
        loadGameAdapter.setOnItemClickListener(new LoadGameAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                SourceSound.getInstance().play("cursor", ConfigurationSound.NOREPEAT);
                showDialog();
            }
        });
    }
    public void addList(){
        listLoadGameInfo.add(new LoadGameInfo("My house","20:00 20/08/2021",
                10000,"Lv.1","5:00",true));
        listLoadGameInfo.add(new LoadGameInfo("My house","21:00 20/08/2021",
                10000,"Lv.2","5:00",false));
        listLoadGameInfo.add(new LoadGameInfo("My house","22:00 20/08/2021",
                10000,"Lv.3","5:00",false));
        listLoadGameInfo.add(new LoadGameInfo("My house","23:00 20/08/2021",
                10000,"Lv.4","5:00",false));
        listLoadGameInfo.add(new LoadGameInfo("My house","24:00 20/08/2021",
                10000,"Lv.5","5:00",false));
    }
    public void showDialog(){
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

            }
        });
        dialog.show();
    }
}