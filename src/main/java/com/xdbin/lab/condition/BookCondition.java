package com.xdbin.lab.condition;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BookCondition implements Serializable {

    private Integer id;
    private String isbn;
    private String title;
    private String subTitle;
    private String publisher; // 出版社
    private String publishDate;
    private String price;
    private String pages; // 页数
    private String summary;
    private String image;
    private String doubanLink;
    private Integer pub;

    private List<String> authors;
    private List<String> translators;
    private List<String> tagGroup;

}
