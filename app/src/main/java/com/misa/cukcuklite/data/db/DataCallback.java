package com.misa.cukcuklite.data.db;

/**
 * - Mục đích Class : tạo interface Callback - @created_by Hoàng Hiệp on 4/12/2019
 */
public interface DataCallback<T> {

  void onGetDataSuccess(T data);

  void onGetDataFailed(String msg);
}