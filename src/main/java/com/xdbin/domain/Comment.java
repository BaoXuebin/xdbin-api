package com.xdbin.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: BaoXuebin
 * Date: 2018/9/11
 * Time: 下午11:18
 */
@Data
@Entity
@Table(name = "T_COMMENT")
public class Comment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String origin;
    private Integer replyId;
    private String username;
    private String email;
    private String website;
    private String content;
    private Integer type;
    private Date publishTime;
    private Integer valid;

}
