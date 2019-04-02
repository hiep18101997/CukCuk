package com.misa.cukcuklite.screen.home;

public class HomePresenter implements IHomeContract.IPresenter {
    private static final String TAG = HomePresenter.class.getName();

    private IHomeContract.IView mView;

    public HomePresenter(IHomeContract.IView view) {
        mView = view;
    }
}
