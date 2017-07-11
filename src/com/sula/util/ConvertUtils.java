package com.sula.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ConvertUtils {
    /**
     * 字符串转换为int
     */
    public static int strToInt(String str, int defaultValue) {
        try {
            defaultValue = Integer.parseInt(str);
        } catch (Exception localException) {
        }
        return defaultValue;
    }

    /**
     * String转换为long
     */
    public static long strToLong(String str, long defaultValue) {
        try {
            defaultValue = Long.parseLong(str);
        } catch (Exception localException) {
        }
        return defaultValue;
    }

    /**
     * 字符串转换为float
     */
    public static float strToFloat(String str, float defaultValue) {
        try {
            defaultValue = Float.parseFloat(str);
        } catch (Exception localException) {
        }
        return defaultValue;
    }

    /**
     * String转换为Double
     */
    public static double strToDouble(String str, double defaultValue) {
        try {
            defaultValue = Double.parseDouble(str);
        } catch (Exception localException) {
        }
        return defaultValue;
    }

    /**
     * 字符串转换日期
     */
    public static java.util.Date strToDate(String str,java.util.Date defaultValue) {
        return strToDate(str, "yyyy-MM-dd HH:mm:ss", defaultValue);
    }

    /**
     * 字符串转换为指定格式的日期
     */
    public static java.util.Date strToDate(String str, String format,java.util.Date defaultValue) {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        try {
            defaultValue = fmt.parse(str);
        } catch (Exception localException) {
        }
        return defaultValue;
    }

    /**
     * 日期转换为字符串
     */
    public static String dateToStr(java.util.Date date, String defaultValue) {
        return dateToStr(date, "yyyy-MM-dd HH:mm:ss", defaultValue);
    }

    /**
     * 日期转换为指定格式的字符串
     */
    public static String dateToStr(java.util.Date date, String format, String defaultValue) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            defaultValue = sdf.format(date);
        } catch (Exception localException) {
        }
        return defaultValue;
    }

    /**
     * util date 转换为 sqldate
     */
    public static java.sql.Date dateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * sql date 转换为 util date
     */
    public static java.util.Date sqlDateToDate(java.sql.Date date) {
        return new java.util.Date(date.getTime());
    }

    /**
     * date 转换为 timestamp
     */
    public static Timestamp dateToSqlTimestamp(java.util.Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * timestamp 转换为date
     */
    public static java.util.Date qlTimestampToDate(Timestamp date) {
        return new java.util.Date(date.getTime());
    }
}
