package com.misa.cukcuklite.screen.menu;

import com.misa.cukcuklite.data.db.model.Dish;

import java.util.List;

/**
 * ‐ Contract  trong mô hình MVP cho màn hình Danh sách món
 * <p>
 * ‐ @created_by Hoàng Hiệp on 3/23/2019
 */
interface IMenuContract {
    interface IView {
        void onLoadListDishSuccess(List<Dish> dishes);
    }

    interface IPresenter {
        void getAllDish();
    }
}
