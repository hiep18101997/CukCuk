package com.misa.cukcuklite.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.misa.cukcuklite.data.model.Order;
import java.util.List;

/**
 * - @created_by Hoàng Hiệp on 3/27/2019
 */
@Dao
public interface OrderDAO {

  @Query("SELECT * FROM orders WHERE is_pay=0")
  List<Order> getAllOrderNotPay();

  @Insert
  long insertOrder(Order unit);

  @Delete
  void deleteOrder(Order unit);

  @Update
  void updateOrder(Order unit);

  @Query("SELECT * FROM orders WHERE id=:id")
  Order getOrderById(int id);
}