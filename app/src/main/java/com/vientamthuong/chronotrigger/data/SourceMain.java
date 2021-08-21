package com.vientamthuong.chronotrigger.data;

import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.MainActivity;
import com.vientamthuong.chronotrigger.R;
import com.vientamthuong.chronotrigger.dao.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

public class SourceMain {

    private static SourceMain sourceMain;
    // Tên nhân vật
    private String nameCrono;
    private String nameLucca;
    // dao
    private DAO dao;
    // Cửa sổ mở hay tắt ở map my home
    private boolean isOpenWindown;
    private boolean isStartIntroMyHomeUp;
    private boolean isStartIntroMyHomeDown;
    private int x;
    private int y;
    private int dir;
    private String nameMap;
    private long ngayBatDau;
    private long ngaySave;

    private SourceMain() {
        // Mặc định là chrono để làm các activity khác
        nameCrono = "Crono";
        nameLucca = "Lucca";
    }

    public static SourceMain getInstance() {
        if (sourceMain == null) {
            sourceMain = new SourceMain();
        }
        return sourceMain;
    }

    public void createDAO(AppCompatActivity appCompatActivity) {
        dao = new DAO(appCompatActivity, "database.sqlite", null, 1);
        loadData();
    }

    private void loadData() {
        Cursor cursor = dao.getData("SELECT * FROM thong_tin");
        int count = 0;
        while (cursor.moveToNext()) {
            String ten = cursor.getString(0);
            String gia_tri = cursor.getString(1);
            switch (ten) {
                case "window":
                    isOpenWindown = !gia_tri.equals("false");
                    break;
                case "intro_up":
                    isStartIntroMyHomeUp = !gia_tri.equals("false");
                    break;
                case "intro_down":
                    isStartIntroMyHomeDown = !gia_tri.equals("false");
                    break;
                case "crono":
                    nameCrono = gia_tri;
                    break;
                case "lucca":
                    nameLucca = gia_tri;
                    break;
                case "x":
                    x = Integer.parseInt(gia_tri);
                    break;
                case "y":
                    y = Integer.parseInt(gia_tri);
                    break;
                case "dir":
                    dir = Integer.parseInt(gia_tri);
                    break;
                case "ngay_bat_dau":
                    ngayBatDau = Long.parseLong(gia_tri);
                    break;
                case "ngay_luu":
                    ngaySave = Long.parseLong(gia_tri);
                    break;
                case "ten_map":
                    nameMap = gia_tri;
                    break;
            }
            count++;
        }
        cursor.close();
        if (count == 0) {
            dao.updateData("INSERT INTO thong_tin VALUES('window','false')");
            dao.updateData("INSERT INTO thong_tin VALUES('intro_up','false')");
            dao.updateData("INSERT INTO thong_tin VALUES('intro_down','false')");
            dao.updateData("INSERT INTO thong_tin VALUES('crono','Crono')");
            dao.updateData("INSERT INTO thong_tin VALUES('lucca','Lucca')");
            dao.updateData("INSERT INTO thong_tin VALUES('x','0')");
            dao.updateData("INSERT INTO thong_tin VALUES('y','0')");
            dao.updateData("INSERT INTO thong_tin VALUES('dir','0')");
            dao.updateData("INSERT INTO thong_tin VALUES('ngay_bat_dau','0')");
            dao.updateData("INSERT INTO thong_tin VALUES('ngay_luu','0')");
            dao.updateData("INSERT INTO thong_tin VALUES('ten_map','')");
            this.loadData();
        }
    }

    public void actionNewGame() {
        setOpenWindown(false);
        setStartIntroMyHomeDown(true);
        setStartIntroMyHomeUp(true);
        // Xoá save = cách cho ngày save = 0
        setNgaySave(0);
        setNgayBatDau(new Date().getTime());
    }

    // GETTER AND SETTER
    public String getNameCrono() {
        return nameCrono;
    }

    public void setNameCrono(String nameCrono) {
        this.nameCrono = nameCrono;
        dao.updateData("UPDATE thong_tin SET gia_tri = '" + nameCrono + "' WHERE ten = 'crono' ");
    }

    public String getNameLucca() {
        return nameLucca;
    }

    public void setNameLucca(String nameLucca) {
        this.nameLucca = nameLucca;
        dao.updateData("UPDATE thong_tin SET gia_tri = '" + nameLucca + "' WHERE ten = 'lucca' ");
    }

    public boolean isOpenWindown() {
        return isOpenWindown;
    }

    public void setOpenWindown(boolean openWindown) {
        isOpenWindown = openWindown;
        String result = openWindown ? "true" : "false";
        dao.updateData("UPDATE thong_tin SET gia_tri = '" + result + "' WHERE ten = 'window' ");
    }

    public boolean isStartIntroMyHomeUp() {
        return isStartIntroMyHomeUp;
    }

    public void setStartIntroMyHomeUp(boolean startIntroMyHomeUp) {
        isStartIntroMyHomeUp = startIntroMyHomeUp;
        String result = startIntroMyHomeUp ? "true" : "false";
        dao.updateData("UPDATE thong_tin SET gia_tri = '" + result + "' WHERE ten = 'intro_up' ");
    }

    public boolean isStartIntroMyHomeDown() {
        return isStartIntroMyHomeDown;
    }

    public void setStartIntroMyHomeDown(boolean startIntroMyHomeDown) {
        isStartIntroMyHomeDown = startIntroMyHomeDown;
        String result = startIntroMyHomeDown ? "true" : "false";
        dao.updateData("UPDATE thong_tin SET gia_tri = '" + result + "' WHERE ten = 'intro_down' ");
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        String result = x + "";
        dao.updateData("UPDATE thong_tin SET gia_tri = '" + result + "' WHERE ten = 'x' ");
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        String result = y + "";
        dao.updateData("UPDATE thong_tin SET gia_tri = '" + result + "' WHERE ten = 'y' ");
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
        String result = dir + "";
        dao.updateData("UPDATE thong_tin SET gia_tri = '" + result + "' WHERE ten = 'dir' ");
    }

    public String getNameMap() {
        return nameMap;
    }

    public void setNameMap(String nameMap) {
        this.nameMap = nameMap;
        String result = nameMap + "";
        dao.updateData("UPDATE thong_tin SET gia_tri = '" + result + "' WHERE ten = 'ten_map' ");
    }

    public long getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(long ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
        String result = ngayBatDau + "";
        dao.updateData("UPDATE thong_tin SET gia_tri = '" + result + "' WHERE ten = 'ngay_bat_dau' ");
    }

    public long getNgaySave() {
        return ngaySave;
    }

    public void setNgaySave(long ngaySave) {
        this.ngaySave = ngaySave;
        String result = ngaySave + "";
        dao.updateData("UPDATE thong_tin SET gia_tri = '" + result + "' WHERE ten = 'ngay_luu' ");
    }
}
