package com.vientamthuong.chronotrigger.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.appcompat.app.AppCompatActivity;

import com.vientamthuong.chronotrigger.R;
import com.vientamthuong.chronotrigger.gameEffect.Animation;
import com.vientamthuong.chronotrigger.parameterConversion.ParameterConversionSingleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceAnimation {

    // Khai báo các thuộc tính
    // Instance
    private static SourceAnimation sourceAnimation;
    // List Animation
    private final Map<String, Animation> animations;

    private SourceAnimation() {
        animations = new HashMap<>();
    }

    public static SourceAnimation getInstance() {
        if (sourceAnimation == null) {
            sourceAnimation = new SourceAnimation();
        }
        return sourceAnimation;
    }

    // Phương thức lấy một animation
    @SuppressWarnings("ConstantConditions")
    public Animation getAnimation(String name) {
        return new Animation(animations.get(name));
    }

    // Phương thức load animation
    public void loadAnimation(AppCompatActivity appCompatActivity) {
        try {
            InputStream inputStream = appCompatActivity.getResources().openRawResource(R.raw.details_animation);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            Bitmap nowBitMap = null;
            List<Bitmap> listBitmap = null;
            List<Long> listDuration = null;
            String nameAnimation = null;
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                String[] array = line.split(" ");
                if (array[0].equals("image")) {
                    String nameImage = array[1];
                    int idImage = appCompatActivity.getResources().getIdentifier(nameImage, "drawable", appCompatActivity.getPackageName());
                    nowBitMap = BitmapFactory.decodeResource(appCompatActivity.getResources(), idImage);
                } else if (array[0].equals("animation")) {
                    if (listBitmap != null) {
                        Animation animation = new Animation(listBitmap, listDuration);
                        animations.put(nameAnimation, animation);
                    }
                    listBitmap = new ArrayList<>();
                    listDuration = new ArrayList<>();
                    nameAnimation = array[1];
                } else {
                    int x = Integer.parseInt(array[0]);
                    int y = Integer.parseInt(array[1]);
                    int width = Integer.parseInt(array[2]);
                    int height = Integer.parseInt(array[3]);
                    long duration = Long.parseLong(array[4]);
                    int xPx = ParameterConversionSingleton.getInstance().convertDpToPx(x, appCompatActivity);
                    int yPx = ParameterConversionSingleton.getInstance().convertDpToPx(y, appCompatActivity);
                    int wPx = ParameterConversionSingleton.getInstance().convertDpToPx(width, appCompatActivity);
                    int hPx = ParameterConversionSingleton.getInstance().convertDpToPx(height, appCompatActivity);
                    Bitmap bitmap = Bitmap.createBitmap(nowBitMap, xPx, yPx, wPx, hPx);
                    listBitmap.add(bitmap);
                    listDuration.add(duration);
                }
            }
            Animation animation = new Animation(listBitmap, listDuration);
            animations.put(nameAnimation, animation);
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}