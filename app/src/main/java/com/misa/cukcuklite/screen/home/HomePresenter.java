package com.misa.cukcuklite.screen.home;

import android.net.Uri;
import com.google.firebase.auth.FirebaseAuth;

/**
 * ‐ Presenter trong mô hình MVP cho màn hình Home ‐ @created_by Hoàng Hiệp on 4/5/2019
 */
public class HomePresenter implements IHomeContract.IPresenter {

  private static final String TAG = HomePresenter.class.getName();

  private IHomeContract.IView mView;

  public HomePresenter(IHomeContract.IView view) {
    mView = view;
  }

  /**
   * Mục đích method: Lấy thông tin user
   *
   * @created_by Hoàng Hiệp on 4/15/2019
   */
  @Override
  public void getInfoUser() {
    try {
      String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
      Uri uri = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
      String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
      if (name == null) {
        mView.showInfo(email, uri);
      } else {
        mView.showInfo(name, uri);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method: Đăng xuất
   *
   * @created_by Hoàng Hiệp on 4/15/2019
   */
  @Override
  public void logOut() {
    FirebaseAuth.getInstance().signOut();
    mView.navigateLoginScreen();
  }
}
