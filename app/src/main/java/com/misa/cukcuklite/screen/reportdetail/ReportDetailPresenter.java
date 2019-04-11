package com.misa.cukcuklite.screen.reportdetail;

public class ReportDetailPresenter implements IReportDetailContract.IPresenter {
    private static final String TAG = ReportDetailPresenter.class.getName();

    private IReportDetailContract.IView mView;

    public ReportDetailPresenter(IReportDetailContract.IView view) {
        mView = view;
    }
}
