package com.xdbin.common.utils;

import org.springframework.util.StringUtils;

public abstract class SqlUtil {

    public static String like(String s) {
        return StringUtils.isEmpty(s) ? null : "%" + s + "%";
    }

    public static String likeLeft(String s) {
        return StringUtils.isEmpty(s) ? null : "%" + s;
    }

    public static String likeRight(String s) {
        return StringUtils.isEmpty(s) ? null : s + "%";
    }
}
