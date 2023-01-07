package com.zeewain.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

    public static String formatDateToString(Date date) {
        return formatDateToString(date, null);
    }

    public static String formatDateToString(Date date, String format) {
        SimpleDateFormat formatter;
        if(format == null)
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        else
            formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static Date formatDateStringToDate(String dateString, String format) {
        SimpleDateFormat formatter;
        if(format == null)
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        else
            formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String formatToTimeString(long millionSeconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millionSeconds);
        return simpleDateFormat.format(c.getTime());
    }
}
