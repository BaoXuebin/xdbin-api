package com.xdbin.vo;

import com.xdbin.domain.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Author: BaoXuebin
 * Date: 2018/9/15
 * Time: 上午11:52
 */
@Data
@NoArgsConstructor
public class CommentVo {

    private Long id;
    private String origin;
    private Integer replyId;
    private String username;
    private String content;
    private Integer type;
    private Date publishTime;
    private Integer valid;

    public CommentVo(Comment comment) {
        if (!StringUtils.isEmpty(comment)) {
            this.id = comment.getId();
            this.origin = comment.getOrigin();
            this.replyId = comment.getReplyId();
            this.username = comment.getUsername();
            this.content = comment.getContent();
            this.type = comment.getType();
            this.publishTime = comment.getPublishTime();
            this.valid = comment.getValid();
        }
    }

    public static CommentVo parse(Comment comment) {
        if (StringUtils.isEmpty(comment)) return null;
        return new CommentVo(comment);
    }

}
