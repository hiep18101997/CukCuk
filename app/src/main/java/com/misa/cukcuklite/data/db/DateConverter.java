package com.misa.cukcuklite.data.db;

import androidx.room.TypeConverter;
import java.util.Date;

/**
 * - Mục đích Class : Hàm chuyển đổi long <=> Date trong Room - @created_by Hoàng Hiệp on 4/12/2019
 */
public class DateConverter {

  @TypeConverter
  public static Date fromTimestamp(Long value) {
    return value == null ? null : new Date(value);
  }

  @TypeConverter
  public static Long dateToTimestamp(Date date) {
    return date == null ? null : date.getTime();
  }
}