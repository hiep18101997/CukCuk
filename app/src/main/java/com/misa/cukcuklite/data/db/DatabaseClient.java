package com.misa.cukcuklite.data.db;

import android.content.Context;

import androidx.room.Room;

/**
 * ‐ Mục đích Class : tạo ra đối tượng tương tác với database
 * <p>
 * ‐ @created_by dhhiep on 3/22/2019
 */
public class DatabaseClient {
    private static DatabaseClient mInstance;
    private Context mCtx;
    private AppDatabase appDatabase;

    private DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "CukCukLite").fallbackToDestructiveMigration().build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}