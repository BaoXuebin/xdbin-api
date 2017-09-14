package com.xdbin.config;

import java.util.Map;

/**
 * Author: baoxuebin
 * Date: 2017/9/11
 */
public class DicConstants {

    private static DicConstants instance = new DicConstants();

    private DicConstants() {}

    public static DicConstants getInstance() {
        return instance;
    }

    private Map<Long, String> tagMap;

    public Map<Long, String> getTagMap() {
        return tagMap;
    }

    public void setTagMap(Map<Long, String> tagMap) {
        this.tagMap = tagMap;
    }
}
