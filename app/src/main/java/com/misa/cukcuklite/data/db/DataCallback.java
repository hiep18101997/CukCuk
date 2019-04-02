package com.misa.cukcuklite.data.db;

public interface DataCallback<T> {
    void onGetDataSuccess(T data);

    void onGetDataFailed(String msg);
}