package com.xdbin.note.vo;

import com.xdbin.note.entity.FullNote;
import com.xdbin.utils.ConvertUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Date;

/**
 * Author: BaoXuebin
 * Date: 2019/2/3
 * Time: 10:37 PM
 */
@Data
public class FullNoteVO {

    private Long id;
    private String userId;
    private String content;
    private Date publishTime;
    private Integer pub;
    private Integer valid;
    private String nickName;
    private String avatarUrl;

    private List<String> images;

    public static FullNoteVO from(Object obj) {
        if (StringUtils.isEmpty(obj)) return null;
        if (obj instanceof FullNote) {
            return from((FullNote) obj);
        }
        return null;
    }

    public static FullNoteVO from(FullNote fullNote) {
        if (StringUtils.isEmpty(fullNote)) return null;
        FullNoteVO fullNoteVO = new FullNoteVO();
        fullNoteVO.setId(fullNote.getId());
        fullNoteVO.setUserId(fullNote.getUserId());
        fullNoteVO.setContent(fullNote.getContent());
        fullNoteVO.setPublishTime(fullNote.getPublishTime());
        fullNoteVO.setPub(fullNote.getPub());
        fullNoteVO.setValid(fullNote.getValid());
        fullNoteVO.setImages(ConvertUtil.split(fullNote.getImages(), ","));
        fullNoteVO.setNickName(fullNote.getNickName());
        fullNoteVO.setAvatarUrl(fullNote.getAvatarUrl());
        return fullNoteVO;
    }

}
