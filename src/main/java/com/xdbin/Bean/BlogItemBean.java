package com.xdbin.Bean;

import com.xdbin.config.DicConstants;
import com.xdbin.domain.Blog;
import com.xdbin.utils.ConvertUtil;
import org.springframework.util.StringUtils;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Author: baoxuebin
 * Date: 2017/9/10
 */
public class BlogItemBean implements Serializable {

    private String blogId;

    private String title;

    private Date updateTime;

    private String summary;

    private String summaryTextType;

    private List<String> tags;

    public BlogItemBean() {}

    public BlogItemBean(String blogId, String title, Date updateTime, String summaryTextType, String summary, List<String> tags) {
        this.blogId = blogId;
        this.title = title;
        this.updateTime = updateTime;
        this.summaryTextType = summaryTextType;
        this.summary = summary;
        this.tags = tags;
    }

    public static BlogItemBean parseBean(Blog blog) {
        if (StringUtils.isEmpty(blog)) return null;
        List<String> tags = ConvertUtil.getTagNames(blog.getTags());
        String summaryTextType = "markdown";
        if (blog.getSummaryTextType() == Blog.HTML) {
            summaryTextType = "html";
        } else if (blog.getSummaryTextType() == Blog.TEXT) {
            summaryTextType = "text";
        }

        return new BlogItemBean(
                blog.getBlogId(),
                blog.getTitle(),
                blog.getUpdateTime(),
                summaryTextType,
                blog.getSummary(),
                tags
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String>
                                tags) {
        this.tags = tags;
    }

    public String getSummaryTextType() {
        return summaryTextType;
    }

    public void setSummaryTextType(String summaryTextType) {
        this.summaryTextType = summaryTextType;
    }

    @Override
    public String toString() {
        return "BlogItemBean{" +
                "blogId='" + blogId + '\'' +
                ", title='" + title + '\'' +
                ", updateTime=" + updateTime +
                ", summary='" + summary + '\'' +
                ", summaryTextType='" + summaryTextType + '\'' +
                ", tags=" + tags +
                '}';
    }
}
