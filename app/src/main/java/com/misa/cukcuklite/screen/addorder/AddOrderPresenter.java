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

public class AddOrderPresenter implements IAddOrderContract.IPresenter {
    private static final String TAG = AddOrderPresenter.class.getName();
    private IAddOrderContract.IView mView;
    private Context mContext;

    public AddOrderPresenter(IAddOrderContract.IView view, Context context) {
        mView = view;
        mContext = context;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void getMenu() {
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
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void saveOrder(String table, String person, List<DishOrder> list) {
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
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void editOrder(int idOrder, String table, String person, List<DishOrder> list) {
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

    }

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
