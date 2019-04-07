package com.misa.cukcuklite.screen.sale;

import com.misa.cukcuklite.data.model.Order;

import java.util.List;

interface ISaleContract {
    interface IView {
        void onLoadListOrderSuccess(List<Order> orders);

        void onRemoveOrderSuccess();

    }

    interface IPresenter {
        void getAllOrder();

        void removeOrder(Order order);
    }
}
