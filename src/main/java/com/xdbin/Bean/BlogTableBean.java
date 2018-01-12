package com.xdbin.Bean;

import com.xdbin.domain.Blog;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: baoxuebin
 * Date: 2017/10/2
 */
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

    public BlogTableBean() {
    }

    public BlogTableBean(String blogId, String shortBlogId, String title, Date publishDate, boolean isPub) {
        this.blogId = blogId;
        this.shortBlogId = shortBlogId;
        this.title = title;
        this.publishDate = publishDate;
        this.isPub = isPub;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getShortBlogId() {
        return shortBlogId;
    }

    public void setShortBlogId(String shortBlogId) {
        this.shortBlogId = shortBlogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public boolean isPub() {
        return isPub;
    }

    public void setPub(boolean pub) {
        isPub = pub;
    }
}
