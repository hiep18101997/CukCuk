package com.misa.cukcuklite.screen.loginphoneemail;

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
