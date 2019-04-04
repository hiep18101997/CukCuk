package com.misa.cukcuklite.data.db.model;

import com.misa.cukcuklite.data.db.Converters;

import java.io.Serializable;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "orders")
public class Order implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "table")
    private int mNumberTable;
    @ColumnInfo(name = "person")
    private int mNumberPerson;
    @TypeConverters(Converters.class)
    @ColumnInfo(name = "dishes")
    private List<DishOrder> mListDish;

    public Order(int mNumberTable, int mNumberPerson, List<DishOrder> mListDish) {
        this.mNumberTable = mNumberTable;
        this.mNumberPerson = mNumberPerson;
        this.mListDish = mListDish;
    }

    public Order(Builder builder) {
        mNumberPerson = builder.mNumberPerson;
        mNumberTable = builder.mNumberTable;
        mListDish = builder.mListDish;
        id = builder.mId;
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

    public List<DishOrder> getListDish() {
        return mListDish;
    }

    public void setListDish(List<DishOrder> listDish) {
        mListDish = listDish;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", mNumberTable=" + mNumberTable +
                ", mNumberPerson=" + mNumberPerson +
                ", mListDish=" + mListDish +
                '}';
    }

    public static class Builder {
        private int mId;
        private int mNumberTable;
        private int mNumberPerson;
        private List<DishOrder> mListDish;

        public Builder setId(int id) {
            mId = id;
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

        public Builder setListDish(List<DishOrder> listDish) {
            mListDish = listDish;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
