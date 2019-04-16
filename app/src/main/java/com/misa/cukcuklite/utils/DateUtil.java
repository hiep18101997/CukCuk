package com.misa.cukcuklite.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

  public static Date[] getThisWeek() {
    Date[] dateArr = new Date[2];
    Calendar calendar = Calendar.getInstance();
    Date toDate = calendar.getTime();
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    Date fromDate = calendar.getTime();
    dateArr[0] = fromDate;
    dateArr[1] = toDate;
    return dateArr;
  }

  public static Date[] getToday() {
    Date[] dateArr = new Date[2];
    Calendar calendar = Calendar.getInstance();
    Date toDate = calendar.getTime();
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    Date fromDate = calendar.getTime();
    dateArr[0] = fromDate;
    dateArr[1] = toDate;
    return dateArr;
  }

  public static Date[] getThisYear() {
    Date[] dateArr = new Date[2];
    Calendar calendar = Calendar.getInstance();
    Date toDate = calendar.getTime();
    calendar.set(Calendar.DAY_OF_YEAR, 1);
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    Date fromDate = calendar.getTime();
    dateArr[0] = fromDate;
    dateArr[1] = toDate;
    return dateArr;
  }

  public static Date[] getYesterday() {
    Date[] dateArr = new Date[2];
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, -1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    Date fromDate = calendar.getTime();
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    Date toDate = calendar.getTime();
    dateArr[0] = fromDate;
    dateArr[1] = toDate;
    return dateArr;
  }

  public static Date[] getThisMonth() {
    Date[] dateArr = new Date[2];
    Calendar calendar = Calendar.getInstance();
    Date toDate = calendar.getTime();
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    Date fromDate = calendar.getTime();
    dateArr[0] = fromDate;
    dateArr[1] = toDate;
    return dateArr;
  }

  public static Date[] getDate(int dayOfMonth, int monthOfYear, int year) {
    Date[] dateArr = new Date[2];
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    calendar.set(Calendar.MONTH, monthOfYear - 1);
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    Date fromDate = calendar.getTime();
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    Date toDate = calendar.getTime();
    dateArr[0] = fromDate;
    dateArr[1] = toDate;
    return dateArr;
  }

  public static Date[] getLastWeek() {
    Date[] dateArr = new Date[2];
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.WEEK_OF_YEAR, -1);
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    Date fromDate = calendar.getTime();
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    Date toDate = calendar.getTime();
    dateArr[0] = fromDate;
    dateArr[1] = toDate;
    return dateArr;
  }

  public static Date[] getLastYear() {
    Date[] dateArr = new Date[2];
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.YEAR, -1);
    calendar.set(Calendar.DAY_OF_YEAR, 1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    Date fromDate = calendar.getTime();
    calendar.add(Calendar.YEAR, +1);
    calendar.add(Calendar.DATE, -1);
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    Date toDate = calendar.getTime();
    dateArr[0] = fromDate;
    dateArr[1] = toDate;
    return dateArr;
  }

  public static Date[] getLastMonth() {
    Date[] dateArr = new Date[2];
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MONTH, -1);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    Date fromDate = calendar.getTime();
    calendar.add(Calendar.MONTH, 1);
    calendar.add(Calendar.DATE, -1);
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    Date toDate = calendar.getTime();
    dateArr[0] = fromDate;
    dateArr[1] = toDate;
    return dateArr;
  }
}
