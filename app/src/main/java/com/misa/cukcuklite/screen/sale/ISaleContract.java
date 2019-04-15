package com.misa.cukcuklite.screen.sale;

import com.misa.cukcuklite.data.model.Order;

import java.util.List;

/**
 * ‐ Contract  trong mô hình MVP cho màn hình Bán hàng
 * ‐ @created_by Hoàng Hiệp on 4/15/2019
 */
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
