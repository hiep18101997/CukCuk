package com.misa.cukcuklite.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.misa.cukcuklite.data.db.DateConverter;
import java.util.Date;

/**
 * - Mục đích Class :Trừ tượng đối tượng Hóa đơn - @created_by Hoàng Hiệp on 4/12/2019
 */
@Entity(tableName = "bills")
public class Bill {

  @PrimaryKey(autoGenerate = true)
  private int id;
  @ColumnInfo(name = "order_id")
  private int orderId;
  @ColumnInfo(name = "date")
  @TypeConverters({DateConverter.class})
  private Date date;
  @ColumnInfo(name = "amount")
  private long amount;

  public Bill() {
  }

  public Bill(int orderId, Date date) {
    this.orderId = orderId;
    this.date = date;
  }

  public long getAmount() {
    return amount;
  }

  public void setAmount(long amount) {
    this.amount = amount;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
