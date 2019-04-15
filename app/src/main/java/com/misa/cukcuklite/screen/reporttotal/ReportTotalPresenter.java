package com.misa.cukcuklite.screen.reporttotal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.misa.cukcuklite.data.db.DatabaseClient;
import com.misa.cukcuklite.data.model.Bill;
import com.misa.cukcuklite.data.model.ParamReport;
import com.misa.cukcuklite.data.model.ReportTotal;
import com.misa.cukcuklite.enums.ReportTotalEnum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * ‐ Presenter trong mô hình MVP cho màn hình Báo cáo Số lượng
 * <p>
 * ‐ @created_by Hoàng Hiệp on 7/4/2019
 */
public class ReportTotalPresenter implements IReportTotalContract.IPresenter {
    private static final String TAG = ReportTotalPresenter.class.getName();

    private IReportTotalContract.IView mView;
    private Context mContext;

    public ReportTotalPresenter(IReportTotalContract.IView view, Context context) {
        mView = view;
        mContext = context;
    }

    /**
     * Mục đích method: Tính tổng số tiền từ danh sách hóa đơn
     *
     * @param bills:danh sách hóa đơn
     * @return amount: tổng số tiền
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

    /**
     * Mục đích method: Lấy ra danh sách ReportTotal từ ParamReport
     *
     * @param paramReport:ParamReport
     * @created_by Hoàng Hiệp on 4/15/2019
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void loadData(final ParamReport paramReport) {
        try {
            new AsyncTask<Void, Void, List<ReportTotal>>() {
                @Override
                protected List<ReportTotal> doInBackground(Void... voids) {
                    try {
                        Calendar calendar = Calendar.getInstance();
                        List<ReportTotal> reportTotals = new ArrayList<>();
                        switch (paramReport.getParamType()) {
                            case LAST_WEEK:
                            case THIS_WEEK:
                                calendar.setTime(paramReport.getFromDate());
                                while (calendar.getTime().compareTo(paramReport.getToDate()) < 0) {
                                    ReportTotal reportTotal = new ReportTotal(ReportTotalEnum.WEEK);
                                    Date from = calendar.getTime();
                                    if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
                                        reportTotal.setTitleReportDetail("CN");
                                    } else {
                                        reportTotal.setTitleReportDetail("Thứ " + calendar.get(Calendar.DAY_OF_WEEK));
                                    }
                                    calendar.add(Calendar.DATE, 1);
                                    calendar.add(Calendar.SECOND, -1);
                                    Date to = calendar.getTime();
                                    List<Bill> bills = DatabaseClient.getInstance(mContext).getAppDatabase().mBillDAO().getBillBetweenDate(from, to);
                                    reportTotal.setAmount(getAmount(bills));
                                    reportTotal.setFromDate(from);
                                    reportTotal.setToDate(to);
                                    reportTotals.add(reportTotal);
                                    calendar.add(Calendar.SECOND, 1);
                                }
                                break;
                            case LAST_MONTH:
                            case THIS_MONTH:
                                calendar.setTime(paramReport.getFromDate());
                                while (calendar.getTime().compareTo(paramReport.getToDate()) < 0) {
                                    ReportTotal reportTotal = new ReportTotal(ReportTotalEnum.MONTH);
                                    Date from = calendar.getTime();
                                    reportTotal.setTitleReportDetail("Ngày " + calendar.get(Calendar.DAY_OF_MONTH));
                                    calendar.add(Calendar.DATE, 1);
                                    calendar.add(Calendar.SECOND, -1);
                                    Date to = calendar.getTime();
                                    List<Bill> bills = DatabaseClient.getInstance(mContext).getAppDatabase().mBillDAO().getBillBetweenDate(from, to);
                                    reportTotal.setAmount(getAmount(bills));
                                    reportTotal.setFromDate(from);
                                    reportTotal.setToDate(to);
                                    reportTotals.add(reportTotal);
                                    calendar.add(Calendar.SECOND, 1);
                                }
                                break;
                            case LAST_YEAR:
                            case THIS_YEAR:
                                calendar.setTime(paramReport.getFromDate());
                                while (calendar.getTime().compareTo(paramReport.getToDate()) < 0) {
                                    ReportTotal reportTotal = new ReportTotal(ReportTotalEnum.YEAR);
                                    Date from = calendar.getTime();
                                    reportTotal.setTitleReportDetail("Tháng " + (calendar.get(Calendar.MONTH) + 1));
                                    calendar.add(Calendar.MONTH, 1);
                                    calendar.add(Calendar.SECOND, -1);
                                    Date to = calendar.getTime();
                                    List<Bill> bills = DatabaseClient.getInstance(mContext).getAppDatabase().mBillDAO().getBillBetweenDate(from, to);
                                    reportTotal.setAmount(getAmount(bills));
                                    reportTotal.setFromDate(from);
                                    reportTotal.setToDate(to);
                                    reportTotals.add(reportTotal);
                                    calendar.add(Calendar.SECOND, 1);
                                }
                                break;
                        }

                        return reportTotals;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return new ArrayList<>();
                }

                @Override
                protected void onPostExecute(List<ReportTotal> reportTotals) {
                    super.onPostExecute(reportTotals);
                    mView.onLoadDataDone(reportTotals);
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
