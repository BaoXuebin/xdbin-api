package com.xdbin.lab.entity;

import com.xdbin.common.constants.Constants;
import com.xdbin.lab.condition.MoodCondition;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "T_MOOD")
@SQLDelete(sql = "UPDATE t_mood SET valid = " + Constants.DELETE_FLAG_DELETE + " WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE t_mood SET valid = " + Constants.DELETE_FLAG_DELETE + " WHERE id = ?")
@Where(clause = "valid = " + Constants.DELETE_FLAG_NORMAL)
public class Mood implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String user;
    private String avatar;
    private String action;
    private String content;
    private Integer type;
    private Date publishTime;
    private Integer liked;
    private Integer pub;
    private Integer valid;

    public static Mood from(MoodCondition moodCondition) {
        if (StringUtils.isEmpty(moodCondition)) return null;
        Mood mood = new Mood();
        mood.setUser(moodCondition.getUser());
        mood.setAction(moodCondition.getAction());
        mood.setAvatar(moodCondition.getAvatar());
        mood.setType(moodCondition.getType());
        mood.setContent(moodCondition.getContent());
        mood.setPub(moodCondition.getPub());
        return mood;
    }

}
