package com.ade.fun.cg.utils;

/**
 *
 * Created by liyang on 2017/1/12.
 */
public class StringUtils {

    public static boolean isPositive(String... values) {
        if (null == values) return false;
        for (String value : values)
            if (null == value || value.trim().length() == 0) return false;
        return true;
    }

}
