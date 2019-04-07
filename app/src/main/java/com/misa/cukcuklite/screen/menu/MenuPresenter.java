package com.misa.cukcuklite.screen.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.misa.cukcuklite.data.db.DatabaseClient;
import com.misa.cukcuklite.data.model.Dish;

import java.util.List;

/**
 * ‐ Presenter trong mô hình MVP cho màn hình Danh sách món
 * <p>
 * ‐ @created_by Hoàng Hiệp on 3/27/2019
 */
public class MenuPresenter implements IMenuContract.IPresenter {
    private static final String TAG = MenuPresenter.class.getName();
    private Context mContext;
    private IMenuContract.IView mView;

    public MenuPresenter(IMenuContract.IView view, Context context) {
        mView = view;
        mContext = context;
    }

    /**
     * Mục đích method: Lấy toàn bộ danh sách món ăn
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void getAllDish() {
        new AsyncTask<Void, Void, List<Dish>>() {
            @Override
            protected List<Dish> doInBackground(Void... voids) {
                return DatabaseClient.getInstance(mContext).getAppDatabase().mDishDAO().getAllDish();
            }

            @Override
            protected void onPostExecute(List<Dish> dishes) {
                super.onPostExecute(dishes);
                mView.onLoadListDishSuccess(dishes);
            }
        }.execute();
    }
}
