package com.misa.cukcuklite.screen.bill;

/**
 * ‐ Presenter trong mô hình MVP cho màn hình Hóa đơn
 * ‐ @created_by Hoàng Hiệp on 4/5/2019
 */
public class BillPresenter implements IBillContract.IPresenter {
    private static final String TAG = BillPresenter.class.getName();
    private IBillContract.IView mView;

    public BillPresenter(IBillContract.IView view) {
        mView = view;
    }
}
