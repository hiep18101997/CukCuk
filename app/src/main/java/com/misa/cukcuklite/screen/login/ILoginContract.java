package com.misa.cukcuklite.screen.login;

import com.facebook.AccessToken;

interface ILoginContract {
    interface IView {
        void navigateHomeScreen();
    }

    interface IPresenter {
        void loginWithFacebook(AccessToken accessToken);
    }
}
