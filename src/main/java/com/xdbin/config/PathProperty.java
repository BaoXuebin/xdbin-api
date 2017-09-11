package com.xdbin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Author: baoxuebin
 * Date: 2017/9/11
 */
@Component
@ConfigurationProperties(prefix = "path")
public class PathProperty {

    private String blog;

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }
}
