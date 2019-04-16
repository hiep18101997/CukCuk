package com.misa.cukcuklite.screen.loginphoneemail;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPhoneEmailPresenter implements ILoginPhoneEmailContract.IPresenter {

  private static final String TAG = LoginPhoneEmailPresenter.class.getName();

  private ILoginPhoneEmailContract.IView mView;

  public LoginPhoneEmailPresenter(ILoginPhoneEmailContract.IView view) {
    mView = view;
  }

  /**
   * Mục đích method: Đăng nhập bằng tài khoản, mật khẩu
   *
   * @param username: tên đăng nhập
   * @param password: mật khẩu
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  @Override
  public void login(String username, String password) {
    try {
      if (isValidInput(username, password)) {
        mView.showLoading();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                mView.hideLoading();
                if (!task.isSuccessful()) {
                  mView.onLoginFail();
                  return;
                }
                mView.navigateHomeScreen();
              }
            })
            .addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
              }
            });
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method: Kiểm tra dữ liệu đầu vào
   *
   * @param username: tên đăng nhập
   * @param password: mật khẩu
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  private boolean isValidInput(String username, String password) {
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
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }


}
