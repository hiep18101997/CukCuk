package com.misa.cukcuklite.screen.addorder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.misa.cukcuklite.data.db.DatabaseClient;
import com.misa.cukcuklite.data.db.model.Dish;
import com.misa.cukcuklite.data.db.model.DishOrder;
import com.misa.cukcuklite.data.db.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * ‐ Presenter trong mô hình MVP cho màn hình Đặt món
 * ‐ @created_by Hoàng Hiệp on 4/5/2019
 */
public class AddOrderPresenter implements IAddOrderContract.IPresenter {
    private static final String TAG = AddOrderPresenter.class.getName();
    private IAddOrderContract.IView mView;
    private Context mContext;

    public AddOrderPresenter(IAddOrderContract.IView view, Context context) {
        mView = view;
        mContext = context;
    }

    /**
     * Mục đích method: Lấy tất cả danh sách món ăn từ databasse
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void getMenu() {
        try {
            new AsyncTask<Void, Void, List<Dish>>() {
                @Override
                protected List<Dish> doInBackground(Void... voids) {
                    return DatabaseClient.getInstance(mContext).getAppDatabase().mDishDAO().getAllDish();
                }

                @Override
                protected void onPostExecute(List<Dish> dishes) {
                    super.onPostExecute(dishes);
                    List<DishOrder> list = new ArrayList<>();
                    for (Dish dish : dishes) {
                        if (dish.isSell()) {
                            DishOrder entry = new DishOrder(dish, 0);
                            list.add(entry);
                        }
                    }
                    mView.onLoadListDishSuccess(list);
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Lưu đối tượng Đặt món
     *
     * @param table:  số bàn
     * @param person: số người
     * @param list:   danh sách các món đã order
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void saveOrder(String table, String person, List<DishOrder> list) {
        try {
            if (isValidData(table, person, list)) {
                final Order mOrder = new Order.Builder()
                        .setNumberTable(Integer.valueOf(table))
                        .setNumberPerson(Integer.valueOf(person))
                        .setListDish(list).build();
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        DatabaseClient.getInstance(mContext).getAppDatabase().mOrderDAO().insertOrder(mOrder);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        mView.onSaveOrderDone();
                    }
                }.execute();

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Lưu đối tượng Đặt món
     *
     * @param idOrder: id của đối tượng đặt món cần sửa
     * @param table:   số bàn
     * @param person:  số người
     * @param list:    danh sách các món đã order
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void editOrder(int idOrder, String table, String person, List<DishOrder> list) {
        try {
            if (isValidData(table, person, list)) {
                final Order mOrder = new Order.Builder()
                        .setId(idOrder)
                        .setNumberTable(Integer.valueOf(table))
                        .setNumberPerson(Integer.valueOf(person))
                        .setListDish(list).build();
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        DatabaseClient.getInstance(mContext).getAppDatabase().mOrderDAO().updateOrder(mOrder);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        mView.onEditOrderDone();
                    }
                }.execute();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    /**
     * Mục đích method: Kiểm tra đầu vào
     *
     * @param table:  số bàn
     * @param person: số người
     * @param list:   danh sách các món đã order
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    private boolean isValidData(String table, String person, List<DishOrder> list) {
        try {
            if (TextUtils.isEmpty(table)) {
                mView.onZeroPerson();
                return false;
            } else if (Integer.valueOf(table) == 0) {
                mView.onZeroPerson();
                return false;
            }
            if (TextUtils.isEmpty(person)) {
                mView.onZeroTable();
                return false;
            } else if (Integer.valueOf(person) == 0) {
                mView.onZeroTable();
                return false;
            }
            long amount = 0;
            for (DishOrder entry : list) {
                amount += entry.getDish().getCost() * entry.getCount();
            }
            if (amount == 0) {
                mView.onZeroDish();
                return false;
            }
            return true;

        } catch (Exception e) {
        }
        return false;
    }
}
