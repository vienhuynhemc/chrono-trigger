package com.vientamthuong.chronotrigger.data;

public class SourceMain {

    private static SourceMain sourceMain;
    // Tên nhân vật
    private String name;

    private SourceMain() {
        name = "Chrono";
    }

    public static SourceMain getInstance() {
        if (sourceMain == null) {
            sourceMain = new SourceMain();
        }
        return sourceMain;
    }

    // GETTER AND SETTER
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
