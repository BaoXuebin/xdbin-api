package com.xdbin.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author: baoxuebin
 * Date: 2017/9/11
 */
@Entity
@Table(name = "DIC_TAG")
public class Tag implements Serializable {

    private long tagId;

    private String tagName;

    public Tag() {
    }

    public Tag(long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
