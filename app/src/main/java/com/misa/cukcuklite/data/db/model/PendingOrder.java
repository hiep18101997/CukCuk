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

    public HashMap<Dish, Integer> getListDish() {
        return mListDish;
    }

    public void setListDish(HashMap<Dish, Integer> listDish) {
        mListDish = listDish;
    }

    public static class Builder {
        private int mNumberTable;
        private int mNumberPerson;
        private HashMap<Dish, Integer> mListDish;

        public Builder setNumberTable(int numberTable) {
            mNumberTable = numberTable;
            return this;
        }

        public Builder setNumberPerson(int numberPerson) {
            mNumberPerson = numberPerson;
            return this;
        }

        public Builder setListDish(HashMap<Dish, Integer> listDish) {
            mListDish = listDish;
            return this;
        }
    }
}
