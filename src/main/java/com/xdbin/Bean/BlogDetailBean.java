package com.xdbin.Bean;

import com.xdbin.domain.Blog;
import com.xdbin.utils.FileUtil;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;

/**
 * Author: baoxuebin
 * Date: 2017/9/10
 */
public class BlogDetailBean implements Serializable {

    private String blogId;

    private String title;

    private Date updateTime;

    private String[] tags;

    private String contentTextType;

    private String content;

    public BlogDetailBean() {}

    public BlogDetailBean(String blogId, String title, Date updateTime, String[] tags, String contentTextType, String content) {
        this.blogId = blogId;
        this.title = title;
        this.updateTime = updateTime;
        this.tags = tags;
        this.contentTextType = contentTextType;
        this.content = content;
    }

    public static BlogDetailBean parseBean(Blog blog, String content) {
        if (StringUtils.isEmpty(blog)) return null;
        String[] tags = StringUtils.isEmpty(blog.getTags()) ? null : blog.getTags().split(",");
        String contentTextType = "markdown";
        if (blog.getContentTextType() == Blog.HTML) {
            contentTextType = "html";
        } else if (blog.getContentTextType() == Blog.TEXT) {
            contentTextType = "text";
        }
        return new BlogDetailBean(
                blog.getBlogId(),
                blog.getTitle(),
                blog.getUpdateTime(),
                tags,
                contentTextType,
                content
        );
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentTextType() {
        return contentTextType;
    }

    public void setContentTextType(String contentTextType) {
        this.contentTextType = contentTextType;
    }
}
