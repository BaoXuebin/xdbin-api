package com.xdbin.bean;

import com.xdbin.domain.Blog;
import com.xdbin.domain.Tag;
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
public class BlogItemBean implements Serializable {

    private String blogId;

    private String title;

    private Date publishTime;

    private Date updateTime;

    private String summary;

    private String summaryTextType;

    private List<Tag> tags;

    public static BlogItemBean parseBean(Blog blog) {
        if (StringUtils.isEmpty(blog)) return null;
        List<Tag> tags = ConvertUtil.getTagNames(blog.getTags());
        String summaryTextType = "markdown";
        if (blog.getSummaryTextType() == Blog.HTML) {
            summaryTextType = "html";
        } else if (blog.getSummaryTextType() == Blog.TEXT) {
            summaryTextType = "text";
        }

        return new BlogItemBean(
                blog.getBlogId(),
                blog.getTitle(),
                blog.getPublishTime(),
                blog.getUpdateTime(),
                blog.getSummary(),
                summaryTextType,
                tags
        );
    }
}
