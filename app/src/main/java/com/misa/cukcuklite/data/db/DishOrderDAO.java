package com.misa.cukcuklite.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.misa.cukcuklite.data.model.DishOrder;
import java.util.List;

/**
 * - @created_by Hoàng Hiệp on 3/27/2019
 */
@Dao
public interface DishOrderDAO {

  @Query("SELECT * FROM dish_order")
  List<DishOrder> getAllDishOrder();

  @Insert
  void insertDishOrder(DishOrder dishOrder);

  @Delete
  void deleteDishOrder(DishOrder dishOrder);

  @Update
  void updateDishOrder(DishOrder dishOrder);

  @Query("SELECT * FROM dish_order WHERE order_id=:orderId")
  List<DishOrder> getDishOrderByOrderId(int orderId);

  @Query("SELECT * FROM dish_order WHERE dish_id=:id")
  DishOrder getDishOrderByDishId(int id);
}