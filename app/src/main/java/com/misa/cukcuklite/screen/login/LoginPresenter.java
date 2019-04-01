package com.misa.cukcuklite.screen.login;

public class LoginPresenter implements ILoginContract.IPresenter {
    private static final String TAG = LoginPresenter.class.getName();

    private ILoginContract.IView mView;

    public LoginPresenter(ILoginContract.IView view) {
        mView = view;
    }
}
