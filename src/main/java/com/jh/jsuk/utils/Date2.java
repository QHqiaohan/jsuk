package com.jh.jsuk.utils;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Date2 extends Date {

    private static final long serialVersionUID = 1L;

    /**
     * 秒
     */
    public static final int SECOND = Calendar.SECOND;
    /**
     * 分
     */
    public static final int MINUTE = Calendar.MINUTE;
    /**
     * 小时
     */
    public static final int HOUR = Calendar.HOUR_OF_DAY;
    /**
     * 天
     */
    public static final int DAY = Calendar.DATE;
    /**
     * 月
     */
    public static final int MONTH = Calendar.MONTH;
    /**
     * 年
     */
    public static final int YEAR = Calendar.YEAR;

    /**
     * 所有备选的日期格式 可自行定义
     */
    /**
     * 2015-10-30
     */
    public static final String YYYYMMDD = "yyyy-MM-dd";
    /**
     * 2015-10-30 17:19
     */
    public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
    /**
     * 2015-10-30 17:19:58
     */
    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 20151030171958
     */
    public static final String YYYYMMDDHHMMSS_EX = "yyyyMMddHHmmss";
    /**
     * 2015年10月30日
     */
    public static final String YYYYMMDD_CN = "yyyy年MM月dd日";
    /**
     * 2015年10月30日 17时20分
     */
    public static final String YYYYMMDDHHMM_CN = "yyyy年MM月dd日HH时mm分";
    /**
     * 2015年10月30日 17时20分58秒
     */
    public static final String YYYYMMDDHHMMSS_CN = "yyyy年MM月dd日HH时mm分ss秒";
    /**
     * 30日17:28
     */
    public static final String DDHHMM_CN = "dd日HH:mm";
    /**
     * 17:22:17
     */
    public static final String HHMMSS = "HH:mm:ss";
    /**
     * 17:22
     */
    public static final String HHMM = "HH:mm";

    private Calendar calen;
    private SimpleDateFormat formatter;
    // 默认的日期格式不能随便修改
    private String pattern = YYYYMMDDHHMMSS;

    public Date2() {
    }

    /**
     * 以时间戳的方式初始数据
     */
    public Date2(Timestamp timestamp) {
        setTime(timestamp.getTime());
    }

    /**
     * 添加的时间
     */
    public Date2(int days) {
        addDay(days);
    }

    /**
     * date 转为date2
     */
    public Date2(Date date) {
        setDate(date);
    }

    /**
     * @param days    添加的时间
     * @param pattern 格式
     */
    public Date2(int days, String pattern) {
        setPattern(pattern);
    }

    /**
     * 日期格式
     */
    public Date2(String date) throws ParseException {
        parse(date, pattern);
    }

    /**
     * @param date    字符串日期
     * @param pattern 日期的格式
     */
    public Date2(String date, String pattern) throws ParseException {
        parse(date, pattern);
    }

    /**
     * 在需要使用时才初始化 节省内存
     */
    private Calendar getCalendar() {
        if (calen == null) {
            synchronized (this) {
                calen = Calendar.getInstance();
                calen.setTime(this);
            }
        }
        return calen;
    }

    /**
     * 在需要使用时才初始化 节省内存
     */
    private SimpleDateFormat getFormatter() {
        if (formatter == null) {
            synchronized (this) {
                formatter = new SimpleDateFormat();
            }
        }
        return formatter;
    }

    /**
     * 设置它的格式 建议调用时使用 SDate。提示
     */
    public Date2 setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    /**
     * date 转为 date2
     */
    public Date2 setDate(Date date) {
        setTime(date.getTime());
        return this;
    }

    /**
     * 添加时间 可选 分 小时 天 月 年
     */
    public Date2 add(int field, int amount) {
        getCalendar().add(field, amount);
        setTime(getCalendar().getTime().getTime());
        return this;
    }

    /**
     * 添加秒
     */
    public Date2 addSecond(int amount) {
        return add(SECOND, amount);
    }

    /**
     * 添加分
     */
    public Date2 addMinute(int amount) {
        return add(MINUTE, amount);
    }

    /**
     * 添加小时
     */
    public Date2 addHour(int amount) {
        return add(HOUR, amount);
    }

    /**
     * 添加天
     */
    public Date2 addDay(int amount) {
        return add(DAY, amount);
    }

    /**
     * 添加月
     */
    public Date2 addMonth(int amount) {
        return add(MONTH, amount);
    }

    /**
     * 添加月 (不精确)
     */
    public Date2 addMonth(double amount) {
        int month = (int) Math.floor(amount);
        int day = (int) ((amount - month) * 30);
        return addMonth(month).addDay(day);
    }

    /**
     * 添加周
     */
    public Date2 addWeek(int amount) {
        return addDay(amount * 7);
    }

    /**
     * 添加年
     */
    public Date2 addYear(int amount) {
        return add(YEAR, amount);
    }

    /**
     * 解析日期 这里的格式不会修改 默认的格式
     */
    public Date2 parse(String date, String pattern) throws ParseException {
        getFormatter().applyPattern(pattern);
        setTime(getFormatter().parse(date).getTime());
        return this;
    }

    /**
     * 获取当天是星期几 0为 星期天 星期几 即为几
     */
    public int getWeek() {
        return getCalendar().get(Calendar.DAY_OF_WEEK) - 1;
    }


    public String getWeekCN() {
        String[] weks = new String[]{"日", "一", "二", "三", "四", "五", "六"};
        return "星期" + weks[getWeek()];
    }

    /**
     * 是否为周末
     */
    public boolean isWeekend() {
        int d = getWeek();
        return d == 6 || d == 0;
    }

    /**
     * 是否为工作日
     */
    public boolean isWeekday() {
        return !isWeekend();
    }

    /**
     * 判断是否在date前
     */
    public boolean isBefore(Date2 date) {
        return getTime() < date.getTime();
    }

    /**
     * 判断是否在date后
     */
    public boolean isAfter(Date2 date) {
        return !isBefore(date);
    }

    /**
     * 根据时间戳解析时间
     */
    public Date2 setTime2(long time) {
        setTime(time);
        return this;
    }

    /**
     * get format toString 都是同一个方法
     */
    public String get(String pattern) {
        return toString(pattern);
    }

    /**
     * 获取时间
     */
    public String get() {
        return toString();
    }

    /**
     * 获取时间
     */
    public String format(String pattern) {
        return toString(pattern);
    }

    /**
     * 获取时间
     */
    public String format() {
        return toString();
    }

    /**
     * 计算两个日期相差的天数
     */
    public int byDays(Date2 date) {
        return Math.round((date.getTime() - this.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * 计算两个日期相差的秒数
     */
    public int bySeconds(Date2 date) {
        return Math.round((date.getTime() - this.getTime()) / 1000);
    }

    /**
     * 获取时间
     */
    public String toString(String pattern) {
        setPattern(pattern);
        return toString();
    }

    @Override
    public String toString() {
        getFormatter().applyPattern(pattern);
        return getFormatter().format(this);
    }

    /**
     * 转为 字符串
     */
    public String to(String pattern) {
        return toString(pattern);
    }

    /**
     * 转为 yyyy-MM-dd
     */
    public String toYYYYMMDD() {
        return to(YYYYMMDD);
    }

    public String toYYYYMMDDHHMMSS() {
        return to(YYYYMMDDHHMMSS);
    }

    /**
     * 系统当前日期的 yyyy-MM-dd
     */
    public static String YYYYMMDD() {
        return new Date2().toYYYYMMDD();
    }

    /**
     * 系统当前日期的 yyyy-MM-dd hh:mm:ss
     */
    public static String YYYYMMDDHHMMSS() {
        return new Date2().toYYYYMMDDHHMMSS();
    }

    /**
     * 获取年月日时分秒
     */
    private int get(int field) {
        return getCalendar().get(field);
    }

    public int getYear2() {
        return get(YEAR);
    }

    public int getMonth2() {
        return get(MONTH);
    }

    /*
     * get the day of month
     */
    public int getDay2() {
        return get(DAY);
    }

    /**
     * 获取一月的一天
     */
    public int getDayOfMonth() {
        return getDay2();
    }

    /**
     * 获取时
     */
    public int getHour() {
        return get(HOUR);
    }

    /**
     * 获取分
     */
    public int getMinute() {
        return get(MINUTE);
    }

    /**
     * 获取秒
     */
    public int getSecond() {
        return get(SECOND);
    }

    /**
     * 设置 年月日时分秒
     */
    private Date2 set(int field, int value) {
        getCalendar().set(field, value);
        return setTime2(getCalendar().getTime().getTime());
    }

    /**
     * 设置年
     */
    public Date2 setYear2(int value) {
        set(YEAR, value);
        return this;
    }


    /**
     * 设置月
     */
    public Date2 setMonth2(int value) {
        set(MONTH, value);
        return this;
    }

    /**
     * 设置天
     */
    public Date2 setDay(int value) {
        return set(DAY, value);
    }

    /**
     * 设置天
     */
    public Date2 setDay2(int value) {
        return setDay(value);
    }

    /**
     * 设置天
     */
    public Date2 setDayOfMonth(int value) {
        return setDay(value);
    }

    /**
     * 设置小时 24 时制
     */
    public Date2 setHour(int value) {
        return set(HOUR, value);
    }

    /**
     * 设置小时24 时制
     */
    public Date2 setHour2(int value) {
        return setHour(value);
    }

    /**
     * 设置分
     */
    public Date2 setMinute(int value) {
        return set(MINUTE, value);
    }

    /**
     * 设置分
     */
    public Date2 setMinute2(int value) {
        return setMinute(value);
    }

    /**
     * 设置秒
     */
    public Date2 setSecond(int value) {
        return set(SECOND, value);
    }

    /**
     * 设置秒
     */
    public Date2 setSecond2(int value) {
        return setSecond(value);
    }

    /**
     * date的23点59分59秒
     */
    public Date2 end() {
        return setHour(23).setMinute(59).setSecond(59);
    }

    /**
     * date的0点0分0秒
     */
    public Date2 start() {
        return setHour(0).setMinute(0).setSecond(0);
    }


    /**
     * 可以直接转化为 2016-04-18 23:59:59
     */
    public String dbEnd() {
        return end().toYYYYMMDDHHMMSS();
    }

    /**
     * 可以直接转化为 2016-04-18 00:00:00
     */
    public String dbStart() {
        return start().toYYYYMMDDHHMMSS();
    }

    /**
     * 本月最后一天
     *
     * @return
     */
    public Date2 lastDayOfMonth() {
        return setDay2(1).addMonth(1).addDay(-1);
    }


    /**
     * 判断是否符合 yyyy-MM-dd的格式
     */
    public static boolean isMatch(String date) {
        return isMatch(date, YYYYMMDD);
    }

    /**
     * 判断是否符合 yyyy-MM-dd的格式
     */
    public static boolean isNotMatch(String date) {
        return !isMatch(date);
    }

    /**
     * 判断是否符合指定格式
     */
    public static boolean isMatch(String date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            formatter.setLenient(false);
            formatter.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 判断是否符合指定格式
     */
    public static boolean isNotMatch(String date, String pattern) {
        return !isMatch(date, pattern);
    }


    /**
     * 时间戳与当前时间相差
     */
    public static String by(long timeStamp) {
        String[] units = {"ms", "s", "m", "h", "day", "mon", "year"};
        int[] divides = {1000, 60, 60, 60, 24, 30, 12};
        long by = System.currentTimeMillis() - timeStamp;
        for (int i = 0; i < divides.length; i++) {
            if (by < divides[i])
                return by + units[i];
            else
                by /= divides[i];
        }
        return by + units[units.length - 1];
    }
}
