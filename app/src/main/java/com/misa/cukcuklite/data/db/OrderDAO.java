package com.misa.cukcuklite.data.db;

import com.misa.cukcuklite.data.db.model.Order;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * - @created_by Hoàng Hiệp on 3/27/2019
 */
@Dao
public interface OrderDAO {
    @Query("SELECT * FROM orders")
    List<Order> getAllOrder();

    @Insert
    long insertOrder(Order unit);

    @Delete
    void deleteOrder(Order unit);

    @Update
    void updateOrder(Order unit);
}