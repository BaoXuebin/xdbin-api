package com.xdbin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Author: baoxuebin
 * Date: 2018/3/30
 */
@Entity
@Table(name = "NET_BLOG")
public class NetBlog implements Serializable {

    private Long uid;

    private String title;

    private String href;

    private String author;

    private Date publishTime;

    private String origin;

    private String originLink;

    @Id
    @Column(name = "uid")
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "href")
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Column(name = "publishtime")
    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    @Column(name = "origin")
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Column(name = "originlink")
    public String getOriginLink() {
        return originLink;
    }

    public void setOriginLink(String originLink) {
        this.originLink = originLink;
    }
}
