package com.misa.cukcuklite.screen.reportcurrent;

/**
 * ‐ Presenter trong mô hình MVP cho màn hình Báo cáo
 * <p>
 * ‐ @created_by Hoàng Hiệp on 7/4/2019
 */
public class ReportCurrentPresenter implements IReportCurrentContract.IPresenter {
    private static final String TAG = ReportCurrentPresenter.class.getName();

    private IReportCurrentContract.IView mView;

    public ReportCurrentPresenter(IReportCurrentContract.IView view) {
        mView = view;
    }
}
