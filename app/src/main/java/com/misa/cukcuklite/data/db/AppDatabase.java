package com.misa.cukcuklite.data.db;

import com.misa.cukcuklite.data.db.model.Dish;
import com.misa.cukcuklite.data.db.model.Unit;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * ‐ Mục đích Class : ra lớp để kế thừa lại Roomdatabase
 * <p>
 * ‐ @created_by dhhiep on 3/22/2019
 */
@Database(entities = {Dish.class, Unit.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DishDAO mDishDAO();

    public abstract UnitDAO mUnitDAO();
}
