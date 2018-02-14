package com.xdbin.Bean;

import com.xdbin.domain.Blog;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Author: baoxuebin
 * Date: 2017/9/13
 */
@Data
public class BlogBean implements Serializable {

    private String blogId;

    private String title;

    private String tags;

    private String summary;

    private String content;

    private boolean ifPub;

    public static BlogBean parseBean(Blog blog, String content) {
        if (StringUtils.isEmpty(blog)) return null;

        BlogBean blogBean = new BlogBean();
        blogBean.setBlogId(blog.getBlogId());
        blogBean.setTitle(blog.getTitle());
        blogBean.setTags(blog.getTags());
        blogBean.setSummary(blog.getSummary());
        blogBean.setContent(content);
        blogBean.setIfPub(blog.getIfPub() == 1);

        return blogBean;
    }

    public String validate() {
        if (StringUtils.isEmpty(title)) {
            return "标题不能为空";
        } else if (StringUtils.isEmpty(content)) {
            return "博客内容不能为空";
        }
        return null;
    }

}
