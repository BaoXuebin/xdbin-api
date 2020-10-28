package com.xdbin.utils;

import com.xdbin.config.DicConstants;
import com.xdbin.note.entity.FullNote;
import com.xdbin.tag.entity.Tag;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static List<Tag> getTagNames(String tagStr) {
        if (StringUtils.isEmpty(tagStr)) return Collections.emptyList();

        String[] tags = tagStr.split(",");
        List<Tag> tagList = null;
        if (!StringUtils.isEmpty(tags) && tags.length > 0) {
            tagList = new ArrayList<>(tags.length);
            for (String s : tags) {
                Long tagId = ConvertUtil.parseLong(s, -1);
                String t = DicConstants.getInstance().getTagMap().get(tagId);
                if (!StringUtils.isEmpty(t) && !tagList.contains(t)) {
                    tagList.add(new Tag(tagId, t));
                }
            }
        }
        return tagList;
    }

    public static String parseDatetoString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    public static String parseDateTimetoString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

    public static String getRandomStr() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String flat(List<String> list, String separator) {
        if (StringUtils.isEmpty(list) || list.isEmpty()) return null;
        StringBuffer result = new StringBuffer();
        list.forEach(l -> {
            result.append(l);
            result.append(separator);
        });
        return result.toString();
    }

    public static List<String> split(String str, String separator) {
        if (StringUtils.isEmpty(str)) return Collections.emptyList();
        return Stream.of(str.split(separator))
                .filter(s -> !StringUtils.isEmpty(s))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public static Date toDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static void main(String[] args) {
        System.out.println(null instanceof FullNote);

    }
}
