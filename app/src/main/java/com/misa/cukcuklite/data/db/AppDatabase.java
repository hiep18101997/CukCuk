package com.misa.cukcuklite.data.db;

import com.misa.cukcuklite.data.db.model.Dish;
import com.misa.cukcuklite.data.db.model.Order;
import com.misa.cukcuklite.data.db.model.Unit;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * ‐ Mục đích Class : ra lớp để kế thừa lại Roomdatabase
 * <p>
 * ‐ @created_by dhhiep on 3/22/2019
 */
@Database(entities = {Dish.class, Unit.class, Order.class}, version = 3)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DishDAO mDishDAO();

    public abstract UnitDAO mUnitDAO();

    public abstract OrderDAO mOrderDAO();
}
