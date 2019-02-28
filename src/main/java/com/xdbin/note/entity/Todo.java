package com.xdbin.note.entity;

import com.xdbin.common.constants.Constants;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: BaoXuebin
 * Date: 2019/2/25
 * Time: 10:23 PM
 */
@Data
@Entity
@Table(name = "t_todo")
@SQLDelete(sql = "UPDATE t_todo SET valid = " + Constants.DELETE_FLAG_DELETE + " WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE t_todo SET valid = " + Constants.DELETE_FLAG_DELETE + " WHERE id = ?")
@Where(clause = "valid = " + Constants.DELETE_FLAG_NORMAL)
public class Todo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String type;
    private Date createTime;
    private Integer ifFinish;
    private Date finishTime;
    private Integer ifPub;
    private Integer valid;

}
