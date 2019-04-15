package com.misa.cukcuklite.data.model;

import java.io.Serializable;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * - Mục đích Class :Trừu tượng đối tượng gọi món
 * - @created_by Hoàng Hiệp on 4/12/2019
 */
@Entity(tableName = "orders")
public class Order implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "table")
    private int mNumberTable;
    @ColumnInfo(name = "person")
    private int mNumberPerson;
    @ColumnInfo(name = "is_pay")
    private boolean isPay;
    @Ignore
    private List<DishOrder> mOrders;

    public Order(int mNumberTable, int mNumberPerson) {
        this.mNumberTable = mNumberTable;
        this.mNumberPerson = mNumberPerson;
    }

    public Order(Builder builder) {
        mNumberPerson = builder.mNumberPerson;
        mNumberTable = builder.mNumberTable;
        id = builder.mId;
        mOrders = builder.mOrders;
        isPay = builder.isPay;
    }

    public boolean isPay() {
        return isPay;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }

    public List<DishOrder> getOrders() {
        return mOrders;
    }

    public void setOrders(List<DishOrder> orders) {
        mOrders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberTable() {
        return mNumberTable;
    }

    public void setNumberTable(int numberTable) {
        mNumberTable = numberTable;
    }

    public int getNumberPerson() {
        return mNumberPerson;
    }

    public void setNumberPerson(int numberPerson) {
        mNumberPerson = numberPerson;
    }

    public static class Builder {
        private int mId;
        private int mNumberTable;
        private int mNumberPerson;
        private boolean isPay;
        private List<DishOrder> mOrders;

        public Builder setPay(boolean pay) {
            isPay = pay;
            return this;
        }

        public Builder setId(int id) {
            mId = id;
            return this;
        }

        public Builder setOrders(List<DishOrder> orders) {
            mOrders = orders;
            return this;
        }

        public Builder setNumberTable(int numberTable) {
            mNumberTable = numberTable;
            return this;
        }

        public Builder setNumberPerson(int numberPerson) {
            mNumberPerson = numberPerson;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
