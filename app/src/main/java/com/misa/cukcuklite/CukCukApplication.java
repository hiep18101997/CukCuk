package com.misa.cukcuklite;

import android.app.Application;

import com.facebook.FacebookSdk;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * ‐ Application của app
 * <p>
 * ‐ @created_by Hoàng Hiệp on 3/27/2019
 */
public class CukCukApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
