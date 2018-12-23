package com.xdbin.lab.entity.book;

import com.xdbin.common.constants.Constants;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "BOOK_COMMENT")
@SQLDelete(sql = "UPDATE book_comment SET valid = " + Constants.DELETE_FLAG_DELETE + " WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE book_comment SET valid = " + Constants.DELETE_FLAG_DELETE + " WHERE id = ?")
@Where(clause = "valid = " + Constants.DELETE_FLAG_NORMAL)
public class BookComment implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer bookId;
    private String comment;
    private String author;
    private String avatar;
    private Integer chapter;
    private Integer section;
    private String position;
    private Date publishTime;
    private Integer pub;
    private Integer valid;
}
