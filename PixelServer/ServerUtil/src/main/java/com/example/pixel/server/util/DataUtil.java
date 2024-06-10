package com.example.pixel.server.util;

import java.util.Calendar;
import java.util.Date;

public class DataUtil {

    public static Date addHoursToJavaUtilDate(int hours) {
        return addHoursToJavaUtilDate(new Date(), hours);
    }

    public static Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

}
