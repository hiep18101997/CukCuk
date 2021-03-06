package com.misa.cukcuklite.screen.loginphoneemail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.screen.home.HomeActivity;

/**
 * - Mục đích Class :Màn hình đăng nhập bằng email và mật khẩu - @created_by Hoàng Hiệp on
 * 4/15/2019
 */
public class LoginPhoneEmailActivity extends AppCompatActivity implements
    ILoginPhoneEmailContract.IView, View.OnClickListener {

  private static final String TAG = LoginPhoneEmailActivity.class.getName();
  private ILoginPhoneEmailContract.IPresenter mPresenter;
  private EditText mUsername, mPassword;
  private ProgressDialog progressDialog;

  /**
   * Mục đích method: Lấy intent
   *
   * @param context Context
   * @return Trả về intent trỏ tới AddDishActivity
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  public static Intent getIntent(Context context) {
    Intent intent = new Intent(context, LoginPhoneEmailActivity.class);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_phone_email);
    mPresenter = new LoginPhoneEmailPresenter(this);
    initComponent();
    initListener();
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
  }

  /**
   * Mục đích method: Bắt sự kiện
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  private void initListener() {
    findViewById(R.id.btnBack).setOnClickListener(this);
    findViewById(R.id.btnLogin).setOnClickListener(this);
  }

  /**
   * Mục đích method: Xử lý sự kiện
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnBack:
        onBackPressed();
        break;
      case R.id.btnLogin:
        String username = mUsername.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        mPresenter.login(username, password);
        break;

    }
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
   * Mục đích method: Chuyển đến màn hình Home
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */

  @Override
  public void navigateHomeScreen() {
    startActivity(HomeActivity.getIntent(this));
  }

  /**
   * Mục đích method: Hiện thông báo khi đăng nhập thất bại
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */

  @Override
  public void onLoginFail() {
    Toast.makeText(this, R.string.msg_login_fail, Toast.LENGTH_SHORT).show();

  }

  /**
   * Mục đích method: Hiện loading
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */

  @Override
  public void showLoading() {
    progressDialog.setMessage(getString(R.string.loading));
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.show();
  }

  /**
   * Mục đích method: Ẩn loading
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  @Override
  public void hideLoading() {
    progressDialog.dismiss();
  }
}
