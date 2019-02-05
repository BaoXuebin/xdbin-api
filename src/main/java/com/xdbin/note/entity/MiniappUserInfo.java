package com.xdbin.note.entity;

import com.xdbin.common.constants.Constants;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author: BaoXuebin
 * Date: 2019/2/2
 * Time: 9:40 PM
 */
@Data
@Entity
@Table(name = "miniapp_user_info")
@SQLDelete(sql = "UPDATE miniapp_user_info SET valid = " + Constants.DELETE_FLAG_DELETE + " WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE miniapp_user_info SET valid = " + Constants.DELETE_FLAG_DELETE + " WHERE id = ?")
@Where(clause = "valid = " + Constants.DELETE_FLAG_NORMAL)
public class MiniappUserInfo implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String openId;
    private String nickName;
    private String avatarUrl;
    private Integer valid;

}
