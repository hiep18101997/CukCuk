package com.misa.cukcuklite.screen.login;

import android.app.Activity;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

/**
 * ‐ Contract  trong mô hình MVP cho màn hình Đăng nhập
 * ‐ @created_by Hoàng Hiệp on 4/15/2019
 */
interface ILoginContract {
    interface IView {
        void navigateHomeScreen();

        void showLoading();

        void hideLoading();

        void onLoginFail();
    }

    interface IPresenter {
        void loginWithFacebook(AccessToken accessToken);

        void handleSignInResult(Activity activity,Task<GoogleSignInAccount> task);
    }
}
