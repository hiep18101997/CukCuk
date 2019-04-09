package com.misa.cukcuklite.screen.reporttotal;

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
public class ReportTotalPresenter implements IReportTotalContract.IPresenter {
    private static final String TAG = ReportTotalPresenter.class.getName();

    private IReportTotalContract.IView mView;
    private Context mContext;

    public ReportTotalPresenter(IReportTotalContract.IView view, Context context) {
        mView = view;
        mContext = context;
    }


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
