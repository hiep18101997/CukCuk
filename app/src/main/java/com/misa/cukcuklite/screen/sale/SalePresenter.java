package com.misa.cukcuklite.screen.sale;

public class SalePresenter implements ISaleContract.IPresenter {
    private static final String TAG = SalePresenter.class.getName();

    private ISaleContract.IView mView;

    public SalePresenter(ISaleContract.IView view) {
        mView = view;
    }
}
