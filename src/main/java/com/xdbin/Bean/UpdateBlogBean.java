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
 * Date: 2017/10/6
 */
@Data
public class UpdateBlogBean implements Serializable {

    private String blogId;

    private String title;

    private List<Tag> tags;

    private String summary;

    private String content;

    private boolean ifPub;

    public static UpdateBlogBean parseBean(Blog blog, String content) {
        if (StringUtils.isEmpty(blog)) return null;

        UpdateBlogBean updateBlogBean = new UpdateBlogBean();
        updateBlogBean.setBlogId(blog.getBlogId());
        updateBlogBean.setTitle(blog.getTitle());

        List<Tag> tags = new ArrayList<>();
        if (!StringUtils.isEmpty(blog.getTags())) {
            String[] tagIds = blog.getTags().split(",");
            for (String tagId : tagIds) {
                Long id = ConvertUtil.parseLong(tagId, -1);
                String name = DicConstants.getInstance().getTagMap().get(id);
                if (!StringUtils.isEmpty(name))
                    tags.add(new Tag(id, name));
            }
        }

        updateBlogBean.setTags(tags);
        updateBlogBean.setSummary(blog.getSummary());
        updateBlogBean.setContent(content);
        updateBlogBean.setIfPub(blog.getIfPub() == 1);

        return updateBlogBean;
    }
}
