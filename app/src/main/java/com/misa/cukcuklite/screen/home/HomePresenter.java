package com.misa.cukcuklite.screen.home;

/**
 * ‐ Presenter trong mô hình MVP cho màn hình Home
 * ‐ @created_by Hoàng Hiệp on 4/5/2019
 */
public class HomePresenter implements IHomeContract.IPresenter {
    private static final String TAG = HomePresenter.class.getName();

    private IHomeContract.IView mView;

    public HomePresenter(IHomeContract.IView view) {
        mView = view;
    }
}
