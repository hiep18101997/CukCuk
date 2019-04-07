package com.misa.cukcuklite.screen.chooseunit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.misa.cukcuklite.data.db.DatabaseClient;
import com.misa.cukcuklite.data.db.model.Dish;
import com.misa.cukcuklite.data.db.model.Unit;

import java.util.List;

/**
 * ‐ Presenter trong mô hình MVP cho màn hình Chon đơn vị
 * <p>
 * ‐ @created_by Hoàng Hiệp on 3/27/2019
 */
public class ChooseUnitPresenter implements IChooseUnitContract.IPresenter {
    private static final String TAG = ChooseUnitPresenter.class.getName();
    private Context mContext;
    private IChooseUnitContract.IView mView;

    public ChooseUnitPresenter(Context context, IChooseUnitContract.IView view) {
        mContext = context;
        mView = view;
    }

    /**
     * Mục đích method lấy dánh sách các đơn vị tính
     *
     * @created_by Hoàng Hiệp on 3/28/2019
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void getListUnit() {
        new AsyncTask<Void, Void, List<Unit>>() {
            @Override
            protected List<Unit> doInBackground(Void... voids) {
                return DatabaseClient.getInstance(mContext).getAppDatabase().mUnitDAO().getAllUnit();
            }

            @Override
            protected void onPostExecute(List<Unit> units) {
                super.onPostExecute(units);
                mView.onGetUnitSuccess(units);
            }
        }.execute();
    }

    /**
     * Mục đích method lưu đơn vị tính
     *
     * @param text Đơn vị tính được thêm
     * @created_by Hoàng Hiệp on 3/28/2019
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void saveUnit(final String text) {
        final int[] unitId = new int[1];
        new AsyncTask<Void, Boolean, Void>() {
            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {
                Unit unit = new Unit(text);
                Unit existUnit = DatabaseClient.getInstance(mContext).getAppDatabase().mUnitDAO().getUnitByName(text);
                if (existUnit == null) {
                    unitId[0] = (int) DatabaseClient.getInstance(mContext).getAppDatabase().mUnitDAO().insertUnit(unit);
                    publishProgress(true);
                } else {
                    publishProgress(false);
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Boolean... values) {
                super.onProgressUpdate(values);
                if (values[0]) {
                    mView.onInsertUnitSuccess(unitId[0]);
                } else {
                    mView.onInsertUnitError();
                }
            }
        }.execute();
    }

    /**
     * Mục đích method sửa đơn vị tính
     *
     * @param unitEdit Đơn vị tính được sửa
     * @created_by Hoàng Hiệp on 3/28/2019
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void editUnit(final Unit unitEdit) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(mContext).getAppDatabase().mUnitDAO().updateUnit(unitEdit);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mView.onEditUnitDone(unitEdit.getId());
            }
        }.execute();
    }

    /**
     * Mục đích method xóa đơn vị tính
     *
     * @param unit Đơn vị tính bị xóa
     * @created_by Hoàng Hiệp on 3/28/2019
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void removeUnit(final Unit unit) {
        new AsyncTask<Void, Boolean, Void>() {
            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {
                Dish dish = DatabaseClient.getInstance(mContext).getAppDatabase().mDishDAO().getDishById(unit.getId());
                if (dish == null) {
                    DatabaseClient.getInstance(mContext).getAppDatabase().mUnitDAO().deleteUnit(unit);
                    publishProgress(true);
                } else {
                    publishProgress(false);
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Boolean... values) {
                super.onProgressUpdate(values);
                if (values[0]) {
                    mView.onRemoveUnitSuccess();
                } else {
                    mView.onRemoveUnitError();
                }
            }
        }.execute();
    }
}
