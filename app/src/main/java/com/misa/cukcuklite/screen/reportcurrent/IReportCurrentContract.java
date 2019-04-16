package com.misa.cukcuklite.screen.reportcurrent;

import com.misa.cukcuklite.data.model.ReportCurrent;
import java.util.List;

/**
 * ‐ Contract  trong mô hình MVP cho màn hình Báo cáo Gần đây
 * <p>
 * ‐ @created_by Hoàng Hiệp on 7/4/2019
 */
interface IReportCurrentContract {

  interface IView {

    void onLoadReportCurrentDone(List<ReportCurrent> reportCurrents);
  }

  interface IPresenter {

    void getListReportCurrent();
  }
}
