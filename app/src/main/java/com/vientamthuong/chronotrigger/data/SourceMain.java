package com.vientamthuong.chronotrigger.data;

public class SourceMain {

    private static SourceMain sourceMain;
    // Tên nhân vật
    private String nameCrono;
    private String nameLucca;
    // Cửa sổ mở hay tắt ở map my home
    private boolean isOpenWindown;

    private SourceMain() {
        // Mặc định là chrono để làm các activity khác
        nameCrono = "Crono";
        nameLucca = "Lucca";
        // Mặc định là cửa sổ mở để làm activity khác
        isOpenWindown =true;
    }

    public static SourceMain getInstance() {
        if (sourceMain == null) {
            sourceMain = new SourceMain();
        }
        return sourceMain;
    }

    // GETTER AND SETTER
    public String getNameCrono() {
        return nameCrono;
    }

    public void setNameCrono(String nameCrono) {
        this.nameCrono = nameCrono;
    }

    public String getNameLucca() {
        return nameLucca;
    }

    public void setNameLucca(String nameLucca) {
        this.nameLucca = nameLucca;
    }

    public boolean isOpenWindown() {
        return isOpenWindown;
    }

    public void setOpenWindown(boolean openWindown) {
        isOpenWindown = openWindown;
    }
}
