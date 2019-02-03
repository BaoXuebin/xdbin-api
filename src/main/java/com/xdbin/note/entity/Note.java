package com.xdbin.note.entity;

import com.xdbin.common.constants.Constants;
import com.xdbin.note.condition.NoteCondition;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author: BaoXuebin
 * Date: 2019/2/2
 * Time: 9:37 PM
 */
@Data
@Entity
@Table(name = "NOTE")
@SQLDelete(sql = "UPDATE note SET valid = " + Constants.DELETE_FLAG_DELETE + " WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE note SET valid = " + Constants.DELETE_FLAG_DELETE + " WHERE id = ?")
@Where(clause = "valid = " + Constants.DELETE_FLAG_NORMAL)
public class Note implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String content;
    private Date publishTime;
    private Integer pub;
    private Integer valid;

    public static Note from(NoteCondition noteCondition) {
        if (StringUtils.isEmpty(noteCondition)) return null;
        Note note = new Note();
        note.setContent(noteCondition.getContent());
        note.setPub(noteCondition.getPub());
        return note;
    }

}
