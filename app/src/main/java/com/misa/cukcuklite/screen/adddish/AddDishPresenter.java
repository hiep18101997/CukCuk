package com.misa.cukcuklite.screen.adddish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;

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
public class AddDishPresenter implements IAddDishContract.IPresenter {
    private static final String TAG = AddDishPresenter.class.getName();
    private Context mContext;
    private IAddDishContract.IView mView;

    public AddDishPresenter(Context context, IAddDishContract.IView view) {
        mView = view;
        mContext = context;
    }

    /**
     * Mục dích method thực hiện việc chuyển image từ asset thành Bitmap
     *
     * @param context context
     * @param icon    đường dẫn ảnh
     * @return trả về Bitmap
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public Bitmap getBitmapFromAssets(Context context, String icon) {
        AssetManager assetManager = context.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(IMAGE_ASSETS + icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(istr);
    }

    /**
     * Mục dích method thực hiện việc thêm món vào Database
     *
     * @param dish Món
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void addDish(final Dish dish) {
        if (validateData(dish)) {
            new AsyncTask<Void, Boolean, Void>() {
                @SuppressLint("WrongThread")
                @Override
                protected Void doInBackground(Void... voids) {
                    DatabaseClient.getInstance(mContext).getAppDatabase().mDishDAO().insertDish(dish);
                    return null;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    mView.onAddDishDone();
                }
            }.execute();
        }
    }

    /**
     * Mục dích method thực hiện việc kiểm tra dữ liệu nhập vào
     *
     * @param dish Món
     * @return trả về xem dữ liệu hợp lẹ hay không
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private boolean validateData(Dish dish) {
        if (TextUtils.isEmpty(dish.getName())) {
            mView.onEmptyName();
            return false;
        }
        if (TextUtils.isEmpty(dish.getUnit())) {
            mView.onEmptyUnit();
            return false;
        }
        return true;
    }
}
