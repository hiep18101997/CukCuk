package com.misa.cukcuklite.data.db;

import com.misa.cukcuklite.data.db.model.Unit;

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
public interface UnitDAO {
    @Query("SELECT * FROM units")
    List<Unit> getAllUnit();

    @Insert
    void insertUnit(Unit unit);

    @Delete
    void deleteUnit(Unit unit);

    @Update
    void updateUnit(Unit unit);

    @Query("SELECT * FROM units WHERE name=:text")
    Unit getUnitByName(String text);
}