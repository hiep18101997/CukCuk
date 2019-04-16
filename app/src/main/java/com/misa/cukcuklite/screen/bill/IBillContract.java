package com.misa.cukcuklite.screen.bill;

import com.misa.cukcuklite.data.model.Order;

/**
 * ‐ Contract  trong mô hình MVP cho màn hình Hóa đơn ‐ @created_by Hoàng Hiệp on 4/5/2019
 */
interface IBillContract {

  interface IView {

    void onSaveBillDone();

    void showBillId(Integer integer);
  }

  interface IPresenter {

    void saveBill(Order order, long l);

    void getBillId();
  }
}
