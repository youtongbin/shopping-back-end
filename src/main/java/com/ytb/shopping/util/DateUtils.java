package com.ytb.shopping.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateUtils {

    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间转字符串
     */
    public static String dateToStr(Date date,String format){

        DateTime dateTime = new DateTime(date);

        return dateTime.toString(format);

    }

    public static String dateToStr(Date date){

        DateTime dateTime = new DateTime(date);

        return dateTime.toString(STANDARD_FORMAT);

    }

    /**
     * 字符串转时间
     */
    public static Date strToDate(String str){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);

        DateTime dateTime = dateTimeFormatter.parseDateTime(str);

        return dateTime.toDate();
    }

    public static Date strToDate(String str,String format){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(format);

        DateTime dateTime = dateTimeFormatter.parseDateTime(str);

        return dateTime.toDate();
    }

}
