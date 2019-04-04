package com.misa.cukcuklite.data.db.model;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * ‐ Mục đích Class : Trừu tượng đối tượng món
 * <p>
 * ‐ @created_by dhhiep on 3/25/2019
 */
@Entity(tableName = "dishes")
public class Dish  implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String mName;
    @ColumnInfo(name = "cost")
    private long mCost;
    @ColumnInfo(name = "unit")
    private String mUnit;
    @ColumnInfo(name = "color")
    private int mColor;
    @ColumnInfo(name = "icon")
    private String mIcon;
    @ColumnInfo(name = "is_sell")
    private boolean isSell;

    public Dish(int id, String name, long cost, String unit, int color, String icon, boolean isSell) {
        this.id = id;
        mName = name;
        mCost = cost;
        mUnit = unit;
        mColor = color;
        mIcon = icon;
        this.isSell = isSell;
    }

    private Dish(Builder builder) {
        mName = builder.mName;
        mCost = builder.mCost;
        mUnit = builder.mUnit;
        mColor = builder.mColor;
        mIcon = builder.mIcon;
        isSell = builder.isSell;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public long getCost() {
        return mCost;
    }

    public void setCost(long cost) {
        mCost = cost;
    }

    public String getUnit() {
        return mUnit;
    }

    public void setUnit(String unit) {
        mUnit = unit;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public boolean isSell() {
        return isSell;
    }

    public void setSell(boolean sell) {
        isSell = sell;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", mName='" + mName + '\'' +
                ", mCost=" + mCost +
                ", mUnit='" + mUnit + '\'' +
                ", mColor=" + mColor +
                ", mIcon='" + mIcon + '\'' +
                ", isSell=" + isSell +
                '}';
    }

    public static class Builder {
        private String mName;
        private long mCost;
        private String mUnit;
        private int mColor;
        private String mIcon;
        private boolean isSell;

        public Builder setName(String name) {
            mName = name;
            return this;
        }

        public Builder setCost(long cost) {
            mCost = cost;
            return this;
        }

        public Builder setUnit(String unit) {
            mUnit = unit;
            return this;
        }

        public Builder setColor(int color) {
            mColor = color;
            return this;
        }

        public Builder setIcon(String icon) {
            mIcon = icon;
            return this;
        }

        public Builder setSell(boolean sell) {
            isSell = sell;
            return this;
        }

        public Dish build() {
            return new Dish(this);
        }
    }
}
