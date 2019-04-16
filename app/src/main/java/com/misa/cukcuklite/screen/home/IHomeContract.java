package com.misa.cukcuklite.screen.home;

import android.net.Uri;

/**
 * ‐ Contract  trong mô hình MVP cho màn hình Home ‐ @created_by Hoàng Hiệp on 4/5/2019
 */
interface IHomeContract {

  interface IView {

    void showInfo(String name, Uri uri);

    void navigateLoginScreen();
  }

  interface IPresenter {

    void getInfoUser();

    void logOut();

  }
}
