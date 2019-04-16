package com.misa.cukcuklite.screen.reportcurrent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import com.misa.cukcuklite.data.db.DatabaseClient;
import com.misa.cukcuklite.data.model.Bill;
import com.misa.cukcuklite.data.model.ReportCurrent;
import com.misa.cukcuklite.enums.ParamReportEnum;
import java.util.ArrayList;
import java.util.List;

/**
 * ‐ Presenter trong mô hình MVP cho màn hình Báo cáo
 * <p>
 * ‐ @created_by Hoàng Hiệp on 7/4/2019
 */
public class ReportCurrentPresenter implements IReportCurrentContract.IPresenter {

  private static final String TAG = ReportCurrentPresenter.class.getName();

  private IReportCurrentContract.IView mView;
  private Context mContext;

  public ReportCurrentPresenter(IReportCurrentContract.IView view, Context context) {
    mView = view;
    mContext = context;
  }

  /**
   * Mục đích method: Trả về danh sách ReportCurrent
   *
   * @created_by Hoàng Hiệp on 4/15/2019
   */
  @SuppressLint("StaticFieldLeak")
  @Override
  public void getListReportCurrent() {
    new AsyncTask<Void, Void, List<ReportCurrent>>() {
      @Override
      protected List<ReportCurrent> doInBackground(Void... voids) {
        List<ReportCurrent> reportCurrentList = new ArrayList<>();
        reportCurrentList.add(new ReportCurrent(ParamReportEnum.YESTERDAY));
        reportCurrentList.add(new ReportCurrent(ParamReportEnum.TODAY));
        reportCurrentList.add(new ReportCurrent(ParamReportEnum.THIS_WEEK));
        reportCurrentList.add(new ReportCurrent(ParamReportEnum.THIS_MONTH));
        reportCurrentList.add(new ReportCurrent(ParamReportEnum.THIS_YEAR));
        for (ReportCurrent current : reportCurrentList) {
          List<Bill> bills = DatabaseClient.getInstance(mContext)
              .getAppDatabase()
              .mBillDAO()
              .getBillBetweenDate(current.getFromDate(), current.getToDate());
          current.setAmount(getAmount(bills));
        }
        return reportCurrentList;
      }

      @Override
      protected void onPostExecute(List<ReportCurrent> reportCurrents) {
        super.onPostExecute(reportCurrents);
        mView.onLoadReportCurrentDone(reportCurrents);
      }
    }.execute();


  }

  /**
   * Mục đích method: Tính tổng số tiền
   *
   * @param bills : danh sách hóa đơn
   * @return amount: tổng só tiền của hóa đơn
   * @created_by Hoàng Hiệp on 4/15/2019
   */
  private long getAmount(List<Bill> bills) {
    try {
      long amount = 0;
      for (Bill bill : bills) {
        amount += bill.getAmount();
      }
      return amount;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }
}
