package com.misa.cukcuklite.data.db;

import com.misa.cukcuklite.data.model.Dish;

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
public interface DishDAO {
    @Query("SELECT * FROM dishes")
    List<Dish> getAllDish();

    @Insert
    void insertDish(Dish dish);

    @Delete
    void deleteDish(Dish dish);

    @Update
    void updateDish(Dish dish);

    @Query("SELECT * FROM dishes WHERE unit_id=:unitId")
    Dish getDishByUnitId(int unitId);

    @Query("SELECT * FROM dishes WHERE id=:id")
    Dish getDishById(int id);
}