package com.misa.cukcuklite.data.db.model;


import java.io.Serializable;

public class DishOrder implements Serializable {
    private Dish mDish;
    private int count;

    public DishOrder(Dish dish, int count) {
        mDish = dish;
        this.count = count;
    }

    public Dish getDish() {
        return mDish;
    }

    public void setDish(Dish dish) {
        mDish = dish;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "DishOrder{" +
                "mDish=" + mDish +
                ", count=" + count +
                '}';
    }
}
