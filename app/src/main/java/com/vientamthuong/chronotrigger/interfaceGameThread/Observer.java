package com.vientamthuong.chronotrigger.interfaceGameThread;

public interface Observer {

    // update
    void update();

    // vẽ
    void draw();

    // kiểm tra out camera
    boolean isOutCamera();

    // Xóa khỏi layout
    void outToLayout();

}
