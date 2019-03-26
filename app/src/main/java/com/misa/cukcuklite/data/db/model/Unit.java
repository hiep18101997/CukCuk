package com.misa.cukcuklite.data.db.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * - Mục đích Class : Trừu tượng đối tượng đơn vị
 * <p>
 * - @created_by Hoàng Hiệp on 3/27/2019
 */
@Entity(tableName = "units")
public class Unit {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String mName;

    public Unit(String name) {
        mName = name;
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

    @Override
    public String toString() {
        return "Unit{" +
                "id=" + id +
                ", mName='" + mName + '\'' +
                '}';
    }
}
