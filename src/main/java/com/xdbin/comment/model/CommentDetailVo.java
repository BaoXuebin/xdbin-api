package com.xdbin.comment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CommentDetailVo implements Serializable {

    @Id
    private Long id;
    private String origin;
    private String title; // 相关文章标题
    private Integer replyId;
    private String username;
    private String content;
    private Integer type;
    private Date publishTime;
    private Integer valid;

}
