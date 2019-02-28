package com.ytb.shopping.util;


import java.math.BigDecimal;

/**
 * 价格计算工具类
 */
public class BigDecimalUtils {

    /**
     * 加法
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal add(double d1,double d2){

        BigDecimal bd1 = new BigDecimal(String.valueOf(d1));
        BigDecimal bd2 = new BigDecimal(String.valueOf(d2));
        return bd1.add(bd2);

    }

    /**
     * 减法
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal sub(double d1,double d2){

        BigDecimal bd1 = new BigDecimal(String.valueOf(d1));
        BigDecimal bd2 = new BigDecimal(String.valueOf(d2));
        return bd1.subtract(bd2);

    }

    /**
     * 乘法
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal mul(double d1,double d2){

        BigDecimal bd1 = new BigDecimal(String.valueOf(d1));
        BigDecimal bd2 = new BigDecimal(String.valueOf(d2));
        return bd1.multiply(bd2);

    }

    /**
     * 除法,保留两位小数，四舍五入
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal div(double d1,double d2){

        BigDecimal bd1 = new BigDecimal(String.valueOf(d1));
        BigDecimal bd2 = new BigDecimal(String.valueOf(d2));
        return bd1.divide(bd2,2,BigDecimal.ROUND_HALF_UP);

    }

}
