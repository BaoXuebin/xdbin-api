package com.xdbin.note.entity;

import com.xdbin.common.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author: BaoXuebin
 * Date: 2019/2/2
 * Time: 9:39 PM
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "NOTE_IMAGES")
@SQLDelete(sql = "UPDATE note_images SET valid = " + Constants.DELETE_FLAG_DELETE + " WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE note_images SET valid = " + Constants.DELETE_FLAG_DELETE + " WHERE id = ?")
@Where(clause = "valid = " + Constants.DELETE_FLAG_NORMAL)
public class NoteImages implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long noteId;
    private String imageUrl;
    private Integer valid;

    public NoteImages(Long noteId, String imageUrl, Integer valid) {
        this.noteId = noteId;
        this.imageUrl = imageUrl;
        this.valid = valid;
    }
}
