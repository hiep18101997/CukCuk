package com.misa.cukcuklite.screen.loginphoneemail;

/**
 * - Mục đích Class :Màn hình đăng nhập bằng tài khoản mật khẩu - @created_by Hoàng Hiệp on
 * 4/15/2019
 */
interface ILoginPhoneEmailContract {

  interface IView {

    void onEmptyUsername();

    void onEmptyPassword();

    void navigateHomeScreen();

    void onLoginFail();

    void showLoading();

    void hideLoading();
  }

  interface IPresenter {

    void login(String username, String password);
  }
}
