package com.misa.cukcuklite.screen.adddish;

import android.content.Context;
import android.graphics.Bitmap;

import com.misa.cukcuklite.data.db.model.Dish;

/**
 * ‐ Contract  trong mô hình MVP cho màn hình Thêm món
 * <p>
 * ‐ @created_by Hoàng Hiệp on 3/23/2019
 */
interface IAddDishContract {
    interface IView {
        void onEmptyName();

        void onEmptyUnit();

        void onAddDishDone();
    }

    interface IPresenter {
        Bitmap getBitmapFromAssets(Context context, String icon);

        void addDish(Dish dish);
    }
}
