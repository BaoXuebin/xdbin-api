package com.xdbin.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author: baoxuebin
 * Date: 2017/9/10
 */
@Entity
@Table(name = "T_BLOG")
public class Blog implements Serializable {

    public final static int MARKDOWN = 1;
    public final static int HTML = 2;
    public final static int TEXT = 3;

    private String blogId;

    private String title;

    private Integer summaryTextType;

    private String summary;

    private Date publishTime;

    private Date updateTime;

    private Integer editNum;

    private String tags;

    private Integer contentTextType;

    private String contentUrl;

    private Integer ifPub;

    public Blog() {
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(name = "blog_id", length = 32)
    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    @Column(name = "summary_text_type")
    public Integer getSummaryTextType() {
        return summaryTextType;
    }

    public void setSummaryTextType(Integer summaryTextType) {
        this.summaryTextType = summaryTextType;
    }

    @Column(name = "summary", length = 2000)
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Column(name = "publish_time", nullable = false)
    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "tags")
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Column(name = "content_text_type")
    public Integer getContentTextType() {
        return contentTextType;
    }

    public void setContentTextType(Integer contentTextType) {
        this.contentTextType = contentTextType;
    }

    @Column(name = "content_url", nullable = false)
    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    @Column(name = "edit_num")
    public Integer getEditNum() {
        return editNum;
    }

    public void setEditNum(Integer editNum) {
        this.editNum = editNum;
    }

    @Column(name = "if_pub")
    public Integer getIfPub() {
        return ifPub;
    }

    public void setIfPub(Integer ifPub) {
        this.ifPub = ifPub;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "blogId='" + blogId + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", publishTime=" + publishTime +
                ", updateTime=" + updateTime +
                ", tags='" + tags + '\'' +
                ", contentUrl='" + contentUrl + '\'' +
                ", ifPub=" + ifPub +
                '}';
    }
}
