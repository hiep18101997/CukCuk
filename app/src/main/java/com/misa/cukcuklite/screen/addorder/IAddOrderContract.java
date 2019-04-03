package com.misa.cukcuklite.screen.addorder;

import com.misa.cukcuklite.data.db.model.Dish;
import com.misa.cukcuklite.data.db.model.PendingOrder;

import java.util.List;
import java.util.Map;

interface IAddOrderContract {
    interface IView {
        void onLoadListDishSuccess(List<Map.Entry<Dish, Integer>> list);
    }

    interface IPresenter {
        void getMenu();

        void savePendingOrder(PendingOrder order);
    }
}
