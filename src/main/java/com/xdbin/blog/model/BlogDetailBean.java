package com.xdbin.blog.model;

import com.xdbin.blog.entity.Blog;
import com.xdbin.tag.entity.Tag;
import com.xdbin.utils.ConvertUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Author: baoxuebin
 * Date: 2017/9/10
 */
@Data
@AllArgsConstructor
public class BlogDetailBean implements Serializable {

    private String blogId;

    private String title;

    private Date publishTime;

    private Date updateTime;

    private List<Tag> tags;

    private String contentTextType;

    private String content;

    public BlogDetailBean() {}

    public static BlogDetailBean parseBean(Blog blog, String content) {
        if (StringUtils.isEmpty(blog)) return null;
        List<Tag> tags = ConvertUtil.getTagNames(blog.getTags());
        String contentTextType = "markdown";
        if (blog.getContentTextType() == Blog.HTML) {
            contentTextType = "html";
        } else if (blog.getContentTextType() == Blog.TEXT) {
            contentTextType = "text";
        }
        return new BlogDetailBean(
                blog.getBlogId(),
                blog.getTitle(),
                blog.getPublishTime(),
                blog.getUpdateTime(),
                tags,
                contentTextType,
                content
        );
    }
}
