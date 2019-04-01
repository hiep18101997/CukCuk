package com.misa.cukcuklite.screen.sale;

import android.os.Bundle;

import com.misa.cukcuklite.R;

import androidx.appcompat.app.AppCompatActivity;


public class SaleActivity extends AppCompatActivity implements ISaleContract.IView {
    private static final String TAG = SaleActivity.class.getName();
    private ISaleContract.IPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        mPresenter = new SalePresenter(this);
    }
}
