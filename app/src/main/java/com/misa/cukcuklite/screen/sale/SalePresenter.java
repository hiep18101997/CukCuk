package com.misa.cukcuklite.screen.sale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.misa.cukcuklite.data.db.DatabaseClient;
import com.misa.cukcuklite.data.db.model.Order;

import java.util.List;

public class SalePresenter implements ISaleContract.IPresenter {
    private static final String TAG = SalePresenter.class.getName();

    private ISaleContract.IView mView;
    private Context mContext;

    public SalePresenter(ISaleContract.IView view, Context context) {
        mView = view;
        mContext = context;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void getAllOrder() {
        new AsyncTask<Void, Void, List<Order>>() {
            @Override
            protected List<Order> doInBackground(Void... voids) {
                return DatabaseClient.getInstance(mContext).getAppDatabase().mOrderDAO().getAllOrder();
            }

            @Override
            protected void onPostExecute(List<Order> orders) {
                super.onPostExecute(orders);
                mView.onLoadListOrderSuccess(orders);
            }
        }.execute();

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void removeOrder(final Order order) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(mContext).getAppDatabase().mOrderDAO().deleteOrder(order);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mView.onRemoveOrderSuccess();
            }
        }.execute();
    }
}
