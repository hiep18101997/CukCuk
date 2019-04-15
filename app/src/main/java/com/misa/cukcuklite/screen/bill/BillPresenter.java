package com.misa.cukcuklite.screen.bill;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.misa.cukcuklite.data.db.DatabaseClient;
import com.misa.cukcuklite.data.model.Bill;
import com.misa.cukcuklite.data.model.DishOrder;
import com.misa.cukcuklite.data.model.Order;

import java.util.Calendar;
import java.util.List;

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

    /**
     * Mục đích method: Lưu hóa đơn
     *
     * @param amount:Tổng số tiền
     * @param order:      đối tượng đặt món
     * @created_by Hoàng Hiệp on 4/6/2019
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void saveBill(final Order order, final long amount) {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    order.setPay(true);
                    if (order.getId() == 0) {
                        int orderId = (int) DatabaseClient.getInstance(mContext).getAppDatabase().mOrderDAO().insertOrder(order);
                        order.setId(orderId);
                        saveDishOrderByOrderId(order.getOrders(), orderId);
                    } else {
                        DatabaseClient.getInstance(mContext).getAppDatabase().mOrderDAO().updateOrder(order);
                    }
                    Bill bill = new Bill(order.getId(), Calendar.getInstance().getTime());
                    bill.setAmount(amount);
                    DatabaseClient.getInstance(mContext).getAppDatabase().mBillDAO().saveBill(bill);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    mView.onSaveBillDone();
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Lưu hóa Món ăn theo danh sách đặt món
     *
     * @param list:Danh sách món
     * @param orderId:  id món
     * @created_by Hoàng Hiệp on 4/6/2019
     */
    @SuppressLint("StaticFieldLeak")
    private void saveDishOrderByOrderId(final List<DishOrder> list, final int orderId) {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    for (DishOrder dishOrder : list) {
                        dishOrder.setOrderId(orderId);
                        DatabaseClient.getInstance(mContext).getAppDatabase().mDishOrderDAO().insertDishOrder(dishOrder);
                    }
                    return null;
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
