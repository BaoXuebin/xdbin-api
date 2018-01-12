package com.xdbin.utils;

import com.xdbin.config.DicConstants;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: baoxuebin
 * Date: 2017/9/11
 */
public class ConvertUtil {

    public static long parseLong(String s, long _default) {
        long l = _default;
        if (!StringUtils.isEmpty(s))
            l = Long.parseLong(s);
        return l;
    }

    public static  int parseInteger(String s, int _default) {
        int i = _default;
        if (!StringUtils.isEmpty(s))
            i = Integer.parseInt(s);
        return i;
    }

    public static List<String> getTagNames(String tagStr) {
        if (StringUtils.isEmpty(tagStr)) return Collections.emptyList();

        String[] tags = tagStr.split(",");
        List<String> tagList = null;
        if (!StringUtils.isEmpty(tags) && tags.length > 0) {
            tagList = new ArrayList<>(tags.length);
            for (String s : tags) {
                String t = DicConstants.getInstance().getTagMap().get(ConvertUtil.parseLong(s, -1));
                if (!StringUtils.isEmpty(t) && !tagList.contains(t)) {
                    tagList.add(t);
                }
            }
        }
        return tagList;
    }

    public static String parseDatetoString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    public static String getRandomStr() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
