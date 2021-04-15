package com.vientamthuong.chronotrigger.data;

import android.media.MediaPlayer;
import android.media.SoundPool;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.MainActivity;
import com.vientamthuong.chronotrigger.loadData.ConfigurationSound;
import com.vientamthuong.chronotrigger.loadData.LoadSound;

import java.util.HashMap;
import java.util.Map;

public class SourceSound {

    // Khai báo các thuộc tính
    // Instance
    private static SourceSound sourceSound;
    // Lưu trữ các id nhạc đã được load lên từ sound pool
    private Map<String, Integer> sounds;
    // Lưu trữ các id nhạc trong resource
    private Map<String, Integer> soundIds;
    // Sound pool - Trung tâm phát nhạc effect
    private SoundPool soundPool;
    // Media - Nơi phát nhạc nền
    private MediaPlayer mediaPlayerBackgroundSound;
    // biến để quyết định game có nhạc hay là không
    private boolean isPlayMusic;

    private SourceSound() {
        sounds = new HashMap<>();
        soundIds = new HashMap<>();
        // mặc định là phát nhạc
        isPlayMusic = true;
    }

    public static SourceSound getInstance() {
        if (sourceSound == null) {
            sourceSound = new SourceSound();
        }
        return sourceSound;
    }

    // Hàm chạy nhạc
    public void play(String name, int state) {
        if (isPlayMusic && sounds.get(name) != null) {
            switch (state) {
                case ConfigurationSound.REPEAT:
                    soundPool.play(sounds.get(name), ConfigurationSound.LEFT_VOLUME, ConfigurationSound.RIGHT_VOLUME, 1, -1, 1f);
                    break;
                case ConfigurationSound.NOREPEAT:
                    soundPool.play(sounds.get(name), ConfigurationSound.LEFT_VOLUME, ConfigurationSound.RIGHT_VOLUME, 1, 0, 1f);
                    break;
            }
        }
    }

    // Nhận vô context để load resource
    public void loadSound(MainActivity mainActivity) {
        // Khởi tạo class load data
        LoadSound loadSound = new LoadSound(sourceSound);
        loadSound.loadData(mainActivity);
        // Khởi tạo và chạy nhạc nền mặc định
        mediaPlayerBackgroundSound = MediaPlayer.create(mainActivity, soundIds.get("a_premonition"));
        mediaPlayerBackgroundSound.start();
    }

    // Phương thức chạy nhạc nền
    public void playSoundBackground(String name, AppCompatActivity appCompatActivity) {
        mediaPlayerBackgroundSound.stop();
        mediaPlayerBackgroundSound.release();
        mediaPlayerBackgroundSound = MediaPlayer.create(appCompatActivity, soundIds.get(name));
        mediaPlayerBackgroundSound.setLooping(true);
        mediaPlayerBackgroundSound.start();
    }

    // GETTER AND STTER
    public void setSounds(Map<String, Integer> sounds) {
        this.sounds = sounds;
    }

    public Map<String, Integer> getSounds() {
        return sounds;
    }

    public SoundPool getSoundPool() {
        return soundPool;
    }

    public void setSoundPool(SoundPool soundPool) {
        this.soundPool = soundPool;
    }

    public boolean isPlayMusic() {
        return isPlayMusic;
    }

    public void setPlayMusic(boolean playMusic) {
        isPlayMusic = playMusic;
    }

    public Map<String, Integer> getSoundIds() {
        return soundIds;
    }

    public void setSoundIds(Map<String, Integer> soundIds) {
        this.soundIds = soundIds;
    }
}
