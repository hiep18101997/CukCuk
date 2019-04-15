package com.misa.cukcuklite.screen.sale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.misa.cukcuklite.data.db.DatabaseClient;
import com.misa.cukcuklite.data.model.DishOrder;
import com.misa.cukcuklite.data.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * ‐ Presenter trong mô hình MVP cho màn hình Bán hàng
 * ‐ @created_by Hoàng Hiệp on 4/15/2019
 */
public class SalePresenter implements ISaleContract.IPresenter {
    private static final String TAG = SalePresenter.class.getName();
    private ISaleContract.IView mView;
    private Context mContext;

    public SalePresenter(ISaleContract.IView view, Context context) {
        mView = view;
        mContext = context;
    }

    /**
     * Mục đích method: Lấy hết danh sách order
     *
     * @created_by Hoàng Hiệp on 4/15/2019
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void getAllOrder() {
        try {
            new AsyncTask<Void, Void, List<Order>>() {
                @Override
                protected List<Order> doInBackground(Void... voids) {
                    return DatabaseClient.getInstance(mContext).getAppDatabase().mOrderDAO().getAllOrderNotPay();
                }

                @Override
                protected void onPostExecute(List<Order> orders) {
                    super.onPostExecute(orders);
                    setDishOrderForOrder(orders);
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Thêm DishOrder cho Ỏrder
     *
     * @created_by Hoàng Hiệp on 4/15/2019
     */
    @SuppressLint("StaticFieldLeak")
    private void setDishOrderForOrder(final List<Order> orders) {
        try {
            new AsyncTask<Void, Void, List<Order>>() {
                @Override
                protected List<Order> doInBackground(Void... voids) {
                    List<Order> orderList = new ArrayList<>();
                    try {
                        for (Order order : orders) {
                            List<DishOrder> dishOrders = DatabaseClient.getInstance(mContext)
                                    .getAppDatabase().mDishOrderDAO().getDishOrderByOrderId(order.getId());
                            for (DishOrder dishOrder : dishOrders) {
                                dishOrder.setDish(DatabaseClient.getInstance(mContext)
                                        .getAppDatabase().mDishDAO().getDishById(dishOrder.getDishId()));
                            }
                            order.setOrders(dishOrders);
                            orderList.add(order);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return orderList;
                }

                @Override
                protected void onPostExecute(List<Order> orderList) {
                    super.onPostExecute(orderList);
                    mView.onLoadListOrderSuccess(orderList);
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Thêm xóa Order
     *
     * @param order: Order cần xóa
     * @created_by Hoàng Hiệp on 4/15/2019
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void removeOrder(final Order order) {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    DatabaseClient.getInstance(mContext).getAppDatabase().mOrderDAO().deleteOrder(order);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    removeDishOrder(order);
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Xóa DishOrder
     *
     * @param order: Order cần xóa
     * @created_by Hoàng Hiệp on 4/15/2019
     */
    @SuppressLint("StaticFieldLeak")
    private void removeDishOrder(final Order order) {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    List<DishOrder> dishOrders = order.getOrders();
                    try {
                        for (DishOrder dishOrder : dishOrders) {
                            DatabaseClient.getInstance(mContext).getAppDatabase().mDishOrderDAO().deleteDishOrder(dishOrder);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    mView.onRemoveOrderSuccess();
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
