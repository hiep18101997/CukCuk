package com.misa.cukcuklite.data.db.model;

import java.util.HashMap;

public class PendingOrder {
    private int mNumberTable;
    private int mNumberPerson;
    private HashMap<Dish, Integer> mListDish;

    public PendingOrder(int mNumberTable, int mNumberPerson, HashMap<Dish, Integer> mListDish) {
        this.mNumberTable = mNumberTable;
        this.mNumberPerson = mNumberPerson;
        this.mListDish = mListDish;
    }

    public PendingOrder(Builder builder) {
        mNumberPerson = builder.mNumberPerson;
        mNumberTable = builder.mNumberTable;
        mListDish = builder.mListDish;
    }

    public int getmNumberTable() {
        return mNumberTable;
    }

    public void setmNumberTable(int mNumberTable) {
        this.mNumberTable = mNumberTable;
    }

    public int getmNumberPerson() {
        return mNumberPerson;
    }

    public void setmNumberPerson(int mNumberPerson) {
        this.mNumberPerson = mNumberPerson;
    }

    public HashMap<Dish, Integer> getmListDish() {
        return mListDish;
    }

    public void setmListDish(HashMap<Dish, Integer> mListDish) {
        this.mListDish = mListDish;
    }

    public static class Builder {
        private int mNumberTable;
        private int mNumberPerson;
        private HashMap<Dish, Integer> mListDish;

        public Builder setmNumberTable(int mNumberTable) {
            this.mNumberTable = mNumberTable;
            return this;
        }

        public Builder setmNumberPerson(int mNumberPerson) {
            this.mNumberPerson = mNumberPerson;
            return this;
        }

        public Builder setmListDish(HashMap<Dish, Integer> mListDish) {
            this.mListDish = mListDish;
            return this;
        }
        public PendingOrder build() {
            return new PendingOrder(this);
        }
    }
}
