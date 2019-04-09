package com.misa.cukcuklite.screen.reporttotal;

import com.misa.cukcuklite.data.model.ParamReport;
import com.misa.cukcuklite.data.model.ReportCurrent;
import com.misa.cukcuklite.data.model.ReportTotal;

import java.util.List;

/**
 * ‐ Contract  trong mô hình MVP cho màn hình Báo cáo
 *
 * ‐ @created_by Hoàng Hiệp on 7/4/2019
 */
interface IReportTotalContract {
    interface IView {
        void onLoadDataDone(List<ReportTotal> reportTotals);
    }

    interface IPresenter {
        void loadData(ParamReport paramReport);
    }
}
