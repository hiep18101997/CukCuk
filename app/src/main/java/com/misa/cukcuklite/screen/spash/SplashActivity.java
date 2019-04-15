package com.misa.cukcuklite.screen.spash;

import android.content.Intent;
import android.os.Bundle;

import com.misa.cukcuklite.screen.login.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ‐ Màn hình splash
 * ‐ @created_by Hoàng Hiệp on 4/15/2019
 */
public class SplashActivity extends AppCompatActivity implements ISplashContract.IView {
    private static final String TAG = SplashActivity.class.getName();
    private ISplashContract.IPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
        mPresenter = new SplashPresenter(this);
    }
}
