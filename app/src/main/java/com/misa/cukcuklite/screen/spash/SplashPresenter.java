package com.misa.cukcuklite.screen.spash;

public class SplashPresenter implements ISplashContract.IPresenter {
    private static final String TAG = SplashPresenter.class.getName();

    private ISplashContract.IView mView;

    public SplashPresenter(ISplashContract.IView view) {
        mView = view;
    }
}
