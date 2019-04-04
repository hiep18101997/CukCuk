package com.misa.cukcuklite.screen.bill;

public class BillPresenter implements IBillContract.IPresenter {
    private static final String TAG = BillPresenter.class.getName();
    private IBillContract.IView mView;

    public BillPresenter(IBillContract.IView view) {
        mView = view;
    }
}
