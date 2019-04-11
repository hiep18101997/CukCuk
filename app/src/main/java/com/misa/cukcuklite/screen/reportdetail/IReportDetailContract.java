package com.misa.cukcuklite.screen.reportdetail;

import com.misa.cukcuklite.data.model.ReportDetail;

import java.util.Date;
import java.util.List;

interface IReportDetailContract {
    interface IView {
        void onLoadDataDone(List<ReportDetail> reportDetails);
    }

    interface IPresenter {
        void loadData(Date[] dates);
    }
}
