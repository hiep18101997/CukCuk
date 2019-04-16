package com.misa.cukcuklite.screen.editdish;

import static com.misa.cukcuklite.utils.AppConstant.IMAGE_ASSETS;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import com.misa.cukcuklite.data.db.DatabaseClient;
import com.misa.cukcuklite.data.model.Dish;
import com.misa.cukcuklite.data.model.DishOrder;
import com.misa.cukcuklite.data.model.Unit;
import java.io.IOException;
import java.io.InputStream;

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
   * @param icon đường dẫn ảnh
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
      new AsyncTask<Void, Boolean, Void>() {
        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {
          DishOrder dishOrder = DatabaseClient.getInstance(mContext).getAppDatabase()
              .mDishOrderDAO().getDishOrderByDishId(currentDish.getId());
          if (dishOrder == null) {
            DatabaseClient.getInstance(mContext).getAppDatabase().mDishDAO()
                .deleteDish(currentDish);
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
            mView.onRemoveDishDone();
          } else {
            mView.onRemoveUnitError();
          }
        }
      }.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method: Lấy tên đơn vị từ id
   *
   * @param unit id unit
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  @SuppressLint("StaticFieldLeak")
  @Override
  public void getNameUnitFromId(final int unit) {
    new AsyncTask<Void, Void, Unit>() {
      @Override
      protected Unit doInBackground(Void... voids) {
        return DatabaseClient.getInstance(mContext).getAppDatabase().mUnitDAO().getUnitById(unit);
      }

      @Override
      protected void onPostExecute(Unit unit) {
        super.onPostExecute(unit);
        mView.onGetNameDone(unit);
      }
    }.execute();
  }
}
