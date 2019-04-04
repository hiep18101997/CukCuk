package com.misa.cukcuklite.screen.addorder;

import com.misa.cukcuklite.data.db.model.DishOrder;

import java.util.List;

interface IAddOrderContract {
    interface IView {
        void onLoadListDishSuccess(List<DishOrder> list);

        void onZeroPerson();

        void onZeroTable();

        void onZeroDish();

        void onSaveOrderDone();

        void onEditOrderDone();
    }

    interface IPresenter {
        void getMenu();

        void saveOrder(String table, String person, List<DishOrder> list);

        void editOrder(int idOrder, String table, String person, List<DishOrder> list);
    }
}
