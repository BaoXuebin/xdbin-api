package com.xdbin.Bean;

import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Author: baoxuebin
 * Date: 2017/9/13
 */
public class BlogBean implements Serializable {

    private String blogId;

    private String title;

    private String tags;

    private String summary;

    private String content;

    private boolean ifPub;

    public String validate() {
        if (StringUtils.isEmpty(title)) {
            return "标题不能为空";
        } else if (StringUtils.isEmpty(content)) {
            return "博客内容不能为空";
        }
        return null;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isIfPub() {
        return ifPub;
    }

    public void setIfPub(boolean ifPub) {
        this.ifPub = ifPub;
    }
}
