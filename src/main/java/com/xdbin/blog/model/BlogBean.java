package com.xdbin.blog.model;

import com.xdbin.tag.entity.Tag;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Author: baoxuebin
 * Date: 2017/9/13
 */
@Data
public class BlogBean implements Serializable {

    private String blogId;

    private String title;

    private List<Tag> tags;

    private Integer summaryType;

    private String summary;

    private Integer contentType;

    private String content;

    private boolean ifPub;

    public String validate() {
        if (StringUtils.isEmpty(title)) {
            return "标题不能为空";
        } else if (StringUtils.isEmpty(content)) {
            return "博客内容不能为空";
        }
        return null;
    }

}
