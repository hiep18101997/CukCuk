package com.misa.cukcuklite.screen.report;
/**
 * ‐ Presenter trong mô hình MVP cho màn hình Báo cáo
 *
 * ‐ @created_by Hoàng Hiệp on 7/4/2019
 */
public class ReportPresenter implements IReportContract.IPresenter {
    private static final String TAG = ReportPresenter.class.getName();

    private IReportContract.IView mView;

    public ReportPresenter(IReportContract.IView view) {
        mView = view;
    }
}
