package com.misa.cukcuklite.screen.register;

interface IRegisterContract {

  interface IView {

    void hideLoading();

    void onEmptyUsername();

    void onEmptyPassword();

    void onRepassFail();

    void onEmptyRePass();

    void navigateHomeScreen();

    void onRegisterFail();
  }

  interface IPresenter {

    void register(String username, String password, String repassword);
  }
}
