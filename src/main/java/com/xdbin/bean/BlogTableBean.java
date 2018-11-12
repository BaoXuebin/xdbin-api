package com.xdbin.bean;

import com.xdbin.domain.Blog;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: baoxuebin
 * Date: 2017/10/2
 */
@Data
@AllArgsConstructor
public class BlogTableBean implements Serializable {

    private String blogId;

    private String shortBlogId;

    private String title;

    private Date publishDate;

    private boolean isPub;

    public static BlogTableBean parseBean(Blog blog) {
        if (StringUtils.isEmpty(blog)) return null;
        String shortBlogId = blog.getBlogId().substring(blog.getBlogId().length() - 6);
        return new BlogTableBean(
                blog.getBlogId(),
                shortBlogId,
                blog.getTitle(),
                blog.getPublishTime(),
                blog.getIfPub() == 1
        );
    }
}
