package com.jh.jsuk.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: XuChuRuo
 * @date: 2018/6/4 10:20
 */
public class DatecConvertUtils {

    /**
     * 日期转换成字符串
     *
     * @param date
     * @return str
     */
    public static String DateToStr(LocalDate date) {

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //时间转为字符串
        String str = date.format(f);
        return str;
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static LocalTime StrToDate(String str) {

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalTime date = LocalTime.parse(str, f);

        return date;
    }

    public static String dateFormat(String str) throws ParseException {
        Date date1 = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss z", Locale.ENGLISH).parse(str);
        return new SimpleDateFormat("yyyy-MM-dd").format(date1);
    }

}
