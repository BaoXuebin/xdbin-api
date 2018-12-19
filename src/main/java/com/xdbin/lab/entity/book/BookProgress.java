package com.xdbin.lab.entity.book;

import com.xdbin.common.base.BaseEntity;
import com.xdbin.common.constants.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "BOOK_PROGRESS")
@SQLDelete(sql = "UPDATE BOOK_PROGRESS SET valid = " + Constants.DELETE_FLAG_DELETE + " WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE BOOK_PROGRESS SET valid = " + Constants.DELETE_FLAG_DELETE + " WHERE id = ?")
@Where(clause = "valid = " + Constants.DELETE_FLAG_NORMAL)
public class BookProgress implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer bookId;
    private Integer progress;
    private Date updateTime;
    private Integer valid;
}
