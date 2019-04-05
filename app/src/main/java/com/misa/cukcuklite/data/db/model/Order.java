package com.misa.cukcuklite.data.db.model;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class Order implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "table")
    private int mNumberTable;
    @ColumnInfo(name = "person")
    private int mNumberPerson;

    public Order(int mNumberTable, int mNumberPerson) {
        this.mNumberTable = mNumberTable;
        this.mNumberPerson = mNumberPerson;
    }

    public Order(Builder builder) {
        mNumberPerson = builder.mNumberPerson;
        mNumberTable = builder.mNumberTable;
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

    public static class Builder {
        private int mId;
        private int mNumberTable;
        private int mNumberPerson;

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

        public Order build() {
            return new Order(this);
        }
    }
}
