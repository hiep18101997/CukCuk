package com.misa.cukcuklite.screen.register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.screen.home.HomeActivity;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity implements IRegisterContract.IView, View.OnClickListener {
    private static final String TAG = RegisterActivity.class.getName();
    private IRegisterContract.IPresenter mPresenter;
    private EditText mUsername, mPassword, mRePassword;
    private ProgressDialog progressDialog;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mPresenter = new RegisterPresenter(this);
        initComponent();
        initListener();
    }

    /**
     * Mục đích method: Bắt sự kiện
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initListener() {
        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btnRegister).setOnClickListener(this);
    }

    /**
     * Mục đích method: Khởi tạo, ánh xạ View và đổ dữ liệu mặc định cho View
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initComponent() {
        progressDialog = new ProgressDialog(this);
        mUsername = findViewById(R.id.edUserName);
        mPassword = findViewById(R.id.edPassword);
        mRePassword = findViewById(R.id.edRePassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnRegister:
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String repassword = mRePassword.getText().toString().trim();
                mPresenter.register(username, password, repassword);
                break;
        }

    }

    @Override
    public void hideLoading() {

    }

    /**
     * Mục đích method: Hiện thông báo khi tên đăng nhập để trống
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onEmptyUsername() {
        Toast.makeText(this, getString(R.string.login_msg_username_empty), Toast.LENGTH_SHORT).show();
    }

    /**
     * Mục đích method: Hiện thông báo khi mật khẩu để trống
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onEmptyPassword() {
        Toast.makeText(this, getString(R.string.login_msg_password_empty), Toast.LENGTH_SHORT).show();
    }

    /**
     * Mục đích method: Hiện thông báo khi nhập lại mật khẩu sai
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onRepassFail() {
        Toast.makeText(this, getString(R.string.login_msg_re_password_fail), Toast.LENGTH_SHORT).show();
    }

    /**
     * Mục đích method: Hiện thông báo khi mặt khẩu trống
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onEmptyRePass() {
        Toast.makeText(this, getString(R.string.login_msg_re_password_empty), Toast.LENGTH_SHORT).show();
    }

    /**
     * Mục đích method: Chuyển đến màn hình Home
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */

    @Override
    public void navigateHomeScreen() {
        startActivity(HomeActivity.getIntent(this));
    }

    @Override
    public void onRegisterFail() {
        Toast.makeText(this, getString(R.string.login_msg_register_fail), Toast.LENGTH_SHORT).show();
    }

}
