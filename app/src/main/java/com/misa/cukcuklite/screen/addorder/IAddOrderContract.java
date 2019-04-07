package com.misa.cukcuklite.screen.addorder;

import com.misa.cukcuklite.data.model.DishOrder;
import com.misa.cukcuklite.data.model.Order;

import java.util.List;

/**
 * ‐ Contract  trong mô hình MVP cho màn hình Đặt món
 * ‐ @created_by Hoàng Hiệp on 4/5/2019
 */
interface IAddOrderContract {
    interface IView {
        void onLoadListDishSuccess(List<DishOrder> list);

        void onZeroPerson();

        void onZeroTable();

        void onZeroDish();

        void onSaveOrderDone();

        void onEditOrderDone();

        void navigateBillActivity(Order mOrder);
    }

    interface IPresenter {
        void getMenu();

        void saveOrder(String table, String person, List<DishOrder> list);

        void editOrder(int idOrder, String table, String person, List<DishOrder> list);

        void takeMoney(String toString, String toString1, List<DishOrder> list);
    }
}
