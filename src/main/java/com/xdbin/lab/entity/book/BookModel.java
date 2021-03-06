package com.xdbin.lab.entity.book;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class BookModel implements Serializable {

    @Id
    private Integer id;
    private String isbn;
    private String title;
    private String subTitle;
    private String author;
    private String translator;
    private String tags;
    private String publisher; // 出版社
    private String publishDate;
    private String price;
    private String pages; // 页数
    private String summary;
    private String image;
    private String doubanLink;
    private Integer pub;

    private Integer progress;
    @Column(name = "update_time")
    private Date progressUpdateTime;

}
