package com.xdbin.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BlogItem implements Serializable {

    @Id
    private String blogId;

    private String title;

    private String summary;

    private String tags;

    private Date publishTime;

    private Date updateTime;

    private Integer pub;

}
