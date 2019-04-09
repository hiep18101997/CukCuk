package com.misa.cukcuklite.data.model;

import com.misa.cukcuklite.data.db.DateConverter;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "bills")
public class Bill {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "order_id")
    private int orderId;
    @ColumnInfo(name = "date")
    @TypeConverters({DateConverter.class})
    private Date date;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @ColumnInfo(name = "amount")
    private long amount;
    public Bill() {
    }

    public Bill(int orderId, Date date) {
        this.orderId = orderId;
        this.date = date;
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
