package com.xdbin.blog.vo;

import com.xdbin.blog.entity.BlogItem;
import com.xdbin.config.DicConstants;
import com.xdbin.tag.entity.Tag;
import com.xdbin.utils.ConvertUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogItemView implements Serializable {

    private String blogId;

    private String title;

    private String summary;

    private List<Tag> tags;

    private Date publishTime;

    private Date updateTime;

    private Integer pub;

    public static BlogItemView from(BlogItem blogItem) {
        if (StringUtils.isEmpty(blogItem)) return null;
        List<Tag> tags = ConvertUtil.split(blogItem.getTags(), "\\|").stream()
                            .map(t -> {
                                Long tagId = ConvertUtil.parseLong(t, -1);
                                String tagName = DicConstants.getInstance().getTagMap().get(tagId);
                                return new Tag(tagId, tagName);
                            }).collect(Collectors.toList());

        return new BlogItemView(
                blogItem.getBlogId(),
                blogItem.getTitle(),
                blogItem.getSummary(),
                tags,
                blogItem.getPublishTime(),
                blogItem.getUpdateTime(),
                blogItem.getPub()
        );
    }

}
