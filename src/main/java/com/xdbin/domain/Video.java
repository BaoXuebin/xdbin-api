package com.xdbin.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author: BaoXuebin
 * Date: 2018/7/8
 * Time: 下午3:25
 */
@Data
@Entity
@Table(name = "t_video")
public class Video implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String introduction;

    private String image;

    private String source;

    private String tags;

    private Date uploadTime;

    private Integer heartNum;

    @Column(name = "public")
    private Integer pub;

}
