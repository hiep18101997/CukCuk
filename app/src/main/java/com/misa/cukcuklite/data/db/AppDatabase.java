package com.misa.cukcuklite.data.db;

import com.misa.cukcuklite.data.model.Dish;
import com.misa.cukcuklite.data.model.DishOrder;
import com.misa.cukcuklite.data.model.Order;
import com.misa.cukcuklite.data.model.Unit;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * ‐ Mục đích Class : ra lớp để kế thừa lại Roomdatabase
 * <p>
 * ‐ @created_by dhhiep on 3/22/2019
 */
@Database(entities = {Dish.class, Unit.class, Order.class, DishOrder.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DishDAO mDishDAO();

    public abstract UnitDAO mUnitDAO();

    public abstract OrderDAO mOrderDAO();

    public abstract DishOrderDAO mDishOrderDAO();
}
