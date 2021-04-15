package com.vientamthuong.chronotrigger.parameterConversion;

import android.util.TypedValue;

import androidx.appcompat.app.AppCompatActivity;

public class ParameterConversionSingleton {

    private static ParameterConversionSingleton parameterConversionSingleton;

    private ParameterConversionSingleton() {
    }

    public ParameterConversionSingleton getInstance() {
        if (parameterConversionSingleton == null) {
            parameterConversionSingleton = new ParameterConversionSingleton();
        }

        return parameterConversionSingleton;
    }

    public int convertDpToPx(int dp, AppCompatActivity appCompatActivity) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                appCompatActivity.getResources().getDisplayMetrics()
        );
    }


}
