package com.misa.cukcuklite.screen.register;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPresenter implements IRegisterContract.IPresenter {

  private static final String TAG = RegisterPresenter.class.getName();

  private IRegisterContract.IView mView;

  public RegisterPresenter(IRegisterContract.IView view) {
    mView = view;
  }

  @Override
  public void register(String username, String password, String repassword) {
    if (isValidInput(username, password, repassword)) {
      FirebaseAuth.getInstance().createUserWithEmailAndPassword(username, password)
          .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()) {
                mView.navigateHomeScreen();
              } else {
                mView.onRegisterFail();
              }
            }

          });
    }
  }

  /**
   * Mục đích method: Kiểm tra dữ liệu đầu vào
   *
   * @param username: tên đăng nhập
   * @param password: mật khẩu
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  private boolean isValidInput(String username, String password, String repassword) {
    try {
      if (TextUtils.isEmpty(username)) {
        mView.hideLoading();
        mView.onEmptyUsername();
        return false;
      }
      if (TextUtils.isEmpty(password)) {
        mView.hideLoading();
        mView.onEmptyPassword();
        return false;
      }
      if (TextUtils.isEmpty(repassword)) {
        mView.hideLoading();
        mView.onEmptyRePass();
        return false;
      }
      if (!password.equals(repassword)) {
        mView.hideLoading();
        mView.onRepassFail();
        return false;
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}
