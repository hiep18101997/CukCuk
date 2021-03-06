package com.misa.cukcuklite.screen.editdish;

import android.app.Activity;
import android.graphics.Bitmap;
import com.misa.cukcuklite.data.model.Dish;
import com.misa.cukcuklite.data.model.Unit;

/**
 * ‐ Contract  trong mô hình MVP cho màn hình sửa món
 * <p>
 * ‐ @created_by Hoàng Hiệp on 3/23/2019
 */
interface IEditDishContract {

  interface IView {

    void onRemoveDishDone();

    void onEditDishDone();

    void onGetNameDone(Unit unit);

    void onRemoveUnitError();
  }

  interface IPresenter {

    void editDish(Dish currentDish);

    Bitmap getBitmapFromAssets(Activity activity, String icon);

    void removeDish(Dish currentDish);

    void getNameUnitFromId(int unit);
  }
}
