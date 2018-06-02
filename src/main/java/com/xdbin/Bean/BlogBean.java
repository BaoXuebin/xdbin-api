package com.xdbin.Bean;

import com.xdbin.config.DicConstants;
import com.xdbin.domain.Blog;
import com.xdbin.domain.Tag;
import com.xdbin.utils.ConvertUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
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

    private String summary;

    private String content;

    private boolean ifPub;

//    public static BlogBean parseBean(Blog blog, String content) {
//        if (StringUtils.isEmpty(blog)) return null;
//
//        BlogBean blogBean = new BlogBean();
//        blogBean.setBlogId(blog.getBlogId());
//        blogBean.setTitle(blog.getTitle());
//        List<Tag> tags = new ArrayList<>();
//        if (!StringUtils.isEmpty(blog.getTags())) {
//            String[] tagIds = blog.getTags().split(",");
//            for (String tagId : tagIds) {
//                Long id = ConvertUtil.parseLong(tagId, -1);
//                String name = DicConstants.getInstance().getTagMap().get(id);
//                if (!StringUtils.isEmpty(name))
//                    tags.add(new Tag(id, name));
//            }
//        }
//        blogBean.setSummary(blog.getSummary());
//        blogBean.setContent(content);
//        blogBean.setIfPub(blog.getIfPub() == 1);
//
//        return blogBean;
//    }

    public String validate() {
        if (StringUtils.isEmpty(title)) {
            return "标题不能为空";
        } else if (StringUtils.isEmpty(content)) {
            return "博客内容不能为空";
        }
        return null;
    }

}
