package com.misa.cukcuklite.aplication;

import android.app.Application;
import android.content.Context;
import androidx.appcompat.app.AppCompatDelegate;
import com.facebook.FacebookSdk;

/**
 * ‐ Application của app
 * <p>
 * ‐ @created_by Hoàng Hiệp on 3/27/2019
 */
public class CukCukApplication extends Application {

  private static Context sContext;

  public static Context getContext() {
    return sContext;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    FacebookSdk.sdkInitialize(getApplicationContext());
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    sContext = getApplicationContext();
  }
}
