package com.misa.cukcuklite.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date[] getThisWeek() {
        Date[] dateArr = new Date[2];
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DATE, -3);
        Date monday = calendar.getTime();
        dateArr[0] = monday;
        dateArr[1] = today;
        return dateArr;
    }
}
