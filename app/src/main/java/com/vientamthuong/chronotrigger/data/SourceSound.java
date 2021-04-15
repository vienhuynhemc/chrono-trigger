package com.vientamthuong.chronotrigger.data;

import android.media.SoundPool;

import com.vientamthuong.chronotrigger.MainActivity;
import com.vientamthuong.chronotrigger.loadData.ConfigurationSound;
import com.vientamthuong.chronotrigger.loadData.LoadSound;

import java.util.HashMap;
import java.util.Map;

public class SourceSound {

    private static SourceSound sourceSound;
    private Map<String, Integer> sounds;
    private SoundPool soundPool;

    private SourceSound() {
        sounds = new HashMap<>();
    }

    public static SourceSound getInstance() {
        if (sourceSound == null) {
            sourceSound = new SourceSound();
        }
        return sourceSound;
    }

    public void play(String name, int state) {
        if (sounds.get(name) != null) {
            soundPool.play(sounds.get(name), ConfigurationSound.LEFT_VOLUME, ConfigurationSound.RIGHT_VOLUME, 1, state, 1f);
        }
    }

    public void loadSound(MainActivity mainActivity) {
        LoadSound loadSound = new LoadSound(sourceSound);
        loadSound.loadData(mainActivity);
    }

    // GETTER AND STTER
    public SoundPool getSoundPool() {
        return soundPool;
    }

    public void setSoundPool(SoundPool soundPool) {
        this.soundPool = soundPool;
    }

    public Map<String, Integer> getSounds() {
        return sounds;
    }

    public void setSounds(Map<String, Integer> sounds) {
        this.sounds = sounds;
    }
}
