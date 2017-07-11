package com.sula.util;

import java.math.BigDecimal;

/**
 * Created by jaydenwu on 11/06/2017.
 */
public class BigDecimalUtils {
    /**
     * 默认保留位：2
     */
    private static int 	DEFAULT_SCALE = 2;
    /**
     * 默认四舍五入规则为：向上舍入
     */
    private static int DEFAULT_ROUND = BigDecimal.ROUND_HALF_UP;
    /**
     * 加法运算
     */
    public static String add(String v1,String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).toString();
    }

    /**
     * 除法运算<br>
     */
    public static String div(String v1, String v2, int scale, int round) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }

        if (ValidateHelper.isEmpty(scale)) {
            scale = DEFAULT_SCALE;
        }

        if (ValidateHelper.isEmpty(round)) {
            round = DEFAULT_ROUND;
        }

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 比较两个数<br>
     */
    public static int compareTo(String v1,String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.compareTo(b2);
    }

    /**
     * 返回较小数
     */
    public static String returnMin(String v1,String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.min(b2).toString();
    }

    /**
     * 返回较大数
     */
    public static String returnMax(String v1,String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.max(b2).toString();
    }

    /**
     * 处理BigDecimal数据，保留scale位小数
     */
    public static BigDecimal getValue(BigDecimal value,int scale){
        if(!ValidateHelper.isEmpty(value)){
            return value.setScale(scale, BigDecimal.ROUND_HALF_UP);
        }
        return value;
    }

    /**
     * 将object转换为Bigdecimal
     */
    public static BigDecimal getBigDecimal(Object value){
        BigDecimal resultValue = new BigDecimal(0);
        if(value instanceof String){
            resultValue =  new BigDecimal((String)value);
        }
        else if(value instanceof Integer){
            resultValue =  new BigDecimal((Integer)value);
        }
        else if(value instanceof Long){
            resultValue =  new BigDecimal((Long)value);
        }
        else if(value instanceof Double){
            resultValue =  new BigDecimal((Double)value);
        }
        else{
            resultValue = (BigDecimal) value;
        }

        return resultValue;
    }


    /**
     * 将object转换为Bigdecimal,若object为空，则返回resultValue
     */
    public static BigDecimal getBigDecimal(Object value,BigDecimal resultValue){
        if(ValidateHelper.isEmpty(value)){
            return resultValue;
        }

        resultValue = getBigDecimal(resultValue);

        return resultValue;
    }

    /**
     * 将BigDecimal 转换成Long
     */
    public static Long bigDecimalToLong(BigDecimal value){
        if(value != null){
            return new Long(value.longValue());
        }
        return null;
    }

    /**
     * 将BigDecimal 转换成integer
     */
    public static Integer bigDecimalToInteger(BigDecimal value){
        if(value != null){
            return new Integer(value.intValue());
        }
        return null;
    }
}
