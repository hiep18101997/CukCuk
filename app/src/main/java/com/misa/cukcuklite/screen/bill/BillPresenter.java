package com.misa.cukcuklite.screen.bill;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.misa.cukcuklite.data.db.DatabaseClient;
import com.misa.cukcuklite.data.model.Bill;
import com.misa.cukcuklite.data.model.Order;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * ‐ Presenter trong mô hình MVP cho màn hình Hóa đơn
 * ‐ @created_by Hoàng Hiệp on 4/5/2019
 */
public class BillPresenter implements IBillContract.IPresenter {
    private static final String TAG = BillPresenter.class.getName();
    private IBillContract.IView mView;
    private Context mContext;

    public BillPresenter(IBillContract.IView view, Context context) {
        mView = view;
        mContext = context;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void saveBill(final Order order) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                order.setPay(true);
                Bill bill = new Bill(order.getId(), Calendar.getInstance().getTime());
                DatabaseClient.getInstance(mContext).getAppDatabase().mOrderDAO().updateOrder(order);
                DatabaseClient.getInstance(mContext).getAppDatabase().mBillDAO().saveBill(bill);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mView.onSaveBillDone();
            }
        }.execute();
    }
}
