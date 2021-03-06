package com.misa.cukcuklite.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.misa.cukcuklite.data.model.Unit;
import java.util.List;

/**
 * - @created_by Hoàng Hiệp on 3/27/2019
 */
@Dao
public interface UnitDAO {

  @Query("SELECT * FROM units")
  List<Unit> getAllUnit();

  @Insert
  long insertUnit(Unit unit);

  @Delete
  void deleteUnit(Unit unit);

  @Update
  void updateUnit(Unit unit);

  @Query("SELECT * FROM units WHERE name=:text")
  Unit getUnitByName(String text);

  @Query("SELECT * FROM units WHERE id=:id")
  Unit getUnitById(int id);
}