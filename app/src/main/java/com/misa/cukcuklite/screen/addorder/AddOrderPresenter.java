package com.misa.cukcuklite.screen.addorder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.misa.cukcuklite.data.db.DatabaseClient;
import com.misa.cukcuklite.data.db.model.Dish;
import com.misa.cukcuklite.data.db.model.PendingOrder;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                List<Map.Entry<Dish, Integer>> list = new ArrayList<>();
                for (Dish dish : dishes) {
                    if (dish.isSell()) {
                        Map.Entry<Dish, Integer> entry = new AbstractMap.SimpleEntry<>(dish, 0);
                        list.add(entry);
                    }
                }
                mView.onLoadListDishSuccess(list);
            }
        }.execute();
    }

    @Override
    public void savePendingOrder(PendingOrder order) {
    }
}
