package com.quinn.app.common.util;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.quinn.app.common.constans.BaseEnum;

/**
 * 判断工具包
 *
 * @author Quinn
 */
public class CommonUtils {

    public static boolean isEmpty(Object value) {
        if (null == value) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Object value) {
        if (null != value) {
            return true;
        }
        return false;
    }

    public static boolean isBlank(Object value) {
        if (null == value || value.toString().trim().equals("")) {
            return true;
        }
        return false;
    }

    public static boolean isNotBlank(Object value) {
        if (isBlank(value)) {
            return false;
        }
        return true;
    }

    public static boolean isNullStr(Object value) {
        if (null == value || value.toString().trim().equals("")
                || value.toString().trim().toLowerCase().equals("null")) {
            return true;
        }
        return false;
    }

    public static boolean isNotNullStr(Object value) {
        if (isNullStr(value)) {
            return false;
        }
        return true;
    }

    public static boolean isNumber(Object value) {
        if (isNotNullStr(value)) {
            return value.toString().matches("^\\d+$");
        }
        return false;
    }

    /**
     * 判断是否是金额，保留两位小数
     *
     * @param value
     * @return
     */
    public static boolean isMoney(Object value) {
        if (isBlank(value)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[\\-\\+]?(-)?[0-9]+(.[0-9]{1,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(value.toString());
        return match.matches();
    }

    public static boolean isBigDecimal(Object value) {
        if (isBlank(value)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[\\-\\+]?(-)?[0-9]+(.[0-9]+)?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(value.toString());
        return match.matches();
    }

    public static <T extends Object> boolean isNotEmptyList(Collection<T> c) {
        if (null != c && c.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是可用枚举
     **/
    public static <T extends Enum<T>> boolean isEnum(Class<T> c, String name) {
        try {
            Enum.valueOf(c, name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否是可用枚举
     **/
    public static boolean isEnum(BaseEnum[] bb, String name) {
        for (BaseEnum b : bb) {
            if (b.name().equals(name)) {
                return true;
            }
        }
        return false;

    }

    /**
     * 判断是否是可用枚举
     **/
    public static boolean isEnum(BaseEnum[] bb, Integer code) {
        for (BaseEnum b : bb) {
            if (b.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    public static String getStringTime(Date date, String patter) {
        SimpleDateFormat format = new SimpleDateFormat(patter);
        return format.format(date);
    }

    public static String getUUIDString() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 删除map中值为null或者空字符的key
     **/
    public static void removeMapEmptyKey(Map<String, Object> map) {
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            if (CommonUtils.isBlank(map.get(entry.getKey()))) {
                it.remove();
            }
        }
    }



    public static void main(String[] args) {
        System.out.println(isBigDecimal("23423.234324"));
    }

}
