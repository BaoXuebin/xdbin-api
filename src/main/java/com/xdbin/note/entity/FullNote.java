package com.xdbin.note.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Author: BaoXuebin
 * Date: 2019/2/3
 * Time: 9:15 PM
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FullNote {

    @Id
    private Long id;
    private String userId;
    private String content;
    private Date publishTime;
    private Integer pub;
    private Integer valid;

    private String images;
    private String nickName;
    private String avatarUrl;

}
