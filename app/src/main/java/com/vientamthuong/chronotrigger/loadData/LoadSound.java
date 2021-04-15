package com.vientamthuong.chronotrigger.loadData;

import android.media.AudioAttributes;
import android.media.SoundPool;

import com.vientamthuong.chronotrigger.MainActivity;
import com.vientamthuong.chronotrigger.R;
import com.vientamthuong.chronotrigger.data.SourceSound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoadSound {

    private SourceSound soundSound;

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

    public void loadData(MainActivity mainActivity) {
        try {
            InputStream inputStream = mainActivity.getResources().openRawResource(R.raw.details_sound);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                int idSound = mainActivity.getResources().getIdentifier(line, "raw", mainActivity.getPackageName());
                soundSound.getSounds().put(line, soundSound.getSoundPool().load(mainActivity, idSound, 1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
