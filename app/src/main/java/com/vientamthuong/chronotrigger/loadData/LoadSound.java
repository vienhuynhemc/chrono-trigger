package com.vientamthuong.chronotrigger.loadData;

import android.media.AudioAttributes;
import android.media.SoundPool;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.R;
import com.vientamthuong.chronotrigger.data.SourceSound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoadSound {

    private final SourceSound soundSound;

    public LoadSound(SourceSound sourceSound) {
        this.soundSound = sourceSound;
        init();
    }

    private void init() {
        AudioAttributes audioAttrib = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setAudioAttributes(audioAttrib).setMaxStreams(ConfigurationSound.MAX_STREAMS);
        soundSound.setSoundPool(builder.build());
    }

    public void loadData(AppCompatActivity appCompatActivity) {
        try {
            InputStream inputStream = appCompatActivity.getResources().openRawResource(R.raw.details_sound);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                int idSound = appCompatActivity.getResources().getIdentifier(line, "raw", appCompatActivity.getPackageName());
                soundSound.getSoundIds().put(line, idSound);
                soundSound.getSounds().put(line, soundSound.getSoundPool().load(appCompatActivity, idSound, 1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
