package com.star.app.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description：数据检测类
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-3-28  17:21
 */
public class ValueUtil {

    public static boolean isListNotEmpty(List<?> noteList) {
        return null != noteList && noteList.size() > 0;
    }

    public static boolean isListEmpty(List<?> noteList) {
        return null == noteList || noteList.size() == 0;
    }

    public static boolean isStrEmpty(String value) {
        if (null == value || "".equals(value.trim())) {
            return true;
        } else {
            // 判断是否全是全角空格
            value = value.replaceAll(" ", "").trim();
            if (null == value || "".equals(value.trim())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotEmpty(Object object) {// 不为空方�?
        return null != object;
    }

    public static boolean isEmpty(Object object) {// 为空方法
        return null == object;
    }

    public static boolean isStrNotEmpty(String value) {
        if (null == value || "".equals(value.trim())) {
            return false;
        } else {
            // 判断是否全是全角空格
            value = value.replaceAll(" ", "").trim();
            if (null == value || "".equals(value.trim())) {
                return false;
            }
        }
        return true;
    }

    //Double保留两位小数
    public static String doubleFormat(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.toString();
    }






    public static String deletePerCent(String str){
        if (str.contains("%")) {
            return str.substring(0, str.indexOf("%"));
        } else {
            return str;
        }
    }
    /**
     * 格式化空判断
     * @param str
     * @return
     */
    public static float strToFloat(String str){
        if (str == null || str.equals("-")|| str.equals("- -")) {
            return 0;
        }
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("数据异常");
        }
    }

    //匹配正负号
    public static boolean matchAddSubMark(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    //判断末尾是小数点
    public static boolean matchFinishPoint(String str) {

        Pattern pattern = Pattern.compile("^[+\\-]+([1-9][0-9]*)+(.[0-9]{1,})?\\.$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    //去掉小数点后多余的0
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
}
