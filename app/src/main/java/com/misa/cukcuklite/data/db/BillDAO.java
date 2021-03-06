package com.misa.cukcuklite.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.misa.cukcuklite.data.model.Bill;
import java.util.Date;
import java.util.List;

/**
 * - @created_by Hoàng Hiệp on 3/27/2019
 */
@Dao
public interface BillDAO {

  @Insert
  void saveBill(Bill bill);

  @Query("SELECT * FROM bills WHERE date BETWEEN :from AND :to")
  List<Bill> getBillBetweenDate(Date from, Date to);

  @Query("SELECT * FROM bills")
  List<Bill> getAllBill();

  @Query("SELECT COUNT(*) FROM bills")
  int getBillCount();

}