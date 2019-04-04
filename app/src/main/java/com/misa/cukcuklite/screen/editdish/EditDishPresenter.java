package com.misa.cukcuklite.screen.editdish;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.misa.cukcuklite.data.db.DatabaseClient;
import com.misa.cukcuklite.data.db.model.Dish;

import java.io.IOException;
import java.io.InputStream;

import static com.misa.cukcuklite.AppConstant.IMAGE_ASSETS;

/**
 * ‐ Presenter trong mô hình MVP cho màn hình Thêm món
 * <p>
 * ‐ @created_by Hoàng Hiệp on 3/27/2019
 */
public class EditDishPresenter implements IEditDishContract.IPresenter {
    private static final String TAG = EditDishPresenter.class.getName();
    private Context mContext;
    private IEditDishContract.IView mView;

    public EditDishPresenter(IEditDishContract.IView view) {
        mView = view;
        mContext = (Context) view;
    }

    /**
     * Mục đích method: Sửa món ăn
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void editDish(final Dish currentDish) {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    DatabaseClient.getInstance(mContext).getAppDatabase().mDishDAO().updateDish(currentDish);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    mView.onEditDishDone();
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc chuyển image từ asset thành Bitmap
     *
     * @param activity context
     * @param icon     đường dẫn ảnh
     * @return trả về Bitmap
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public Bitmap getBitmapFromAssets(Activity activity, String icon) {
        AssetManager assetManager = mContext.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(IMAGE_ASSETS + icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(istr);
    }

    /**
     * Mục đích method: Xóa món ăn
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void removeDish(final Dish currentDish) {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    DatabaseClient.getInstance(mContext).getAppDatabase().mDishDAO().deleteDish(currentDish);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    mView.onRemoveDishDone();
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
