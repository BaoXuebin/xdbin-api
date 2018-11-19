package com.xdbin.comment.entity;

import com.xdbin.common.constants.Constants;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author: BaoXuebin
 * Date: 2018/9/11
 * Time: 下午11:18
 */
@Data
@Entity
@Table(name = "T_COMMENT")
@SQLDelete(sql = "UPDATE t_comment SET valid = " + Constants.DELETE_FLAG_DELETE + " WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE t_comment SET valid = " + Constants.DELETE_FLAG_DELETE + " WHERE id = ?")
@Where(clause = "valid = " + Constants.DELETE_FLAG_NORMAL)
public class Comment implements Serializable {

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
