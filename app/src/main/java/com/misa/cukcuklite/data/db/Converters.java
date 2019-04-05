package com.misa.cukcuklite.data.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.misa.cukcuklite.data.db.model.DishOrder;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import androidx.room.TypeConverter;

public class Converters {

    static Gson gson = new Gson();

    @TypeConverter
    public static List<DishOrder> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<DishOrder>>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<DishOrder> someObjects) {
        return gson.toJson(someObjects);
    }
}