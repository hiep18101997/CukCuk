package com.misa.cukcuklite.screen.spash;

import android.content.Intent;
import android.os.Bundle;

import com.misa.cukcuklite.screen.menu.MenuFragment;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity implements ISplashContract.IView {
    private static final String TAG = SplashActivity.class.getName();
    private ISplashContract.IPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(SplashActivity.this, MenuFragment.class));
        mPresenter = new SplashPresenter(this);
        finish();
    }
}
