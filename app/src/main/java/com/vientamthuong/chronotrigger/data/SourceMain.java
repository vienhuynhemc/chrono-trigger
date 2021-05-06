package com.vientamthuong.chronotrigger.data;

public class SourceMain {

    private static SourceMain sourceMain;
    // Tên nhân vật
    private String nameCrono;
    private String nameLucca;

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
}
